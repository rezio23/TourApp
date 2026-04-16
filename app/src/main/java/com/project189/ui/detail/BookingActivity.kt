package com.project189.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.project189.data.model.TourItem
import com.project189.databinding.ActivityBookingBinding
import com.project189.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var tourItem: TourItem

    // Bot Token and Chat ID
    private val BOT_TOKEN = "8705575762:AAElKzeX67gUNw6dgUbTORXLtPj5Q3eATC0"
    private val CHAT_ID = "1453582611"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = intent.getStringExtra(Constants.EXTRA_TOUR_JSON) ?: run {
            finish(); return
        }
        tourItem = Gson().fromJson(json, TourItem::class.java)

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        binding.tvTourName.text = tourItem.title
        binding.tvTourPrice.text = "$${tourItem.price}"
        
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupClickListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val note = binding.etNote.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val message = """
                🆕 *New Booking Received!*
                
                🏨 *Tour:* ${tourItem.title}
                💰 *Price:* $${tourItem.price}
                
                👤 *Customer:* $name
                📞 *Phone:* $phone
                📧 *Email:* $email
                📝 *Note:* ${if (note.isEmpty()) "N/A" else note}
            """.trimIndent()

            sendTelegramMessage(message)
        }
    }

    private fun sendTelegramMessage(message: String) {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                var conn: HttpURLConnection? = null
                try {
                    val url = URL("https://api.telegram.org/bot$BOT_TOKEN/sendMessage")
                    conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.doOutput = true
                    conn.connectTimeout = 15000
                    conn.readTimeout = 15000
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                    val postData = "chat_id=$CHAT_ID&text=${URLEncoder.encode(message, "UTF-8")}&parse_mode=Markdown"
                    conn.outputStream.use { os ->
                        os.write(postData.toByteArray(Charsets.UTF_8))
                    }

                    val responseCode = conn.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        "SUCCESS"
                    } else {
                        val errorStream = conn.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                        Log.e("BookingActivity", "Telegram API Error: $errorStream")
                        "API_ERROR: $responseCode - $errorStream"
                    }
                } catch (e: Exception) {
                    Log.e("BookingActivity", "Network Exception", e)
                    "EXCEPTION: ${e.message}"
                } finally {
                    conn?.disconnect()
                }
            }

            when {
                result == "SUCCESS" -> {
                    Toast.makeText(this@BookingActivity, "Booking sent successfully!", Toast.LENGTH_LONG).show()
                    finish()
                }
                result.startsWith("API_ERROR") -> {
                    Toast.makeText(this@BookingActivity, "Telegram API Error. Please check your Bot Token and Chat ID.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this@BookingActivity, "Network Error: Could not reach Telegram. Please check your internet connection.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

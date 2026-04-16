package com.project189.ui.detail

import android.os.Bundle
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

    // Replace with your actual Bot Token and Chat ID
    private val BOT_TOKEN = "YOUR_BOT_TOKEN"
    private val CHAT_ID = "YOUR_CHAT_ID"

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
            val success = withContext(Dispatchers.IO) {
                try {
                    val urlString = "https://api.telegram.org/bot$BOT_TOKEN/sendMessage?chat_id=$CHAT_ID&text=${URLEncoder.encode(message, "UTF-8")}&parse_mode=Markdown"
                    val url = URL(urlString)
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "GET"
                    val responseCode = conn.responseCode
                    responseCode == HttpURLConnection.HTTP_OK
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if (success) {
                Toast.makeText(this@BookingActivity, "Booking sent successfully!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this@BookingActivity, "Failed to send booking. Check your connection or Bot Token.", Toast.LENGTH_LONG).show()
            }
        }
    }
}

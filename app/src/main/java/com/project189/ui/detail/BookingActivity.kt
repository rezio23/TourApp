package com.project189.ui.detail

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.project189.BuildConfig
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
    private lateinit var auth: FirebaseAuth

    private val telegramBotToken: String by lazy { BuildConfig.TELEGRAM_BOT_TOKEN.trim() }
    private val telegramChatId: String by lazy { BuildConfig.TELEGRAM_CHAT_ID.trim() }

    private val database: FirebaseDatabase by lazy {
        val configuredUrl = BuildConfig.FIREBASE_DATABASE_URL.trim()
        if (configuredUrl.isNotEmpty()) {
            FirebaseDatabase.getInstance(configuredUrl)
        } else {
            FirebaseDatabase.getInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

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

            if (name.length < 2) {
                Toast.makeText(this, "Full name must be at least 2 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!phone.all { it.isDigit() } || phone.length !in 9..10) {
                Toast.makeText(this, "Phone number must be 9-10 digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usernamePart = email.substringBefore("@")
            if (usernamePart != usernamePart.lowercase()) {
                Toast.makeText(this, "Email username must be in lowercase", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.endsWith("@gmail.com") && !email.endsWith("@example.com")) {
                Toast.makeText(this, "Email must end with @gmail.com or @example.com", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bookingData = hashMapOf(
                "tourTitle" to tourItem.title,
                "price" to tourItem.price,
                "customerName" to name,
                "customerPhone" to phone,
                "customerEmail" to email,
                "note" to note,
                "userId" to (auth.currentUser?.uid ?: "anonymous"),
                "timestamp" to System.currentTimeMillis()
            )

            saveBookingToRealtimeDatabase(bookingData)
        }
    }

    private fun saveBookingToRealtimeDatabase(data: Map<String, Any>) {
        val bookingRef = database.getReference("Bookings").push()
        bookingRef.setValue(data)
            .addOnSuccessListener {
                if (!isTelegramConfigured()) {
                    Toast.makeText(
                        this,
                        "Booking saved. Telegram notification is not configured.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                    return@addOnSuccessListener
                }

                val message = """
                    *New Booking Received!*

                    *Tour:* ${data["tourTitle"]}
                    *Price:* $${data["price"]}

                    *Customer:* ${data["customerName"]}
                    *Phone:* ${data["customerPhone"]}
                    *Email:* ${data["customerEmail"]}
                    *Note:* ${if (data["note"].toString().isEmpty()) "N/A" else data["note"]}
                """.trimIndent()

                sendTelegramMessage(message)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save booking: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun isTelegramConfigured(): Boolean {
        return telegramBotToken.isNotEmpty() && telegramChatId.isNotEmpty()
    }

    private fun sendTelegramMessage(message: String) {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                var conn: HttpURLConnection? = null
                try {
                    val url = URL("https://api.telegram.org/bot$telegramBotToken/sendMessage")
                    conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.doOutput = true
                    conn.connectTimeout = 15000
                    conn.readTimeout = 15000
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                    val postData =
                        "chat_id=$telegramChatId&text=${URLEncoder.encode(message, "UTF-8")}&parse_mode=Markdown"
                    conn.outputStream.use { os ->
                        os.write(postData.toByteArray(Charsets.UTF_8))
                    }

                    val responseCode = conn.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        "SUCCESS"
                    } else {
                        val errorStream = conn.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                        Log.e("BookingActivity", "Telegram API Error: $errorStream")
                        "API_ERROR: $responseCode"
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
                    Toast.makeText(
                        this@BookingActivity,
                        "Booking saved, but Telegram rejected the notification.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                else -> {
                    Toast.makeText(
                        this@BookingActivity,
                        "Booking saved, but Telegram could not be reached.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
    }
}

# TourApp - Southeast Asia Travel Guide (Midterm Project)

**Course**: CS 218 - MOBILE DEVELOPMENT FOR ANDROID  
**Institution**: ACLEDA UNIVERSITY OF BUSINESS  
**Project Type**: Midterm Project  

TourApp is a modern Android application designed for exploring the best travel destinations in Cambodia, Philippines, and Indonesia. It provides a seamless user experience from discovery to booking, featuring real-time Telegram integration and Firebase authentication.

## 🚀 Features

### 🗺️ Multi-Country Discovery
- **Country Selector**: Easily toggle between **Cambodia**, **Philippines**, and **Indonesia** to view localized tours.
- **Smart Filtering**: Filter destinations by categories like **Beach**, **Camp**, **Jungle**, and **Mountain**.
- **Sliding Banners**: High-quality highlights for each country on the Home screen.

### 🔐 Secure Authentication
- **Firebase Auth**: Real-time cloud login and registration.
- **Strict Validation**:
    - **Usernames**: Must start with a Capital letter.
    - **Emails**: Limited to `@gmail.com` or `@example.com` domains with lowercase usernames.
    - **Passwords**: 8-16 characters with at least one uppercase, one lowercase, and one number.

### 📅 Booking System
- **Direct-to-Telegram**: Confirming a booking sends a formatted message directly to a Telegram bot for immediate response.
- **Validation**: Ensures high-quality leads with phone number (9-10 digits) and name length checks.

### 👤 User Profile
- **Personalized Account**: Displays capitalized usernames and profile pictures.
- **Saved Tours**: Manage your favorite destinations in one place.
- **Secure Logout**: Firebase session management.

### 📶 Offline Ready
- **Local Assets**: Optimized to load images from local assets for faster performance and offline viewing.

---

## 🛠️ Technologies
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Local JSON (Items) & Firebase Firestore ready
- **Authentication**: Firebase Auth
- **Image Loading**: Glide
- **Navigation**: Jetpack Navigation Component
- **UI Components**: Material Design 3, ViewPager2, DotsIndicator

---

## 🎨 Theme
The app uses a professional **Teal Blue** theme (`#4FA6A8`) designed for a calm and trustworthy travel experience.

---

## ⚙️ Configuration
To get the project running with your own backend:
1. Replace the `google-services.json` in the `app/` folder.
2. Copy `secrets.gradle.example` to `secrets.gradle` (already ignored by Git).
3. Add your local values in `secrets.gradle`:
   - `TELEGRAM_BOT_TOKEN`
   - `TELEGRAM_CHAT_ID`
   - `FIREBASE_DATABASE_URL` (optional if your Firebase default config already includes it)
4. Optionally store the same keys in `local.properties` or environment variables. The build reads values in this order:
   - `secrets.gradle`
   - `local.properties`
   - environment variables
5. Ensure local images are placed in `app/src/main/assets/`.

---

## 👥 Authors
Developed by **Sombath** as part of the CS 218 - Mobile Development for Android midterm requirements.

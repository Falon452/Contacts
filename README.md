# Contacts
[![Unit Tests](https://github.com/Falon452/Contacts/actions/workflows/unit_test.yml/badge.svg?branch=main)](https://github.com/Falon452/Contacts/actions/workflows/unit_test.yml)
[![Build Debug](https://github.com/Falon452/Contacts/actions/workflows/build_debug.yml/badge.svg)](https://github.com/Falon452/Contacts/actions/workflows/build_debug.yml)

## ▶️YouTube🔴

[![Custom Frame](https://img.youtube.com/vi/jNcroWeECBE/0.jpg)](https://youtube.com/shorts/jNcroWeECBE)

## Installation
1. Use **JDK 17**.
2. 🗝️ Add your API key to `local.properties`:
   ```properties
   ACTIVE_CAMPAIGN_API_KEY=your_api_key_here

or download apk from [drive](https://drive.google.com/file/d/1XDQshW3RzGuuGYk7joBTzaXbxaxISFzZ/view?usp=sharing)

## Requirements

- Display a list of contacts in a table ✅
- Limit the number of contacts to 50 ✅
- Sort by name, in descending order ✅
- Show the first and last name if available, otherwise show the email ✅
- Show a spinner while you’re waiting for a response ✅
- Show an appropriate message if the list of contacts is empty. ✅

<img src="https://github.com/user-attachments/assets/80099ec9-d9cb-4e53-8ab0-7b7a27ae29f9" width="300" alt="App Screenshot">

## 🧱 Presentation Architecture – MVI
![image](https://github.com/user-attachments/assets/534b699c-1767-452d-bd89-ffd9738c7d7e)

The project is structured using a **multi-module, feature-based architecture**.

It follows the **MVI (Model–View–Intent)** pattern for the presentation layer, promoting unidirectional data flow and clear state management.

## Additional Features

- 🌗 **Toggle Theme** – Switch between light and dark modes.
- 🚀 **Splash Screen Animation** – Smooth and engaging entry to the app.
- 🔄 **Background Logo Animation** – Adds a subtle animated touch to the UI.
- 👆 **Click Animations** – Interactive feedback for UI elements.
- 🔃 **Pull to Refresh** – Easily refresh the data with a swipe gesture.
- 🛠️ **Auto-Retry on Failure** – If a request to fetch data fails, it retries up to 5 times with a 1-second delay between attempts.
- ❌ **Error State UI** – Clearly communicates when a request fails.

---

## Key Libraries

- **Jetpack Compose**
- **Hilt**
- **Retrofit and OkHttp**
- **Kotlin Coroutines**
- **AndroidX Libraries**
- **Junit 5, mockk**
  

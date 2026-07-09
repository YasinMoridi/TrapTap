# 👹 TrapTap (TrollMind) | ترپ‌تپ (ترول‌مایند)

[English](#english) | [فارسی](#فارسی)

---

## English

## 📜 Description  
**TrapTap** (also known as **TrollMind**) is a cheeky, modern puzzle game designed to challenge your wit against a mischievous troll.  
Built natively for Android using **Jetpack Compose** and **Material 3**, the app offers a vibrant and interactive UI with smooth animations.  
The project follows the **MVVM (Model-View-ViewModel)** architecture and utilizes **Dagger Hilt** for robust dependency injection, ensuring the codebase is clean, testable, and scalable.

## ✨ Features
- 😈 **Outsmart the Troll:** Interactive puzzle mechanics with dynamic troll reactions.
- 🎨 **Material 3 UI:** Modern, responsive design based on high-fidelity Figma mocks.
- ⚡ **Advanced Animations:** Custom-rendered SVG Mascot with Float and Pulse animations.
- 🌓 **Dynamic Theming:** Seamless switching between Light and Dark modes.
- 🌐 **Multilingual & RTL:** Full support for English and Persian (Farsi) with automatic RTL layout.
- 📊 **Progress Tracking:** Level-based progression system with a visual dashboard.
- 🏗️ **Clean Architecture:** Built with MVVM, StateFlow, and Hilt for a premium development standard.

## 🛠 Built With

| Category                  | Technology                                                                                                  |
|---------------------------|-------------------------------------------------------------------------------------------------------------|
| 🏛 Architecture            | MVVM (Model-View-ViewModel)                                                                                |
| 🖼️ UI Framework            | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)                               |
| 🛠️ Dependency Injection    | [Dagger Hilt](https://dagger.dev/hilt/)                                                                     |
| 🔄 State Management        | StateFlow & SharedFlow                                                                                     |
| 🧭 Navigation              | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)                              |
| 🔄 Coroutines              | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)                                         |
| 📦 Local Storage           | [Room Database](https://developer.android.com/training/data-storage/room) & DataStore                      |

---

## فارسی

## 📜 توضیحات  
**ترپ‌تپ (TrapTap)** که با نام **ترول‌مایند** نیز شناخته می‌شود، یک بازی پازل مدرن و سرگرم‌کننده است که هوش شما را در مقابل یک ترول بازیگوش به چالش می‌کشد.  
این برنامه به صورت بومی (Native) برای اندروید با استفاده از **Jetpack Compose** و **Material 3** ساخته شده و رابط کاربری پویا و جذابی را همراه با انیمیشن‌های روان ارائه می‌دهد.  
پروژه از معماری **MVVM** پیروی می‌کند و از **Dagger Hilt** برای تزریق وابستگی استفاده می‌کند تا کدی تمیز، قابل تست و مقیاس‌پذیر داشته باشد.

## ✨ ویژگی‌ها
- 😈 **فریب ترول:** مکانیسم‌های تعاملی پازل با واکنش‌های داینامیک ترول.
- 🎨 **رابط کاربری متریال ۳:** طراحی مدرن و پاسخگو بر اساس طرح‌های فیگما با جزئیات بالا.
- ⚡ **انیمیشن‌های پیشرفته:** مسکات ترول با رندر اختصاصی SVG و انیمیشن‌های Float و Pulse.
- 🌓 **تم داینامیک:** جابجایی روان بین حالت‌های تیره (Dark) و روشن (Light).
- 🌐 **دوزبانه و راست‌چین:** پشتیبانی کامل از زبان‌های انگلیسی و فارسی با چیدمان خودکار RTL.
- 📊 **پیگیری پیشرفت:** سیستم پیشروی مرحله‌محور با داشبورد بصری.
- 🏗️ **معماری پاک:** ساخته شده با MVVM، StateFlow و Hilt برای بالاترین استانداردهای توسعه.

## 🛠 ساخته شده با

| دسته‌بندی | تکنولوژی |
| :--- | :--- |
| 🏛 معماری | MVVM (Model-View-ViewModel) |
| 🖼️ فریم‌ورک UI | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ تزریق وابستگی | [Dagger Hilt](https://dagger.dev/hilt/) |
| 🔄 مدیریت وضعیت | StateFlow & SharedFlow |
| 🧭 ناوبری | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 کوروتین‌ها | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 ذخیره‌سازی | [Room Database](https://developer.android.com/training/data-storage/room) & DataStore |

---

## 🚀 Development | توسعه

### 🛠 Run the App | اجرای برنامه
1. Clone the repository.
2. Open the project in **Android Studio Ladybug** or newer.
3. Sync Gradle and run the `:app` module.

### 🧪 Testing | تست
Run the UI tests for the Splash and Game components:
```bash
./gradlew connectedAndroidTest
```

## 📱 Screenshots | اسکرین‌شات‌ها

<table style="width:100%">
  <tr>
    <th>Splash Screen</th>
    <th>Levels Grid</th> 
    <th>Game UI</th> 
  </tr>
  <tr>
    <td><img src="screenshot/splash.png" width=240/></td> 
    <td><img src="screenshot/levels.png" width=240/></td>
    <td><img src="screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · Developed with ❤️ using Jetpack Compose.

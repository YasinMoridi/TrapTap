# 👹 TrapTap (TrollMind)

[Farsi](README.fa.md) | [Chinese](README.zh.md) | [Spanish](README.es.md) | [French](README.fr.md) | [Russian](README.ru.md) | [Arabic](README.ar.md) | [More...](../README.md)

---

## 📜 Description  
**TrapTap** (also known as **TrollMind**) is a cheeky, modern puzzle game designed to challenge your wit against a mischievous troll.  
Built natively for Android using **Jetpack Compose** and **Material 3**, the app offers a vibrant and interactive UI with smooth animations.  
The project follows the **MVVM (Model-View-ViewModel)** architecture and utilizes **Koin 4.0** for robust dependency injection, ensuring the codebase is clean, testable, and scalable.

## ✨ Features
- 😈 **Outsmart the Troll:** Interactive puzzle mechanics with dynamic troll reactions.
- 🎨 **Material 3 UI:** Modern, responsive design based on high-fidelity Figma mocks.
- ⚡ **Advanced Animations:** Custom-rendered SVG Mascot with Float and Pulse animations.
- 🌓 **Dynamic Theming:** Seamless switching between Light and Dark modes.
- 🌐 **Global Reach:** Full support for **25 languages** (including Persian, English, Chinese, Russian, Arabic, etc.) with automatic RTL layout support.
- 📊 **Progress Tracking:** Level-based progression system with a visual dashboard.
- 🏗️ **Clean Architecture:** Built with MVVM, StateFlow, and Koin 4.0 for a premium development standard.
- 💾 **Robust Persistence:** Leveraging **Room Database** and **DataStore** for reliable data storage.

## 🛠 Built With

| Category                  | Technology                                                                                                  |
|---------------------------|-------------------------------------------------------------------------------------------------------------|
| 🏛 Architecture            | MVVM (Model-View-ViewModel)                                                                                |
| 🖼️ UI Framework            | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)                               |
| 🛠️ Dependency Injection    | [Koin 4.0](https://insert-koin.io/)                                                                        |
| 🔄 State Management        | StateFlow & SharedFlow                                                                                     |
| 🧭 Navigation              | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)                              |
| 🔄 Coroutines              | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)                                         |
| 📦 Serialization           | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)                                   |
| 📦 Local Storage           | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 Testing                 | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) (Flow Testing) |

## 🎮 Level Guide
Check out the [Full Walkthrough Guide](../WALKTHROUGH.md) for solutions to all levels.

## 🚀 Development
### 🛠 Run the App
1. Clone the repository.
2. Open the project in **Android Studio Ladybug** or newer.
3. Sync Gradle and run the `:app` module.

### 🧪 Testing
Run the Unit and UI tests:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📱 Screenshots
<table style="width:100%">
  <tr>
    <th align="center">Splash Screen</th>
    <th align="center">Levels Grid</th> 
    <th align="center">Game UI</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · Developed with ❤️ using Jetpack Compose.

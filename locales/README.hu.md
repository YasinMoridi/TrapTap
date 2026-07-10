# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [Deutsch](README.de.md) | [More...](../README.md)

---

## 📜 Leírás
A **TrollMind** (más néven **TrapTap**) egy pimasz, modern fejtörő játék, amelyet arra terveztek, hogy próbára tegye az eszedet egy csintalan trollal szemben.
Androidra natívan készült **Jetpack Compose** és **Material 3** használatával, az alkalmazás élénk és interaktív felhasználói felületet kínál sima animációkkal.
A projekt az **MVVM (Model-View-ViewModel)** architektúrát követi, és a **Koin 4.0**-t használja a robusztus függőségi injektáláshoz.

## ✨ Funkciók
- 😈 **Járj túl a Troll eszén:** Interaktív rejtvény-mechanika dinamikus troll-reakciókkal.
- 🎨 **Material 3 UI:** Modern, reszponzív dizájn nagy hűségű Figma mock-upok alapján.
- ⚡ **Fejlett Animációk:** Egyedi SVG kabala lebegő és lüktető animációkkal.
- 🌓 **Dinamikus témázás:** Zökkenőmentes váltás a világos és sötét módok között.
- 🌐 **Globális elérhetőség:** Teljes támogatás **25 nyelvhez** (beleértve a perzsa, angol, kínai, orosz, arab stb. nyelveket) automatikus RTL elrendezés támogatással.
- 📊 **Haladás követése:** Szint alapú előrehaladási rendszer vizuális műszerfallal.
- 🏗️ **Tiszta architektúra:** MVVM, StateFlow és Koin 4.0 használatával készült a prémium fejlesztési szabvány érdekében.
- 💾 **Robusztus perzisztencia:** A **Room Database** és a **DataStore** kihasználása a megbízható adattárolás érdekében.

## 🛠 Készült

| Kategória | Technológia |
| :--- | :--- |
| 🏛 Architektúra | MVVM (Model-View-ViewModel) |
| 🖼️ UI Framework | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ Dependency Injection | [Koin 4.0](https://insert-koin.io/) |
| 🔄 Állapotkezelés | StateFlow & SharedFlow |
| 🧭 Navigáció | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 Coroutines | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 Szerializáció | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 Helyi tárolás | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 Tesztelés | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 Szint útmutató
Nézze meg a [Teljes megoldási útmutatót](../WALKTHROUGH.md) az összes szint megoldásához.

## 📱 Képernyőképek
<table style="width:100%">
  <tr>
    <th align="center">Indítóképernyő</th>
    <th align="center">Szintek listája</th> 
    <th align="center">Játék felület</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · ❤️-vel fejlesztve, Jetpack Compose használatával.

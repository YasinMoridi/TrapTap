# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [العربية](README.ar.md) | [More...](../README.md)

---

## 📜 תיאור
**TrollMind** (הידוע גם בשם **TrapTap**) הוא משחק פאזל מודרני ושובב שנועד לאתגר את חוכמתכם מול טרול שובב.
האפליקציה נבנתה באופן טבעי (Native) לאנדרואיד באמצעות **Jetpack Compose** ו-**Material 3**, ומציעה ממשק משתמש תוסס ואינטראקטיבי עם אנימציות חלקות.
הפרויקט עוקב אחר ארכיטקטורת **MVVM (Model-View-ViewModel)** ומשתמש ב-**Koin 4.0** להזרקת תלות חזקה.

## ✨ תכונות
- 😈 **להערים על הטרול:** מכניקת פאזלים אינטראקטיבית עם תגובות דינמיות של הטרול.
- 🎨 **ממשק Material 3:** עיצוב מודרני ורספונסיבי המבוסס על דגמי Figma ברמת דיוק גבוהה.
- ⚡ **אנימציות מתקדמות:** קמע SVG מותאם אישית עם אנימציות צף ודופק.
- 🌓 **ערכות נושא דינמיות:** מעבר חלק בין מצב בהיר לכהה.
- 🌐 **תפוצה עולמית:** תמיכה מלאה ב-**25 שפות** (כולל פרסית, אנגלית, סינית, רוסית, ערבית וכו') עם תמיכה אוטומטית בפריסת RTL.
- 📊 **מעקב אחר התקדמות:** מערכת התקדמות מבוססת שלבים עם לוח בקרה ויזואלי.
- 🏗️ **ארכיטקטורה נקייה:** נבנה עם MVVM, StateFlow ו-Koin 4.0 עבור סטנדרט פיתוח פרימיום.
- 💾 **התמדה חזקה:** ניצול של **Room Database** ו-**DataStore** עבור אחסון נתונים אמין.

## 🛠 נבנה באמצעות

| קטגוריה | טכנולוגיה |
| :--- | :--- |
| 🏛 ארכיטקטורה | MVVM (Model-View-ViewModel) |
| 🖼️ תשתית UI | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ הזרקת תלות | [Koin 4.0](https://insert-koin.io/) |
| 🔄 ניהול מצב | StateFlow & SharedFlow |
| 🧭 ניווט | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 Coroutines | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 סדרתיות | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 אחסון מקומי | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 בדיקות | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 מדריך שלבים
עיינו ב[מדריך המלא לפתרון שלבים](../WALKTHROUGH.md) עבור פתרונות לכל השלבים.

## 📱 צילומי מסך
<table style="width:100%">
  <tr>
    <th align="center">מסך פתיחה</th>
    <th align="center">רשימת שלבים</th> 
    <th align="center">ממשק המשחק</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · פותח עם ❤️ באמצעות Jetpack Compose.

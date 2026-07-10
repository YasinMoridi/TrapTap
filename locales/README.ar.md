# 👹 ترول‌مایند (TrollMind)

[English](README.en.md) | [فارسی](README.fa.md) | [中文](README.zh.md) | [Español](README.es.md) | [Français](README.fr.md) | [More...](../README.md)

---

## 📜 الوصف
**TrollMind** هي لعبة ألغاز حديثة ومرحة مصممة لتحدي ذكائك ضد ترول مشاكس.
تم بناء التطبيق بشكل أصلي لنظام Android باستخدام **Jetpack Compose** و **Material 3**، ويقدم واجهة مستخدم حيوية وتفاعلية مع رسوم متحركة سلسة.
يتبع المشروع هندسة **MVVM (Model-View-ViewModel)** ويستخدم **Koin 4.0** لحقن التبعية القوي، مما يضمن أن تكون قاعدة الكود نظيفة وقابلة للاختبار وقابلة للتوسع.

## ✨ المميزات
- 😈 **التغلب على الترول:** آليات ألغاز تفاعلية مع ردود فعل ديناميكية من الترول.
- 🎨 **واجهة Material 3:** تصميم حديث واستجابة مبنية على نماذج Figma عالية الدقة.
- ⚡ **رسوم متحركة متقدمة:** تميمة SVG مخصصة مع رسوم متحركة للطفو والنبض.
- 🌓 **سمات ديناميكية:** تبديل سلس بين الوضعين الفاتح والداكن.
- 🌐 **انتشار عالمي:** دعم كامل لـ **25 لغة** (بما في ذلك الفارسية والإنجليزية والصينية والروسية والعربية وغيرها) مع دعم تلقائي لتخطيط RTL.
- 📊 **تتبع التقدم:** نظام تقدم قائم على المستويات مع لوحة تحكم مرئية.
- 🏗️ **هندسة نظيفة:** بنيت باستخدام MVVM و StateFlow و Koin 4.0 لمعايير تطوير متميزة.
- 💾 **تخزين قوي:** استخدام **Room Database** و **DataStore** لتخزين بيانات موثوق.

## 🛠 بنيت باستخدام

| الفئة | التكنولوجيا |
| :--- | :--- |
| 🏛 الهندسة المعمارية | MVVM (Model-View-ViewModel) |
| 🖼️ إطار عمل UI | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ حقن التبعية | [Koin 4.0](https://insert-koin.io/) |
| 🔄 إدارة الحالة | StateFlow & SharedFlow |
| 🧭 الملاحة | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 الكوروتين | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 التسلسل | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 التخزين المحلي | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 الاختبار | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 دليل المستويات
تحقق من [دليل الحل الكامل](../WALKTHROUGH.md) لجميع المستويات.

## 📱 لقطات الشاشة
<table style="width:100%">
  <tr>
    <th align="center">شاشة البداية</th>
    <th align="center">شبكة المستويات</th> 
    <th align="center">واجهة اللعبة</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · تم التطوير بكل ❤️ باستخدام Jetpack Compose.

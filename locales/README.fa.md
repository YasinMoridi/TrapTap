# 👹 ترپ‌تپ (ترول‌مایند) | TrapTap (TrollMind)

[English](README.en.md) | [چینی](README.zh.md) | [اسپانیایی](README.es.md) | [فرانسوی](README.fr.md) | [روسی](README.ru.md) | [عربی](README.ar.md) | [بیشتر...](../README.md)

---

## 📜 توضیحات  
**ترپ‌تپ (TrapTap)** که با نام **ترول‌مایند** نیز شناخته می‌شود، یک بازی پازل مدرن و سرگرم‌کننده است که هوش شما را در مقابل یک ترول بازیگوش به چالش می‌کشد.  
این برنامه به صورت بومی (Native) برای اندروید با استفاده از **Jetpack Compose** و **Material 3** ساخته شده و رابط کاربری پویا و جذابی را همراه با انیمیشن‌های روان ارائه می‌دهد.  
پروژه از معماری **MVVM** پیروی می‌کند و از نسخه جدید **Koin 4.0** برای تزریق وابستگی استفاده می‌کند تا کدی تمیز، قابل تست و مقیاس‌پذیر داشته باشد.

## ✨ ویژگی‌ها
- 😈 **فریب ترول:** مکانیسم‌های تعاملی پازل با واکنش‌های داینامیک ترول.
- 🎨 **رابط کاربری متریال ۳:** طراحی مدرن و پاسخگو بر اساس طرح‌های فیگما با جزئیات بالا.
- ⚡ **انیمیشن‌های پیشرفته:** مسکات ترول با رندر اختصاصی SVG و انیمیشن‌های Float و Pulse.
- 🌓 **تم داینامیک:** جابجایی روان بین حالت‌های تیره (Dark) و روشن (Light).
- 🌐 **پشتیبانی جهانی:** پشتیبانی کامل از **۲۵ زبان زنده دنیا** (از جمله فارسی، انگلیسی، چینی، روسی، عربی و ...) با چیدمان خودکار RTL.
- 📊 **پیگیری پیشرفت:** سیستم پیشروی مرحله‌محور با داشبورد بصری.
- 🏗️ **معماری پاک:** ساخته شده با MVVM، StateFlow و Koin 4.0 برای بالاترین استانداردهای توسعه.
- 💾 **پایداری داده‌ها:** استفاده از **Room Database** و **DataStore** برای ذخیره‌سازی مطمئن اطلاعات.

## 🛠 ساخته شده با

| دسته‌بندی | تکنولوژی |
| :--- | :--- |
| 🏛 معماری | MVVM (Model-View-ViewModel) |
| 🖼️ فریم‌ورک UI | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ تزریق وابستگی | [Koin 4.0](https://insert-koin.io/) |
| 🔄 مدیریت وضعیت | StateFlow & SharedFlow |
| 🧭 ناوبری | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 کوروتین‌ها | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 سریال‌سازی | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 ذخیره‌سازی | [Room Database](https://developer.android.com/training/data-storage/room) & DataStore |
| 🧪 تست‌نویسی | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 راهنمای مراحل
نیاز به راهنمایی دارید؟ [راهنمای کامل مراحل](../WALKTHROUGH.md) را برای حل تمام پازل‌ها چک کنید.

## 🚀 توسعه
### 🛠 اجرای برنامه
۱. مخزن (Repository) را کلون کنید.  
۲. پروژه را در **Android Studio Ladybug** یا نسخه‌های جدیدتر باز کنید.  
۳. گریدل را سینک کرده و ماژول `:app` را اجرا کنید.

### 🧪 تست‌نویسی
اجرای تست‌های واحد و رابط کاربری:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📱 اسکرین‌شات‌ها
<table style="width:100%">
  <tr>
    <th align="center">صفحه ورودی</th>
    <th align="center">لیست مراحل</th> 
    <th align="center">محیط بازی</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**ترول‌مایند** · توسعه داده شده با ❤️ با استفاده از Jetpack Compose.

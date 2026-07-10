# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [中文](README.zh.md) | [日本語](README.ja.md) | [More...](../README.md)

---

## 📜 คำอธิบาย
**TrollMind** (หรือที่รู้จักในชื่อ **TrapTap**) เป็นเกมพัซเซิลสมัยใหม่ที่ออกแบบมาเพื่อท้าทายไหวพริบของคุณกับโทรลล์จอมซน
สร้างขึ้นสำหรับ Android โดยเฉพาะโดยใช้ **Jetpack Compose** และ **Material 3** แอปนี้นำเสนอ UI ที่มีชีวิตชีวาและโต้ตอบได้พร้อมแอนิเมชันที่ลื่นไหล
โปรเจกต์นี้ใช้สถาปัตยกรรม **MVVM (Model-View-ViewModel)** และใช้ **Koin 4.0** สำหรับการจัดการ Dependency Injection

## ✨ คุณสมบัติ
- 😈 **ฉลาดกว่าโทรลล์:** กลไกพัซเซิลแบบโต้ตอบพร้อมปฏิกิริยาของโทรลล์ที่เปลี่ยนแปลงตามสถานการณ์
- 🎨 **Material 3 UI:** การออกแบบที่ทันสมัยและตอบสนองตามมาตรฐาน Figma คุณภาพสูง
- ⚡ **แอนิเมชันขั้นสูง:** มาสคอต SVG ที่กำหนดเองพร้อมแอนิเมชันลอยตัวและเต้นเป็นจังหวะ
- 🌓 **ธีมแบบไดนามิก:** การสลับระหว่างโหมดสว่างและโหมดมืดได้อย่างราบรื่น
- 🌐 **รองรับทั่วโลก:** รองรับ **25 ภาษา** (รวมถึงเปอร์เซีย, อังกฤษ, จีน, รัสเซีย, อาหรับ ฯลฯ) พร้อมการรองรับเลย์เอาต์ RTL อัตโนมัติ
- 📊 **การติดตามความคืบหน้า:** ระบบการเล่นตามระดับพร้อมแดชบอร์ดที่สวยงาม
- 🏗️ **สถาปัตยกรรมที่สะอาด:** สร้างด้วย MVVM, StateFlow และ Koin 4.0 เพื่อมาตรฐานการพัฒนาระดับพรีเมียม
- 💾 **การเก็บข้อมูลที่ทนทาน:** ใช้ **Room Database** และ **DataStore** สำหรับการจัดเก็บข้อมูลที่เชื่อถือได้

## 🛠 สร้างด้วย

| หมวดหมู่ | เทคโนโลยี |
| :--- | :--- |
| 🏛 สถาปัตยกรรม | MVVM (Model-View-ViewModel) |
| 🖼️ UI Framework | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ Dependency Injection | [Koin 4.0](https://insert-koin.io/) |
| 🔄 การจัดการสถานะ | StateFlow & SharedFlow |
| 🧭 การนำทาง | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 Coroutines | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 Serialization | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 พื้นที่จัดเก็บข้อมูล | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 การทดสอบ | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 คู่มือระดับ
ตรวจสอบ [คู่มือการเล่นแบบละเอียด](../WALKTHROUGH.md) สำหรับวิธีแก้ปัญหาในทุกระดับ

## 📱 ภาพหน้าจอ
<table style="width:100%">
  <tr>
    <th align="center">หน้าจอเริ่มเกม</th>
    <th align="center">รายการระดับ</th> 
    <th align="center">หน้าจอเกม</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · พัฒนาด้วย ❤️ โดยใช้ Jetpack Compose

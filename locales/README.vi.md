# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [中文](README.zh.md) | [日本語](README.ja.md) | [More...](../README.md)

---

## 📜 Mô tả
**TrollMind** (còn được gọi là **TrapTap**) là một trò chơi giải đố hiện đại và tinh nghịch được thiết kế để thử thách trí thông minh của bạn chống lại một con troll láu lỉnh.
Được xây dựng nguyên bản cho Android bằng **Jetpack Compose** và **Material 3**, ứng dụng mang đến giao diện người dùng sống động và tương tác với các hiệu ứng hoạt ảnh mượt mà.
Dự án tuân theo kiến trúc **MVVM (Model-View-ViewModel)** và sử dụng **Koin 4.0** để tiêm phụ thuộc mạnh mẽ.

## ✨ Tính năng
- 😈 **Thông minh hơn Troll:** Cơ chế giải đố tương tác với các phản ứng động của troll.
- 🎨 **Material 3 UI:** Thiết kế hiện đại, phản hồi nhanh dựa trên các bản thiết kế Figma độ phân giải cao.
- ⚡ **Hoạt ảnh nâng cao:** Linh vật SVG tùy chỉnh với các hiệu ứng Float và Pulse.
- 🌓 **Chủ đề động:** Chuyển đổi mượt mà giữa chế độ Sáng và Tối.
- 🌐 **Hỗ trợ toàn cầu:** Hỗ trợ đầy đủ **25 ngôn ngữ** (bao gồm tiếng Ba Tư, tiếng Anh, tiếng Trung, tiếng Nga, tiếng Ả Rập, v.v.) với hỗ trợ bố cục RTL tự động.
- 📊 **Theo dõi tiến trình:** Hệ thống thăng cấp dựa trên cấp độ với bảng điều khiển trực quan.
- 🏗️ **Kiến trúc sạch:** Được xây dựng với MVVM, StateFlow và Koin 4.0 cho tiêu chuẩn phát triển cao cấp.
- 💾 **Lưu trữ mạnh mẽ:** Tận dụng **Room Database** và **DataStore** để lưu trữ dữ liệu đáng tin có.

## 🛠 Được xây dựng với

| Danh mục | Công nghệ |
| :--- | :--- |
| 🏛 Kiến trúc | MVVM (Model-View-ViewModel) |
| 🖼️ UI Framework | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ Dependency Injection | [Koin 4.0](https://insert-koin.io/) |
| 🔄 Quản lý trạng thái | StateFlow & SharedFlow |
| 🧭 Điều hướng | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 Coroutines | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 Tuần tự hóa | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 Lưu trữ cục bộ | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 Kiểm thử | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 Hướng dẫn cấp độ
Xem [Hướng dẫn giải đố đầy đủ](../WALKTHROUGH.md) để biết giải pháp cho tất cả các cấp độ.

## 📱 Ảnh chụp màn hình
<table style="width:100%">
  <tr>
    <th align="center">Màn hình chờ</th>
    <th align="center">Danh sách cấp độ</th> 
    <th align="center">Giao diện trò chơi</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · Được phát triển với ❤️ bằng Jetpack Compose.

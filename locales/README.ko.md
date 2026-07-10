# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [中文](README.zh.md) | [日本語](README.ja.md) | [More...](../README.md)

---

## 📜 설명
**TrollMind** (일명 **TrapTap**)는 장난기 가득한 트롤을 상대로 지혜를 겨루는 현대적인 퍼즐 게임입니다.
**Jetpack Compose**와 **Material 3**를 사용하여 Android용으로 네이티브 제작되었으며, 부드러운 애니메이션과 생동감 넘치는 인터랙티브 UI를 제공합니다.
이 프로젝트는 **MVVM (Model-View-ViewModel)** 아키텍처를 따르며, **Koin 4.0**을 사용하여 강력한 의존성 주입을 구현함으로써 코드베이스의 청결함, 테스트 가능성 및 확장성을 보장합니다.

## ✨ 주요 기능
- 😈 **트롤을 이겨라:** 트롤의 역동적인 반응이 포함된 인터랙티브 퍼즐 역학.
- 🎨 **Material 3 UI:** 고정밀 Figma 디자인을 기반으로 한 현대적인 반응형 디자인.
- ⚡ **고급 애니메이션:** 부유 및 펄스 애니메이션이 적용된 커스텀 SVG 마스코트.
- 🌓 **다이내믹 테마:** 라이트 모드와 다크 모드 간의 원활한 전환.
- 🌐 **글로벌 지원:** 자동 RTL 레이아웃 지원을 포함한 **25개 언어**(페르시아어, 영어, 중국어, 러시아어, 아랍어 등) 완벽 지원.
- 📊 **진행 상황 추적:** 시각적 대시보드를 갖춘 레벨 기반 진행 시스템.
- 🏗️ **클린 아키텍처:** 프리미엄 개발 표준을 위해 MVVM, StateFlow, Koin 4.0으로 구축.
- 💾 **강력한 데이터 유지:** 신뢰할 수 있는 데이터 저장을 위해 **Room Database** 및 **DataStore** 활용.

## 🛠 사용된 기술

| 카테고리 | 기술 |
| :--- | :--- |
| 🏛 아키텍처 | MVVM (Model-View-ViewModel) |
| 🖼️ UI 프레임워크 | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ 의존성 주입 | [Koin 4.0](https://insert-koin.io/) |
| 🔄 상태 관리 | StateFlow & SharedFlow |
| 🧭 네비게이션 | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 코루틴 | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 직렬화 | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 로컬 저장소 | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 테스트 | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 레벨 가이드
모든 레벨의 솔루션은 [전체 공략 가이드](../WALKTHROUGH.md)를 확인하세요.

## 📱 스크린샷
<table style="width:100%">
  <tr>
    <th align="center">스플래시 화면</th>
    <th align="center">레벨 그리드</th> 
    <th align="center">게임 UI</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · Jetpack Compose를 사용하여 ❤️로 제작되었습니다.

# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [中文](README.zh.md) | [한국어](README.ko.md) | [More...](../README.md)

---

## 📜 説明
**TrollMind**（別名 **TrapTap**）は、いたずら好きなトロールを相手に知恵を絞る、モダンなパズルゲームです。
**Jetpack Compose**と**Material 3**を使用してAndroid向けにネイティブ構築されており、滑らかなアニメーションを備えた鮮やかでインタラクティブなUIを提供します。
このプロジェクトは **MVVM (Model-View-ViewModel)** アーキテクチャに従い、**Koin 4.0** を使用して堅牢な依存関係注入を実現しており、クリーンでテスト可能、かつ拡張性の高いコードベースを保証します。

## ✨ 特徴
- 😈 **トロールを出し抜く:** トロールの動的な反応を楽しめるインタラクティブなパズル。
- 🎨 **Material 3 UI:** Figmaに基づいたモダンでレスポンシブなデザイン。
- ⚡ **高度なアニメーション:** フロートおよびパルスアニメーションを備えたカスタムSVGマスコット。
- 🌓 **ダイナミックなテーマ:** ライトモードとダークモードのシームレスな切り替え。
- 🌐 **グローバル対応:** 自動RTLレイアウト対応で、**25言語**（ペルシア語、英語、中国語、ロシア語、アラビア語など）をフルサポート。
- 📊 **進捗状況の追跡:** 視覚的なダッシュボードを備えたレベルベースの進行システム。
- 🏗️ **クリーンアーキテクチャ:** プレミアムな開発基準のためにMVVM、StateFlow、Koin 4.0を採用。
- 💾 **堅牢な永続性:** 信頼性の高いデータストレージのために **Room Database** と **DataStore** を活用。

## 🛠 開発技術

| カテゴリ | テクノロジー |
| :--- | :--- |
| 🏛 アーキテクチャ | MVVM (Model-View-ViewModel) |
| 🖼️ UIフレームワーク | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ 依存関係注入 | [Koin 4.0](https://insert-koin.io/) |
| 🔄 状態管理 | StateFlow & SharedFlow |
| 🧭 ナビゲーション | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 コルーチン | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 シリアライズ | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 ローカルストレージ | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 テスト | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 レベルガイド
全レベルの解決策については、[完全攻略ガイド](../WALKTHROUGH.md)をご覧ください。

## 📱 スクリーンショット
<table style="width:100%">
  <tr>
    <th align="center">スプラッシュ画面</th>
    <th align="center">レベル選択</th> 
    <th align="center">ゲーム画面</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · Jetpack Composeを使用して❤️で開発。

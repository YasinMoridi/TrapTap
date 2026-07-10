# 👹 TrollMind (TrapTap)

[English](README.en.md) | [فارسی](README.fa.md) | [Deutsch](README.de.md) | [Русский](README.ru.md) | [Français](README.fr.md) | [More...](../README.md)

---

## 📜 Açıklama
**TrollMind** (aynı zamanda **TrapTap** olarak da bilinir), yaramaz bir trole karşı zekanızı test etmek için tasarlanmış, modern bir bulmaca oyunudur.
**Jetpack Compose** ve **Material 3** kullanılarak Android için yerel olarak geliştirilen uygulama, akıcı animasyonlarla canlı ve etkileşimli bir kullanıcı arayüzü sunar.
Proje, **MVVM (Model-View-ViewModel)** mimarisini takip eder ve temiz, test edilebilir ve ölçeklenebilir bir kod tabanı sağlamak için **Koin 4.0** kullanır.

## ✨ Özellikler
- 😈 **Trolü Alt Et:** Dinamik trol tepkileriyle etkileşimli bulmaca mekanikleri.
- 🎨 **Material 3 UI:** Yüksek sadakatli Figma tasarımlarına dayalı modern, duyarlı tasarım.
- ⚡ **Gelişmiş Animasyonlar:** Float ve Pulse animasyonlarına sahip özel SVG Maskot.
- 🌓 **Dinamik Temalandırma:** Açık ve Koyu modlar arasında sorunsuz geçiş.
- 🌐 **Küresel Erişim:** Otomatik RTL düzen desteği ile **25 dil** (Farsça, İngilizce, Çince, Rusça, Arapça vb. dahil) desteği.
- 📊 **İlerleme Takibi:** Görsel bir panel ile seviye tabanlı ilerleme sistemi.
- 🏗️ **Temiz Mimari:** Üst düzey geliştirme standartları için MVVM, StateFlow ve Koin 4.0 ile inşa edildi.
- 💾 **Güçlü Veri Depolama:** Güvenilir veri depolama için **Room Database** ve **DataStore** kullanımı.

## 🛠 İle İnşa Edildi

| Kategori | Teknoloji |
| :--- | :--- |
| 🏛 Mimari | MVVM (Model-View-ViewModel) |
| 🖼️ UI Çerçevesi | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) |
| 🛠️ Bağımlılık Enjeksiyonu | [Koin 4.0](https://insert-koin.io/) |
| 🔄 Durum Yönetimi | StateFlow & SharedFlow |
| 🧭 Navigasyon | [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) |
| 🔄 Coroutines | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| 📦 Serileştirme | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) |
| 📦 Yerel Depolama | [Room Database](https://developer.android.com/training/data-storage/room) & [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) |
| 🧪 Test Etme | [JUnit 4](https://junit.org/junit4/), [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine) |

## 🎮 Seviye Rehberi
Tüm seviyelerin çözümleri için [Tam Çözüm Rehberi](../WALKTHROUGH.md)'ne göz atın.

## 🚀 Geliştirme
### 🛠 Uygulamayı Çalıştırın
1. Depoyu klonlayın.
2. Projeyi **Android Studio Ladybug** veya daha yeni bir sürümde açın.
3. Gradle'ı senkronize edin ve `:app` modülünü çalıştırın.

### 🧪 Test Etme
Birim ve UI testlerini çalıştırın:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📱 Ekran Görüntüleri
<table style="width:100%">
  <tr>
    <th align="center">Açılış Ekranı</th>
    <th align="center">Bölüm Listesi</th> 
    <th align="center">Oyun Arayüzü</th> 
  </tr>
  <tr>
    <td align="center"><img src="../screenshot/splash.png" width=240/></td> 
    <td align="center"><img src="../screenshot/levels.png" width=240/></td>
    <td align="center"><img src="../screenshot/game.png" width=240/></td>
  </tr>
</table>

---
**TrollMind** · Jetpack Compose kullanılarak ❤️ ile geliştirildi.

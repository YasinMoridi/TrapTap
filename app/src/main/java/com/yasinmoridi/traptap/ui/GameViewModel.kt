package com.yasinmoridi.traptap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.ui.levels.util.LevelAction
import com.yasinmoridi.traptap.ui.levels.util.LevelLogicDispatcher
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings
import com.yasinmoridi.traptap.ui.util.ChineseStrings
import com.yasinmoridi.traptap.ui.util.RussianStrings
import com.yasinmoridi.traptap.ui.util.GermanStrings
import com.yasinmoridi.traptap.ui.util.HindiStrings
import com.yasinmoridi.traptap.ui.util.ArabicStrings
import com.yasinmoridi.traptap.ui.util.FrenchStrings
import com.yasinmoridi.traptap.ui.util.SpanishStrings
import com.yasinmoridi.traptap.ui.util.HebrewStrings
import com.yasinmoridi.traptap.ui.util.TurkishStrings
import com.yasinmoridi.traptap.ui.util.VietnameseStrings
import com.yasinmoridi.traptap.ui.util.PortugueseStrings
import com.yasinmoridi.traptap.ui.util.JapaneseStrings
import com.yasinmoridi.traptap.ui.util.KoreanStrings
import com.yasinmoridi.traptap.ui.util.ItalianStrings
import com.yasinmoridi.traptap.ui.util.IndonesianStrings
import com.yasinmoridi.traptap.ui.util.ThaiStrings
import com.yasinmoridi.traptap.ui.util.PolishStrings
import com.yasinmoridi.traptap.ui.util.DutchStrings
import com.yasinmoridi.traptap.ui.util.UkrainianStrings
import com.yasinmoridi.traptap.ui.util.CzechStrings
import com.yasinmoridi.traptap.ui.util.RomanianStrings
import com.yasinmoridi.traptap.ui.util.HungarianStrings
import com.yasinmoridi.traptap.ui.util.SwedishStrings
import com.yasinmoridi.traptap.util.AppConstants
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ویومدل اصلی بازی.
 * مدیریت منطق برنامه، ارتباط با دیتابیس و بروزرسانی وضعیت رابط کاربری (UI State) بر عهده این کلاس است.
 */
class GameViewModel(
    private val repository: GameRepository,
    private val levelLogicDispatcher: LevelLogicDispatcher
) : ViewModel() {

    // وضعیت داخلی برنامه که فقط در ویومدل قابل تغییر است
    private val _uiState = MutableStateFlow(GameState())
    // وضعیت عمومی که لایه UI فقط می‌تواند آن را بخواند
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        // شروع مشاهده تغییرات تنظیمات و مراحل به محض ساخته شدن ویومدل
        observeSettings()
        observeLevels()
    }


     // گوش دادن به تغییرات تنظیمات (زبان، تم و ...) از DataStore
    private fun observeSettings() {
        // مشاهده تغییرات تم
        repository.isDarkMode().onEach { isDark ->
            _uiState.update { it.copy(isDarkMode = isDark) }
        }.launchIn(viewModelScope)

        // مشاهده تغییرات زبان
        repository.getLanguage().onEach { lang ->
            val strings = when (lang) {
                AppConstants.LANG_FA -> PersianStrings
                AppConstants.LANG_ZH -> ChineseStrings
                AppConstants.LANG_RU -> RussianStrings
                AppConstants.LANG_DE -> GermanStrings
                AppConstants.LANG_HI -> HindiStrings
                AppConstants.LANG_AR -> ArabicStrings
                AppConstants.LANG_FR -> FrenchStrings
                AppConstants.LANG_ES -> SpanishStrings
                AppConstants.LANG_HE -> HebrewStrings
                AppConstants.LANG_TR -> TurkishStrings
                AppConstants.LANG_VI -> VietnameseStrings
                AppConstants.LANG_PT -> PortugueseStrings
                AppConstants.LANG_JA -> JapaneseStrings
                AppConstants.LANG_KO -> KoreanStrings
                AppConstants.LANG_IT -> ItalianStrings
                AppConstants.LANG_ID -> IndonesianStrings
                AppConstants.LANG_TH -> ThaiStrings
                AppConstants.LANG_PL -> PolishStrings
                AppConstants.LANG_NL -> DutchStrings
                AppConstants.LANG_UK -> UkrainianStrings
                AppConstants.LANG_CS -> CzechStrings
                AppConstants.LANG_RO -> RomanianStrings
                AppConstants.LANG_HU -> HungarianStrings
                AppConstants.LANG_SV -> SwedishStrings
                else -> EnglishStrings
            }
            _uiState.update { it.copy(strings = strings) }
        }.launchIn(viewModelScope)

        // مشاهده تغییرات سکه
        repository.getCoins().onEach { coins ->
            _uiState.update { it.copy(coins = coins) }
        }.launchIn(viewModelScope)

        // مشاهده وضعیت تله مرحله ۲
        repository.isLevel2TrapActive().onEach { isActive ->
            if (isActive) checkLevel2Win()
        }.launchIn(viewModelScope)
    }


     // منطق پیروزی در مرحله ۲: اگر کاربر از اپ خارج شده باشد.
    private fun checkLevel2Win() {
        viewModelScope.launch {
            val levels = repository.getAllLevels().first()
            val level2 = levels.find { it.id == 2 }
            if (level2?.state == AppConstants.STATE_CURRENT) {
                // مرحله ۲ را تمام کن و مرحله ۳ را باز کن
                repository.updateLevel(LevelEntity(2, AppConstants.STATE_COMPLETED, 3))
                repository.updateLevel(LevelEntity(3, AppConstants.STATE_CURRENT))
                
                // ریست کردن وضعیت تله
                repository.setLevel2TrapActive(false)
            }
        }
    }


     // مشاهده تغییرات مراحل بازی
    private fun observeLevels() {
        repository.getAllLevels().onEach { entities ->
            if (entities.isEmpty()) {
                // اگر دیتابیس خالی بود، مراحل اولیه را بساز
                initLevels()
            } else {
                // تبدیل مدل‌های دیتابیس به مدل‌های قابل نمایش در UI
                val levels = entities.map {
                    LevelData(it.id, LevelState.valueOf(it.state), it.stars, it.emoji)
                }
                _uiState.update { it.copy(levels = levels) }
            }
        }.launchIn(viewModelScope)
    }


    // ساخت مراحل اولیه بازی برای بار اول.
    private fun initLevels() {
        viewModelScope.launch {
            val initial = (1..AppConstants.MAX_LEVEL).map { 
                LevelEntity(it, if (it == 1) AppConstants.STATE_CURRENT else AppConstants.STATE_LOCKED, emoji = when(it) {
                    2 -> "🚪"
                    3 -> "🎚️"
                    4 -> AppConstants.ICON_DEFAULT_PUZZLE
                    5 -> "⏳"
                    6 -> "🏳️"
                    7 -> "🙃"
                    8 -> "🔊"
                    9 -> "☀️"
                    10 -> "🖐️"
                    11 -> "🤌"
                    12 -> "😫"
                    else -> AppConstants.ICON_DEFAULT_PUZZLE
                })
            }
            repository.insertLevels(initial)
        }
    }


    // مدیریت تمام تعاملات کاربر در بازی
    fun handleAction(action: LevelAction) {
        // مدیریت اکشن‌های خاص
        when (action) {
            is LevelAction.MoveExitButton -> {
                viewModelScope.launch {
                    if (!repository.isLevel2TrapActive().first()) {
                        repository.setLevel2TrapActive(true)
                    }
                }
            }
            is LevelAction.NextLevel -> {
                onNextLevel()
                return
            }
            is LevelAction.ToggleHintDialog -> {
                _uiState.update { it.copy(showHintDialog = !it.showHintDialog) }
                return
            }
            is LevelAction.PurchaseHint -> {
                purchaseHint(action.level)
                return
            }
            else -> {}
        }

        // سپردن منطق جزئی هر مرحله به کلاس Dispatcher
        levelLogicDispatcher.dispatch(
            action = action,
            state = _uiState.value,
            scope = viewModelScope,
            onUpdate = { newState ->
                _uiState.value = newState
            }
        )
    }


    // تنظیم زبان برنامه
    fun setLanguage(lang: String) {
        viewModelScope.launch {
            repository.setLanguage(lang)
        }
    }

    // تغییر زبان برنامه (چرخش بین زبان‌ها)
    fun toggleLanguage() {
        viewModelScope.launch {
            val languages = AppConstants.AVAILABLE_LANGUAGES
            val currentLang = repository.getLanguage().first()
            val currentIndex = languages.indexOf(currentLang)
            val nextIndex = (currentIndex + 1) % languages.size
            repository.setLanguage(languages[nextIndex])
        }
    }


     // تغییر تم برنامه (تیره / روشن)
    fun toggleTheme() {
        viewModelScope.launch {
            val isDark = repository.isDarkMode().first()
            repository.setDarkMode(!isDark)
        }
    }


     // جابجایی بین صفحات مختلف

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen, showSuccessDialog = false) }
    }


     // انتخاب یک مرحله برای شروع بازی

    fun selectLevel(level: LevelData) {
        if (level.state != LevelState.Locked) {
            _uiState.update { 
                it.copy(
                    currentLevel = level,
                    currentScreen = Screen.Game,
                    selectedOption = null,
                    isAnswered = false,
                    showHint = false,
                    unlockedHintLevel = 0,
                    showSuccessDialog = false,
                    exitButtonOffset = Pair(0f, 0f),
                    sliderValue = 0f,
                    questionOffset = Pair(0f, 0f),
                    timer = 10,
                    isTimerRunning = false,
                    buttonTapCount = 0,
                    holdProgress = 0f,
                    pinchScale = 1f
                )
            }
            
            // تله مرحله ۲: به محض انتخاب مرحله، وضعیت شروع را ذخیره کن
            if (level.id == 2) {
                viewModelScope.launch {
                    repository.setLevel2TrapActive(true)
                }
            }
        }
    }


     // رفتن به مرحله بعدی بعد از پیروزی
    fun onNextLevel() {
        val currentId = _uiState.value.currentLevel?.id ?: return
        viewModelScope.launch {
            // چک کن اگر اولین بار است که مرحله تمام می‌شود، سکه بده
            val levels = repository.getAllLevels().first()
            val currentLevelEntity = levels.find { it.id == currentId }
            if (currentLevelEntity?.state != AppConstants.STATE_COMPLETED) {
                addCoins(AppConstants.WIN_REWARD)
            }

            // مارک کردن مرحله فعلی به عنوان تمام شده
            repository.updateLevel(LevelEntity(currentId, AppConstants.STATE_COMPLETED, 3))
            
            // باز کردن مرحله بعدی
            val nextId = currentId + 1
            if (nextId <= AppConstants.MAX_LEVEL) {
                repository.updateLevel(LevelEntity(nextId, AppConstants.STATE_CURRENT))
                val nextLevel = _uiState.value.levels.find { it.id == nextId }?.copy(state = LevelState.Current)
                _uiState.update { 
                    it.copy(
                        currentLevel = nextLevel, 
                        showSuccessDialog = false, 
                        isAnswered = false, 
                        selectedOption = null,
                        showHint = false,
                        showHintDialog = false,
                        exitButtonOffset = Pair(0f, 0f),
                        sliderValue = 0f,
                        questionOffset = Pair(0f, 0f),
                        timer = 10,
                        isTimerRunning = false,
                        buttonTapCount = 0,
                        holdProgress = 0f,
                        pinchScale = 1f
                    ) 
                }
            } else {
                // اگر مرحله‌ای باقی نمانده، به لیست مراحل برگرد
                navigateTo(Screen.Levels)
            }
        }
    }


     // اضافه کردن سکه

    private fun addCoins(amount: Int) {
        viewModelScope.launch {
            val currentCoins = repository.getCoins().first()
            repository.saveCoins(currentCoins + amount)
        }
    }

     // خرید راهنمایی یا رد کردن مرحله با سکه
    private fun purchaseHint(purchaseType: Int) {
        viewModelScope.launch {
            val currentCoins = repository.getCoins().first()
            val cost = when (purchaseType) {
                1 -> AppConstants.COST_SIMPLE_HINT
                2 -> AppConstants.COST_DETAILED_HINT
                3 -> AppConstants.COST_SKIP_LEVEL
                else -> 0
            }

            if (currentCoins >= cost) {
                repository.saveCoins(currentCoins - cost)
                when (purchaseType) {
                    1 -> _uiState.update { it.copy(showHint = true, unlockedHintLevel = 1, showHintDialog = false) }
                    2 -> _uiState.update { it.copy(showHint = true, unlockedHintLevel = 2, showHintDialog = false) }
                    3 -> {
                        _uiState.update { it.copy(showHintDialog = false) }
                        onNextLevel() // رد کردن مرحله
                    }
                }
            } else {
                _uiState.update { it.copy(showHintDialog = false) }
            }
        }
    }


     // نمایش یا مخفی کردن راهنمایی مرحله
    fun toggleHint() {
        _uiState.update { it.copy(showHint = !it.showHint) }
    }


     // شروع مجدد مرحله فعلی و ریست کردن تمام وضعیت‌ها
    fun restartLevel() {
        _uiState.update { 
            it.copy(
                selectedOption = null,
                isAnswered = false,
                showHint = false,
                showSuccessDialog = false,
                exitButtonOffset = Pair(0f, 0f),
                sliderValue = 0f,
                questionOffset = Pair(0f, 0f),
                timer = 10,
                isTimerRunning = false,
                buttonTapCount = 0,
                holdProgress = 0f,
                pinchScale = 1f
            )
        }
    }

    // متدهای مربوط به سنسورها که از اکتیویتی صدا زده می‌شوند
    fun onGravityChanged(x: Float, y: Float, z: Float) {
        handleAction(LevelAction.GravityChanged(x, y, z))
    }

    fun onVolumeChanged(current: Int, max: Int) {
        handleAction(LevelAction.VolumeChanged(current, max))
    }

    fun onBrightnessChanged(value: Int) {
        handleAction(LevelAction.BrightnessChanged(value))
    }
}

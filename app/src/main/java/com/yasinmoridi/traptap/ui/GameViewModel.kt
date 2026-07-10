package com.yasinmoridi.traptap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmoridi.traptap.data.db.LevelEntity
import com.yasinmoridi.traptap.data.db.SettingsEntity
import com.yasinmoridi.traptap.data.repository.GameRepository
import com.yasinmoridi.traptap.ui.levels.util.LevelAction
import com.yasinmoridi.traptap.ui.levels.util.LevelLogicDispatcher
import com.yasinmoridi.traptap.ui.util.EnglishStrings
import com.yasinmoridi.traptap.ui.util.PersianStrings
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

    companion object {
        const val MAX_LEVEL = 12 // حداکثر تعداد مراحل بازی
    }

    // وضعیت داخلی برنامه که فقط در ویومدل قابل تغییر است
    private val _uiState = MutableStateFlow(GameState())
    // وضعیت عمومی که لایه UI فقط می‌تواند آن را بخواند
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        // شروع مشاهده تغییرات تنظیمات و مراحل به محض ساخته شدن ویومدل
        observeSettings()
        observeLevels()
    }

    /**
     * گوش دادن به تغییرات تنظیمات (زبان، تم و ...) در دیتابیس.
     */
    private fun observeSettings() {
        repository.getSettings().onEach { entity ->
            entity?.let {
                _uiState.update { state ->
                    state.copy(
                        isDarkMode = it.isDarkMode,
                        strings = if (it.language == AppConstants.LANG_FA) PersianStrings else EnglishStrings
                    )
                }
                
                // بررسی وضعیت خاص مرحله ۲ (تله دکمه خروج)
                if (it.level2TrapStarted) {
                    checkLevel2Win()
                }
            } ?: run {
                // اگر تنظیماتی وجود نداشت، زبان سیستم را چک کن
                val systemLang = java.util.Locale.getDefault().language
                if (systemLang == AppConstants.LANG_FA) toggleLanguage()
            }
        }.launchIn(viewModelScope)
    }

    /**
     * منطق پیروزی در مرحله ۲: اگر کاربر از اپ خارج شده باشد (وضعیت ذخیره شده در دیتابیس).
     */
    private fun checkLevel2Win() {
        viewModelScope.launch {
            val levels = repository.getAllLevels().first()
            val level2 = levels.find { it.id == 2 }
            if (level2?.state == AppConstants.STATE_CURRENT) {
                // مرحله ۲ را تمام کن و مرحله ۳ را باز کن
                repository.updateLevel(LevelEntity(2, AppConstants.STATE_COMPLETED, 3))
                repository.updateLevel(LevelEntity(3, AppConstants.STATE_CURRENT))
                
                // ریست کردن وضعیت تله در تنظیمات
                val currentSettings = repository.getSettings().first()
                currentSettings?.let {
                    repository.saveSettings(it.copy(level2TrapStarted = false))
                }
            }
        }
    }

    /**
     * مشاهده تغییرات مراحل بازی.
     */
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

    /**
     * ساخت مراحل اولیه بازی برای بار اول.
     */
    private fun initLevels() {
        viewModelScope.launch {
            val initial = (1..MAX_LEVEL).map { 
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

    /**
     * مدیریت تمام تعاملات کاربر در بازی.
     */
    fun handleAction(action: LevelAction) {
        // مدیریت اکشن‌های خاص که نیاز به دیتابیس دارند
        when (action) {
            is LevelAction.MoveExitButton -> {
                viewModelScope.launch {
                    val settings = repository.getSettings().first() ?: SettingsEntity()
                    if (!settings.level2TrapStarted) {
                        repository.saveSettings(settings.copy(level2TrapStarted = true))
                    }
                }
            }
            is LevelAction.NextLevel -> {
                onNextLevel()
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

    /**
     * تغییر زبان برنامه بین فارسی و انگلیسی.
     */
    fun toggleLanguage() {
        viewModelScope.launch {
            val current = repository.getSettings().first() ?: SettingsEntity()
            val newLang = if (_uiState.value.strings == EnglishStrings) AppConstants.LANG_FA else AppConstants.LANG_EN
            repository.saveSettings(current.copy(language = newLang))
        }
    }

    /**
     * تغییر تم برنامه (تیره / روشن).
     */
    fun toggleTheme() {
        viewModelScope.launch {
            val current = repository.getSettings().first() ?: SettingsEntity()
            repository.saveSettings(current.copy(isDarkMode = !current.isDarkMode))
        }
    }

    /**
     * جابجایی بین صفحات مختلف.
     */
    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen, showSuccessDialog = false) }
    }

    /**
     * انتخاب یک مرحله برای شروع بازی.
     */
    fun selectLevel(level: LevelData) {
        if (level.state != LevelState.Locked) {
            _uiState.update { 
                it.copy(
                    currentLevel = level,
                    currentScreen = Screen.Game,
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
            
            // تله مرحله ۲: به محض انتخاب مرحله، وضعیت شروع را در تنظیمات ذخیره کن
            if (level.id == 2) {
                viewModelScope.launch {
                    val settings = repository.getSettings().first() ?: SettingsEntity()
                    repository.saveSettings(settings.copy(level2TrapStarted = true))
                }
            }
        }
    }

    /**
     * رفتن به مرحله بعدی بعد از پیروزی.
     */
    fun onNextLevel() {
        val currentId = _uiState.value.currentLevel?.id ?: return
        viewModelScope.launch {
            // مارک کردن مرحله فعلی به عنوان تمام شده
            repository.updateLevel(LevelEntity(currentId, AppConstants.STATE_COMPLETED, 3))
            
            // باز کردن مرحله بعدی
            val nextId = currentId + 1
            if (nextId <= MAX_LEVEL) {
                repository.updateLevel(LevelEntity(nextId, AppConstants.STATE_CURRENT))
                val nextLevel = _uiState.value.levels.find { it.id == nextId }?.copy(state = LevelState.Current)
                _uiState.update { 
                    it.copy(
                        currentLevel = nextLevel, 
                        showSuccessDialog = false, 
                        isAnswered = false, 
                        selectedOption = null,
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

    /**
     * نمایش یا مخفی کردن راهنمایی مرحله.
     */
    fun toggleHint() {
        _uiState.update { it.copy(showHint = !it.showHint) }
    }

    /**
     * شروع مجدد مرحله فعلی و ریست کردن تمام وضعیت‌ها.
     */
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

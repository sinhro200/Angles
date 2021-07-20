package com.sinhro.angles.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _themeId = MutableLiveData<Int>()

    val themeId: LiveData<Int> = _themeId

    fun setThemeId(themeId: Int) {
        _themeId.postValue(themeId)
    }
}
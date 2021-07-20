package com.sinhro.angles.theme

import com.sinhro.angles.storage.Storage
import java.lang.RuntimeException

class ThemeController(
    private val storage: Storage
) {

    fun setTheme(themeId: Int, ifChanged: () -> Unit = {}) {
        val themeNum = themesToNums[themeId] ?: throw RuntimeException("Cant find theme")

        if (storage.themeNum == themeNum)
            return

        storage.themeNum = themeNum
        ifChanged.invoke()
    }

    fun getTheme(): Int {
        val savedNum = storage.themeNum
        val themeToNum = themesToNums.entries.firstOrNull {
            it.value == savedNum
        } ?: throw RuntimeException("Cant find theme with num $savedNum")
        return themeToNum.key
    }
}
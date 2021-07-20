package com.sinhro.angles.storage

import android.content.Context
import android.util.Log
import android.util.Log.d
import java.util.logging.Logger

class Storage(private val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("Angles_shared_prefs", Context.MODE_PRIVATE)

    enum class Page(val code: Int) {
        None(0),
        TrianglePage(1),
        RotationToolHolderPage(2)
    }

    private var _lastOpenedPage = Page.None

    var lastOpenedPage: Page
        set(value) {
            Log.d("Storage", "Saving Last_opened_page: ${value.code} (${value.name})")
            sharedPreferences.edit()
                .putInt("Last_opened_page", value.code)
                .apply()
            _lastOpenedPage = value
        }
        get() {
            if (_lastOpenedPage == Page.None) {
                val code = sharedPreferences.getInt("Last_opened_page", -1)
                if (code != -1) {
                    Page.values().firstOrNull { it.code == code }?.let {
                        _lastOpenedPage = it
                    }
                }
            }
            return _lastOpenedPage
        }

    private var _style: Int = -1

    var themeNum: Int
        set(value) {
            Log.d("Storage", "Saving Theme: $value")
            sharedPreferences.edit()
                .putInt("Theme", value)
                .apply()
            _style = value
        }
        get() {
            if (_style == -1) {
                val st = sharedPreferences.getInt("Theme", -1)
                _style = if (st == -1) 1 else st
            }
            return _style
        }
}
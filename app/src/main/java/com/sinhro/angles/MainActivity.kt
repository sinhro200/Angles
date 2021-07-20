package com.sinhro.angles

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sinhro.angles.databinding.ActivityMainBinding
import com.sinhro.angles.storage.Storage
import com.sinhro.angles.theme.ThemeController
import com.sinhro.angles.ui.settings.SettingsViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val settingsViewModel: SettingsViewModel by viewModels()
    lateinit var storage: Storage
    lateinit var themeController: ThemeController

    override fun onCreate(savedInstanceState: Bundle?) {
        storage = Storage(baseContext)
        themeController = ThemeController(storage)
        setTheme(themeController.getTheme())

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_triangle,
                R.id.navigation_settings,
                R.id.navigation_rotation_tool_holder
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Если в этом же where сделать выделение нужного item в bottomNavView
        // [ Binding.navView.menu.findItem(R.id.navigation_rotation_tool_holder).isChecked = true ]
        // оно почему то не работает. Т.е. не выделяет нужный элемент
        // поэтому выделение нужной вкладки происходит после смены стиля в storage, но до recreate()
        when (storage.lastOpenedPage) {
            Storage.Page.RotationToolHolderPage -> {
                openPage(R.id.navigation_rotation_tool_holder)
            }
            Storage.Page.TrianglePage -> {
                openPage(R.id.navigation_triangle)
            }
        }

        settingsViewModel.themeId.observe(this) {
            themeController.setTheme(it) {
                when (storage.lastOpenedPage) {
                    Storage.Page.RotationToolHolderPage -> {
                        binding.navView.menu.findItem(R.id.navigation_rotation_tool_holder).isChecked =
                            true
                    }
                    Storage.Page.TrianglePage -> {
                        binding.navView.menu.findItem(R.id.navigation_triangle).isChecked = true
                    }
                }
                recreate()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    private fun openPage(resId: Int) {
        navController.navigate(
            resId,
            null,
            NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_settings -> {
                openPage(R.id.navigation_settings)
                binding.navView.menu.findItem(R.id.invisible).isChecked = true
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }
}
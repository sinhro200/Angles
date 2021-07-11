package com.sinhro.angles

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sinhro.angles.databinding.ActivityMainBinding
import com.sinhro.angles.storage.Storage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var storage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = Storage(baseContext)

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

            when(storage.lastOpenedPage){
            Storage.Page.RotationToolHolderPage->{
                openPage(R.id.navigation_rotation_tool_holder)
            }
            Storage.Page.TrianglePage->{
                openPage(R.id.navigation_triangle)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        //todo settings fragment
//        menuInflater.inflate(R.menu.settings_menu, menu)
//        return true
    }

    private fun openPage(resId: Int) {
        navController.navigate(
            resId,
            null,
            NavOptions.Builder()
                .setLaunchSingleTop(true)
//                        .setRestoreState(true)
//                        .setPopUpTo(
//                            navController.graph.findStartDestination().id,
//                            inclusive = false,
//                            saveState = true
//                        )
                .build()
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_settings -> {
                openPage(R.id.navigation_settings)
//                navController.navigate(
//                    R.id.navigation_settings, null,
//                    NavOptions.Builder()
//                        .setLaunchSingleTop(true)
////                        .setRestoreState(true)
////                        .setPopUpTo(
////                            navController.graph.findStartDestination().id,
////                            inclusive = false,
////                            saveState = true
////                        )
//                        .build()
//                )
                binding.navView.menu.findItem(R.id.invisible).isChecked = true
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }


}
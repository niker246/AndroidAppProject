package com.infinitumcode.mymovieapp.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.util.Constants
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeActivity : DaggerAppCompatActivity(), NavController.OnDestinationChangedListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    var popMenu = Constants.POPULAR_MENU
    var popVisible =Constants.POPULAR_VISIBLE
    var childVisible =Constants.CHILD_VISIBLE
    var childMenu =Constants.CHILD_MENU
    var favoVisible =Constants.FAVORITE_VISIBLE
    var favoMenu =Constants.FAVORITE_MENU

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_popular,
                R.id.navigation_child,
                R.id.navigation_favorite,
                R.id.navigation_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener(this)
        navView.setupWithNavController(navController)
        updateNavViewMenuUI()
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return configureToolbar(menu)
    }

    private fun configureToolbar(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                navController.navigate(R.id.navigation_search)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateNavViewMenuUI() {
        if (sharedPreferences.contains(favoMenu))
            changeMenuLabel(
                R.id.navigation_favorite,
                sharedPreferences.getString(favoMenu, "")!!
            )
        if (sharedPreferences.contains(childMenu))
            changeMenuLabel(R.id.navigation_child, sharedPreferences.getString(childMenu, "")!!)
        if (sharedPreferences.contains(popMenu))
            changeMenuLabel(
                R.id.navigation_popular,
                sharedPreferences.getString(popMenu, "")!!
            )
        if (sharedPreferences.contains(favoVisible))
            changeMenuVisibility(
                R.id.navigation_favorite,
                sharedPreferences.getBoolean(favoVisible, true)
            )
        if (sharedPreferences.contains(childVisible))
            changeMenuVisibility(
                R.id.navigation_child,
                sharedPreferences.getBoolean(childVisible, true)
            )
        if (sharedPreferences.contains(popVisible))
            changeMenuVisibility(
                R.id.navigation_popular,
                sharedPreferences.getBoolean(popVisible, true)
            )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.detail_fragment, R.id.splash_fragment -> {
                toolbar.visibility = View.GONE
            }
            else -> {
                toolbar.visibility = View.VISIBLE
                toolbar.tvToolbarTitle.text = destination.label
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            favoMenu -> changeMenuLabel(
                R.id.navigation_favorite,
                sharedPreferences!!.getString(key, "")!!
            )
            childMenu -> changeMenuLabel(
                R.id.navigation_child,
                sharedPreferences!!.getString(key, "")!!
            )
            popMenu -> changeMenuLabel(
                R.id.navigation_popular,
                sharedPreferences!!.getString(key, "")!!
            )
            favoVisible -> changeMenuVisibility(
                R.id.navigation_favorite,
                sharedPreferences!!.getBoolean(key, true)
            )
            childVisible -> changeMenuVisibility(
                R.id.navigation_child,
                sharedPreferences!!.getBoolean(key, true)
            )
            popVisible -> changeMenuVisibility(
                R.id.navigation_popular,
                sharedPreferences!!.getBoolean(key, true)
            )
        }
    }


    private fun changeMenuLabel(menuId: Int, label: String) {
        val menuItem = nav_view.menu.findItem(menuId)
        menuItem.title = label
    }

    private fun changeMenuVisibility(menuId: Int, visibility: Boolean) {
        val menuItem = nav_view.menu.findItem(menuId)
        menuItem.isVisible = visibility
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


}

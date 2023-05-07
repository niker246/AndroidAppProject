package com.infinitumcode.mymovieapp.view.ui.setting

import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.infinitumcode.mymovieapp.R
import com.infinitumcode.mymovieapp.util.Constants

class SettingsFragment : PreferenceFragmentCompat() {


    private var preferenceList: ListPreference? = null
    private var preferenceCategoryPopular: PreferenceCategory? = null
    private var preferenceCategoryChild: PreferenceCategory? = null
    private var preferenceCategoryFavorite: PreferenceCategory? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIPreferences()
    }

    private fun initUIPreferences() {
        preferenceCategoryFavorite = findPreference(Constants.FAVORITE_MENU_KEY)
        preferenceCategoryChild = findPreference(Constants.CHILD_MENU_KEY)
        preferenceCategoryPopular = findPreference(Constants.POPULAR_MENU_KEY)
        preferenceList = findPreference(Constants.MENU)
        manageVisibilityCategory(preferenceList!!.value)
        preferenceList!!.setOnPreferenceChangeListener { _, newValue ->
            manageVisibilityCategory(newValue)
            true
        }
    }

    private fun manageVisibilityCategory(newValue: Any?) {
        when (newValue) {
            Constants.NAVIGATION_FAVORITE -> showHidePreferenceByIndex(0)
            Constants.NAVIGATION_CHILD -> showHidePreferenceByIndex(1)
            Constants.NAVIGATION_POPULAR -> showHidePreferenceByIndex(2)
        }
    }

    private fun showHidePreferenceByIndex(index: Int) {
        when (index) {
            0 -> {
                preferenceCategoryFavorite!!.isVisible = true
                preferenceCategoryChild!!.isVisible = false
                preferenceCategoryPopular!!.isVisible = false
            }
            1 -> {
                preferenceCategoryFavorite!!.isVisible = false
                preferenceCategoryChild!!.isVisible = true
                preferenceCategoryPopular!!.isVisible = false
            }
            2 -> {
                preferenceCategoryFavorite!!.isVisible = false
                preferenceCategoryChild!!.isVisible = false
                preferenceCategoryPopular!!.isVisible = true
            }
        }
    }
}

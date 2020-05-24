package com.varivoda.igor.zagonetke.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.models.Mailing_list
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.riddle.RiddleViewModel
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: PreferenceFragmentCompat() {

    private val settingsViewModel by viewModel<SettingsViewModel>()
    private val preferences by inject<Preferences>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_preference)

        bindPreferenceSummaryToValue(findPreference(getString(R.string.enable_notifications_resource)))
        ((activity) as NavigationActivity).supportActionBar?.title = getString(R.string.settings_resource)

        val switchPreference = findPreference<SwitchPreference>(getString(R.string.subscribe_to_email_list))
        switchPreference?.setOnPreferenceChangeListener { _, newValue ->
            if(newValue as Boolean){
                settingsViewModel.addUserToMailingList(preferences.getToken(), Mailing_list(preferences.getUser(),preferences.geCurrentUserEmail()))
            }else{
                settingsViewModel.deleteUserFromMailingList(preferences.getToken(),preferences.getUser())
            }
            return@setOnPreferenceChangeListener true

        }
    }

    companion object {
        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->

            val stringValue = value.toString()
            println("debug: value: $value")
            true
        }

        private fun bindPreferenceSummaryToValue(preference: Preference?) {
            preference?.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference?.context)
                    .getBoolean(preference?.key, true))
        }
    }

}
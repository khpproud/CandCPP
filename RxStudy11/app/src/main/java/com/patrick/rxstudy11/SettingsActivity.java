package com.patrick.rxstudy11;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle3.components.preference.RxPreferenceFragmentCompat;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;


/**
 *  설정을 위한 Activity
 */
public class SettingsActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends RxPreferenceFragmentCompat {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences_settings);
        }
    }
}

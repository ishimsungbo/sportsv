package com.sportsv.common;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sportsv.R;

/**
 * Created by sungbo on 2016-06-08.
 */
public class SettingVideoUploadActivity extends PreferenceActivity {

    SwitchPreference  complex_upload;
    SwitchPreference  wifi_upload;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.videoupload_layout);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        complex_upload = (SwitchPreference) findPreference("complex_upload");
        wifi_upload = (SwitchPreference) findPreference("wifi_upload");

        complex_upload.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isVibrateOn = (Boolean) newValue;
                if(isVibrateOn){
                    wifi_upload.setChecked(false);
                }else{
                    wifi_upload.setChecked(true);
                }
                return true;
            }
        });

        wifi_upload.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isVibrateOn = (Boolean) newValue;
                if(isVibrateOn){
                    complex_upload.setChecked(false);
                }else{
                    complex_upload.setChecked(true);
                }
                return true;
            }
        });

    }
}

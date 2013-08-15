package org.codechimp.openliveview;

import java.util.List;

import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

}

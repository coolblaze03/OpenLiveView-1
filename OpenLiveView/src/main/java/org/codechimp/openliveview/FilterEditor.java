package org.codechimp.openliveview;

import org.codechimp.appchooser.AppChooserDialog;
import org.codechimp.appchooser.AppChooserListener;
import org.codechimp.appchooser.AppItem;

import org.codechimp.openliveview.data.Prefs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FilterEditor extends Activity implements AppChooserListener {
    static Prefs prefs;
    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_editor);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.prefs_filtereditor_popup_wait));
        prefs = new Prefs(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = getPackageManager();
        ListView listView = (ListView) findViewById(R.id.mylist);
        String[] values = new String[prefs.getNumberOfFilters()];
        for (int i = 0; i < prefs.getNumberOfFilters(); i++) {
            String appName;
            try {
                appName = pm.getApplicationLabel(pm.getApplicationInfo(prefs.getFilterString(i), 0)).toString();
            } catch (Exception e) {
                appName = "";
            }
            values[i] = appName;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prefs.removeFilterString(position);
                onResume();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filtereditor_menu, menu);
        menu.findItem(R.id.menu_filtereditortoast).setChecked(prefs.getFilterToast());
        menu.findItem(R.id.menu_filtereditortoast).setCheckable(true);
        if (prefs.getFilterToast())
            menu.findItem(R.id.menu_filtereditortoast).setIcon(R.drawable.btn_check_on);
        else menu.findItem(R.id.menu_filtereditortoast).setIcon(R.drawable.btn_check_off);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder;
        switch (item.getItemId()) {
            case R.id.menu_filtereditoraddapp:
                //loadingDialog.show();
                AppChooserDialog.show(this.getBaseContext(), this, this.getString(R.string.title_activity_app_dialog));
                return true;
            case R.id.menu_filtereditortoast:
                prefs.setFilterToast(!prefs.getFilterToast());
                item.setChecked(prefs.getFilterToast());
                if (prefs.getFilterToast())
                    item.setIcon(R.drawable.btn_check_on);
                else item.setIcon(R.drawable.btn_check_off);
                return true;
            case R.id.menu_filtereditormode:
                builder = new AlertDialog.Builder(this);
                builder.setPositiveButton(R.string.prefs_filtereditor_mode_blacklist, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        prefs.setFilterBlacklist(true);
                    }
                });
                builder.setNegativeButton(R.string.prefs_filtereditor_mode_whitelist, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        prefs.setFilterBlacklist(false);
                    }
                });
                builder.setTitle(R.string.prefs_filtereditor_menu_mode);
                builder.setMessage((prefs.getFilterBlacklist() ? R.string.prefs_filtereditor_mode_messageblack
                        : R.string.prefs_filtereditor_mode_messagewhite));
                builder.show();
                return true;
            case R.id.menu_filtereditorhelp:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.prefs_filtereditor_popup_help);
                builder.setPositiveButton("OK", null);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onAppChooserSelected(AppItem value) {
        prefs.addFilterString(value.getPackageName());

    }

    public void onAppChooserCancel() {
        // Do nothing
    }


}

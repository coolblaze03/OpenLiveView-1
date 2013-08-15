package org.codechimp.openliveview;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
//import android.preference.ListPreference;
import android.view.View;
import android.widget.Toast;

import org.codechimp.openliveview.data.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigWizardActivity extends Activity {
    private Context mContext;
    private int selected = -1;
    private AlertDialog devicelist;
    private BluetoothAdapter btAdapter;
    // private ListPreference devicePreference;
    private CharSequence[] choiceList;
    private CharSequence[] valueList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_wizard);
        mContext = this;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            fillDevices();
            if (choiceList.length > 0) {
                boolean found = false;
                for (int i = 0; i < choiceList.length; i++) {
                    Log.w("DEBUG", "Device: " + choiceList[i]);
                    if (choiceList[i].equals("LiveView")) {
                        found = true;
                        // Toast.makeText(mContext, "Autoselect: " +
                        // valueList[i].toString(), Toast.LENGTH_SHORT).show();
                        Prefs prefs = new Prefs(mContext);
                        prefs.setDeviceAddress(valueList[i].toString());
                        prefs.setSetupCompleted(true);
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                this);
                        builder.setTitle(R.string.device_found);
                        builder.setMessage(Html
                                .fromHtml("<strong>Welcome to OpenLiveView!</strong><br />Your LiveView device was automatically detected: it's mac adress is "
                                        + valueList[i].toString()
                                        + ". <br />"
                                        + ""));
                        builder.setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        Intent myIntent = new Intent(mContext,
                                                MainActivity.class);
                                        mContext.startActivity(myIntent);
                                        finish();
                                    }
                                });
                        builder.show();
                    }
                }
                if (found == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.no_device_found);
                    builder.setMessage(R.string.liveview_not_found);
                    builder.setPositiveButton(R.string.ok, null);
                    builder.show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.no_device_found);
                builder.setMessage(R.string.please_pair);
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                openBluetoothSettings(null);
                            }
                        });
                builder.show();
            }
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.not_supported);
            builder.setMessage(R.string.error_not_supported_text);
            // builder.setMessage(e.getMessage());
            builder.setPositiveButton(R.string.ok, null);
            builder.setNegativeButton(R.string.ignore,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            Prefs prefs = new Prefs(mContext);
                            prefs.setSetupCompleted(true);
                            Intent myIntent = new Intent(mContext,
                                    MainActivity.class);
                            mContext.startActivity(myIntent);
                            finish();
                        }
                    });
            builder.show();
        }
    }

    public void finishSetup(View view) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your LiveView device");
            fillDevices();
            selected = -1;
            builder.setSingleChoiceItems(choiceList, selected,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(mContext,
                            // "Select "+choiceList[which],
                            // Toast.LENGTH_SHORT).show();
                            selected = which;
                            devicelist.dismiss();
                            if (selected > -1) {
                                Toast.makeText(
                                        mContext,
                                        "Select "
                                                + choiceList[which]
                                                + " (Set to: "
                                                + valueList[selected]
                                                .toString() + ")",
                                        Toast.LENGTH_SHORT).show();
                                Prefs prefs = new Prefs(mContext);
                                prefs.setDeviceAddress(valueList[selected]
                                        .toString());
                                prefs.setSetupCompleted(true);
                                Intent myIntent = new Intent(mContext,
                                        MainActivity.class);
                                mContext.startActivity(myIntent);
                                finish();
                            }
                        }
                    });
            devicelist = builder.create();
            devicelist.show();
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.not_supported);
            builder.setMessage(R.string.error_not_supported_text);
            builder.setPositiveButton(R.string.ok, null);
            builder.setNegativeButton(R.string.ignore,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            Prefs prefs = new Prefs(mContext);
                            prefs.setSetupCompleted(true);
                            Intent myIntent = new Intent(mContext,
                                    MainActivity.class);
                            mContext.startActivity(myIntent);
                            finish();
                        }
                    });
            builder.show();
        }
    }

    public void openBluetoothSettings(View view) {
        Intent settingsIntent = new Intent(
                android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(settingsIntent);
    }

    private void fillDevices() {
        Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
        List<String> names = new ArrayList<String>();
        List<String> addresses = new ArrayList<String>();
        for (BluetoothDevice dev : devices) {
            names.add(dev.getName());
            addresses.add(dev.getAddress());
        }
        choiceList = names.toArray(new String[0]);
        valueList = addresses.toArray(new String[0]);
    }
}

package org.codechimp.openliveview.receiver;

import org.codechimp.openliveview.content.ContentNotification;
import org.codechimp.openliveview.content.manager.SMSNotificationManager;
import org.codechimp.openliveview.data.LiveViewDbConstants;
import org.codechimp.openliveview.data.LiveViewDbHelper;
import org.codechimp.openliveview.data.Prefs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EventReceiver extends BroadcastReceiver {
    private final String LOG_TAG = "EventReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "Received broadcast: '" + intent.getAction() + "'.");
        Boolean broadcastHandled = false;

        if (intent.getAction().equals(LiveViewBrConstants.ALERT_ADD)) {
            String title = intent.getExtras().getString("title");
            String contents = intent.getExtras().getString("contents");
            int type = intent.getExtras().getInt("type");
            long timestamp = intent.getExtras().getLong("timestamp");
            String notificationContentFilter = (new Prefs(context)).getNotificationFilter();
            if (!notificationContentFilter.contains(contents)) {
                LiveViewDbHelper dbHelper;
                try {
                    dbHelper = new LiveViewDbHelper(context);
                    dbHelper.openToWrite();
                    dbHelper.insertAlert(title, contents, type, timestamp);
                    dbHelper.close();
                    Log.i(LOG_TAG, "Alert added to the database.");
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error: Could not add new alertitem. (" + e.getMessage() + ")");
                }
            } else {
                Log.i(LOG_TAG, "Alert not added to the database.");
            }
            if ((new Prefs(context)).getenablenotificationbuzzer2()) {
                Intent bci2 = new Intent(LiveViewBrConstants.PLUGIN_COMMAND);
                Bundle bcb2 = new Bundle();
                bcb2.putString("command", "notify");
                bcb2.putInt("delay", 0);
                bcb2.putInt("time", 600);
                bcb2.putLong("timestamp", timestamp);
                bcb2.putInt("displaynotification", 1);
                bcb2.putString("line1", title);
                bcb2.putString("line2", contents);
                bcb2.putInt("icon_type", 1);
                bci2.putExtras(bcb2);
                context.sendBroadcast(bci2);
            }
            broadcastHandled = true;
        }

        if (intent.getAction().equals(LiveViewBrConstants.OTHER_SMS_RECEIVED)) {
            ContentNotification notification = SMSNotificationManager.getNotificationContent(context, intent);
            LiveViewDbHelper dbHelper;
            try {
                dbHelper = new LiveViewDbHelper(context);
                dbHelper.openToWrite();
                dbHelper.insertAlert(notification.getTitle(), notification.getContent(), LiveViewDbConstants.ALERT_SMS, notification.getTimestamp());
                dbHelper.close();
                Log.i(LOG_TAG, "SMS added to the database.");
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error: Could not add SMS to db. (" + e.getMessage() + ")");
            }
            broadcastHandled = true;
        }

        if (broadcastHandled == false) {
            Log.e(LOG_TAG, "Error: Broadcast type unknown.");
        }
    }

}
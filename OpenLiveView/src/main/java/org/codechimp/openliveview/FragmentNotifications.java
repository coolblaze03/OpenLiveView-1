package org.codechimp.openliveview;

import org.codechimp.openliveview.data.LiveViewDbConstants;
import org.codechimp.openliveview.data.LiveViewDbHelper;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FragmentNotifications extends ListFragment implements
        LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    private NotificationAdapter mAdapter;
    private static final String[] PROJECTION = new String[]{
            LiveViewDbConstants.COLUMN_ALERT_ITEMS_ID,
            LiveViewDbConstants.COLUMN_ALERT_ITEMS_TITLE,
            LiveViewDbConstants.COLUMN_ALERT_ITEMS_CONTENT};

    public FragmentNotifications() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof DrawerFragmentInterface)) {
            throw new ClassCastException(activity.toString()
                    + " must implement DrawerFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbacks = this;

        // Fields on the UI to which we map
        int[] to = new int[] { R.id.id, R.id.title, R.id.content};

        mAdapter = new NotificationAdapter(this.getActivity(), R.layout.notification_item, null, PROJECTION,
                to, 0);

        setListAdapter(mAdapter);

        LoaderManager lm = getLoaderManager();
        lm.initLoader(LOADER_ID, null, mCallbacks);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_notifications, menu);

        menu.findItem(R.id.menu_notifications_clear).setVisible(
                !((DrawerFragmentInterface) getActivity()).isDrawerOpen());

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notifications_clear:
                deleteAllNotifications();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // public void showAllStoredNotifications() {
    // LiveViewDbHelper dbHelper;
    // AlertDialog.Builder builder = new AlertDialog.Builder(
    // this.getActivity());
    // builder.setTitle("Stored notifications");
    // dbHelper = new LiveViewDbHelper(this.getActivity());
    // dbHelper.openToRead();
    // String notifications = dbHelper.getAllAlertsAsString();
    // dbHelper.close();
    // if (notifications.equals("")) {
    // builder.setMessage("There are currently no notifications in the database.");
    // } else {
    // builder.setMessage(notifications);
    // }
    // builder.setPositiveButton(getString(R.string.close), null);
    // builder.setNeutralButton("Filter settings...", new OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // openFilterSettings();
    // }
    // });
    // if (notifications.equals("") == false) {
    // builder.setNegativeButton("Wipe", new OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // deleteAllNotifications();
    // }
    // });
    // }
    // builder.show();
    // }

    public void deleteAllNotifications() {
        try {
            LiveViewDbHelper dbHelper;
            dbHelper = new LiveViewDbHelper(this.getActivity());
            dbHelper.openToWrite();
            dbHelper.deleteAllAlerts();
            dbHelper.close();
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this.getActivity());
            builder.setTitle(getString(R.string.notifications));
            builder.setMessage(getString(R.string.notifications_deleted));
            builder.setPositiveButton(getString(R.string.close), null);
            builder.show();
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this.getActivity());
            builder.setTitle(getString(R.string.error));
            builder.setMessage(e.toString());
            builder.setPositiveButton(getString(R.string.close), null);
            builder.show();
        }
    }

    public void openFilterSettings() {
        Intent intent = new Intent(this.getActivity(), FilterEditor.class);
        startActivity(intent);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Create a new CursorLoader with the following query parameters.
        switch (id) {
            case LOADER_ID:
                Uri uri = AlertContentProvider.CONTENT_URI;
                return new CursorLoader(this.getActivity(), uri, PROJECTION, null,
                        null, null);
            default:
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // A switch-case is useful when dealing with multiple Loaders/IDs
        switch (loader.getId()) {
            case LOADER_ID:
                // The asynchronous load is complete and the data
                // is now available for use. Only now can we associate
                // the queried Cursor with the SimpleCursorAdapter.
                mAdapter.swapCursor(cursor);
                break;
        }
        // The listview now displays the queried data. }
    }

    public void onLoaderReset(Loader<Cursor> cursor) {
        // For whatever reason, the Loader's data is now unavailable.
        // Remove any references to the old data by replacing it with
        // a null Cursor.
        mAdapter.swapCursor(null);
    }
}

package org.codechimp.openliveview;

import org.codechimp.openliveview.data.LiveViewDbHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class AlertContentProvider extends ContentProvider {

    /**
     * A custom Content Provider to do the database operations
     */

    public static final String PROVIDER_NAME = "org.codechimp.openliveview.alert";

    /**
     * A uri to do operations on alerts table. A content provider is
     * identified by its uri
     */
    public static final Uri CONTENT_URI = Uri.parse("content://"
            + PROVIDER_NAME + "/alerts");

    /**
     * Constants to identify the requested operation
     */
    private static final int ALERTS = 1;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "alerts", ALERTS);
    }

    /**
     * This content provider does the database operations by this object
     */
    LiveViewDbHelper mLiveViewDB;

    /**
     * A callback method which is invoked when the content provider is starting
     * up
     */
    @Override
    public boolean onCreate() {
        mLiveViewDB = new LiveViewDbHelper(getContext());
        mLiveViewDB.openToRead();
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * A callback method which is by the default content uri
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == ALERTS) {
            return mLiveViewDB.getAllAlerts(projection);
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
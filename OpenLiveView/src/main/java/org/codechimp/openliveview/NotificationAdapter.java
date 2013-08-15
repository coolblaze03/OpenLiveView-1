package org.codechimp.openliveview;

import org.codechimp.openliveview.data.LiveViewDbConstants;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationAdapter extends SimpleCursorAdapter {

    private final Context mContext;
    private final Cursor mCursor;

    public NotificationAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

        mContext = context;
        mCursor = c;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.notification_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.itemtitle);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.typeicon);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = mCursor.getString(mCursor
                .getColumnIndex(LiveViewDbConstants.COLUMN_ALERT_ITEMS_TITLE));
        holder.text.setText(s);

        return rowView;
    }
}

package org.codechimp.openliveview;

import org.codechimp.openliveview.data.LiveViewDbConstants;
import org.codechimp.openliveview.receiver.LiveViewBrConstants;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentNotes extends Fragment {

    Activity activity;

    public FragmentNotes() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        if (!(activity instanceof DrawerFragmentInterface)) {
            throw new ClassCastException(activity.toString()
                    + " must implement DrawerFragmentInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_notes, container,
                false);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_notes, menu);

        menu.findItem(R.id.menu_notes_add).setVisible(
                !((DrawerFragmentInterface) getActivity()).isDrawerOpen());
        menu.findItem(R.id.menu_notes_delete).setVisible(
                !((DrawerFragmentInterface) getActivity()).isDrawerOpen());

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notes_add:
                addNote();
                return true;
            case R.id.menu_notes_delete:
                // TODO delete the selected note
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addNote() {
        final EditText input = new EditText(this.getActivity());
        input.setText("");
        new AlertDialog.Builder(this.getActivity())
                .setTitle(getString(R.string.notes_add))
                .setMessage(getString(R.string.add_note_help))
                .setView(input)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                String value = input.getText().toString();
                                Intent add_alert_intent = new Intent(
                                        LiveViewBrConstants.ALERT_ADD);
                                Bundle add_alert_bundle = new Bundle();
                                add_alert_bundle.putString("contents", value);
                                add_alert_bundle.putString("title", "Note");
                                add_alert_bundle.putInt("type",
                                        LiveViewDbConstants.ALERT_NOTE);
                                add_alert_bundle.putLong("timestamp",
                                        System.currentTimeMillis());
                                add_alert_intent.putExtras(add_alert_bundle);
                                activity.sendBroadcast(add_alert_intent);
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        activity);
                                builder.setTitle("Info");
                                builder.setMessage("Your note is added to the database.");
                                builder.setPositiveButton(
                                        getString(R.string.close), null);
                                builder.show();
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // Do nothing.
                            }
                        }).show();
    }

}

package be.sanderdecleer.dndapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.view.View;

import be.sanderdecleer.dndapp.R;

/**
 * Created by SD on 23/05/2016.
 */
public final class LayoutUtils {

    public interface EditViewCallback {
        void EditView(View view);
    }

    // Hide the constructor
    private LayoutUtils() {

    }

    public static void showInfoDialog(
            Activity activity, int layoutId, String title, EditViewCallback editViewCallback) {

        // Create the requested layout
        XmlResourceParser weaponLayout = activity.getResources().getLayout(layoutId);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(weaponLayout, null);

        // Pass layout to caller for editing
        if (editViewCallback != null)
            editViewCallback.EditView(view);

        // Build dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setTitle(title)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        // Finalize and show dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

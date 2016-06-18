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

    public interface DismissDialogCallback {
        void OnDialogDismissed(View view);
    }

    public interface OnDeleteCallback {
        void OnDelete(View view);
    }

    public interface EditViewCallback {
        void EditView(View view);
    }

    private interface EditAlertDialogBuilderCallback {
        void EditBuilder(AlertDialog.Builder builder, View view);
    }

    // Hide the constructor
    private LayoutUtils() {

    }

    public static void showInfoDialog(Activity activity, int layoutId, String title,
                                      EditViewCallback editViewCallback) {

        showDialog(activity, layoutId, title, editViewCallback, new EditAlertDialogBuilderCallback() {
            @Override
            public void EditBuilder(AlertDialog.Builder builder, View view) {
                builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public static void showEditDialog(Activity activity, int layoutId, String title,
                                      final EditViewCallback editViewCallback,
                                      final DismissDialogCallback onConfirm) {
        showEditDialog(activity, layoutId, title, editViewCallback, onConfirm, null);
    }

    public static void showEditDialog(Activity activity, int layoutId, String title,
                                      final EditViewCallback editViewCallback,
                                      final DismissDialogCallback onConfirm,
                                      final OnDeleteCallback onDelete) {

        showDialog(activity, layoutId, title,
                new EditViewCallback() {
                    @Override
                    public void EditView(View view) {
                        if (editViewCallback != null)
                            editViewCallback.EditView(view);
                    }
                },
                new EditAlertDialogBuilderCallback() {
                    @Override
                    public void EditBuilder(final AlertDialog.Builder builder, final View view) {
                        builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (onConfirm != null)
                                    onConfirm.OnDialogDismissed(view);
                            }
                        });

                        // Add delete handler if needed
                        if (onDelete != null) {
                            builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (onDelete != null) {
                                        onDelete.OnDelete(view);
                                    }
                                }
                            });
                        }
                    }
                });

    }

    private static void showDialog(Activity activity, int layoutId, String title,
                                   EditViewCallback editViewCallback,
                                   EditAlertDialogBuilderCallback editBuilderCallback) {

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
                .setTitle(title);

        // pass builder for editing
        if (editBuilderCallback != null)
            editBuilderCallback.EditBuilder(builder, view);

        // Finalize and show dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}

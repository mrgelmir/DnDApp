package be.sanderdecleer.dndapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;

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

    public interface TextResultCallback {
        void GetTextResult(String string);
    }

    public interface ArrayResultCallback {
        void GetResult(int arrayIndex);
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

    public static void showEditTextDialog(final Activity activity, String title,
                                          final String startText,
                                          final TextResultCallback textResultCallback) {
        LayoutUtils.showEditDialog(activity, R.layout.edit_text, title,
                new LayoutUtils.EditViewCallback() {
                    @Override
                    public void EditView(View view) {
                        final EditText label = (EditText) view.findViewById(R.id.edit_field);
                        label.setText(startText);


                        final Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Request keyboard
                                        if (label.requestFocus()) {
                                            label.selectAll();
                                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.showSoftInput(label, InputMethodManager.SHOW_IMPLICIT);
                                        }
                                    }
                                });
                            }
                        }, 100);
                    }
                }, new LayoutUtils.DismissDialogCallback() {
                    @Override
                    public void OnDialogDismissed(View view) {
                        if (textResultCallback != null) {
                            EditText label = (EditText) view.findViewById(R.id.edit_field);
                            textResultCallback.GetTextResult(label.getText().toString());
                        }
                    }
                });
    }

    public static void showSpinnerDialog(final Activity activity, String title,
                                         final String[] values, final int startValue,
                                         final ArrayResultCallback arrayResultCallback) {

        LayoutUtils.showEditDialog(activity, R.layout.edit_single_number, title, new EditViewCallback() {
            @Override
            public void EditView(View view) {
                final NumberPicker picker = (NumberPicker) view.findViewById(R.id.edit_value);

                picker.setMaxValue(values.length - 1);
                picker.setDisplayedValues(values);

                picker.setValue(startValue);
            }
        }, new DismissDialogCallback() {
            @Override
            public void OnDialogDismissed(View view) {
                final NumberPicker picker = (NumberPicker) view.findViewById(R.id.edit_value);

                arrayResultCallback.GetResult(picker.getValue());
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

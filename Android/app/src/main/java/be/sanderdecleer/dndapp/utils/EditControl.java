package be.sanderdecleer.dndapp.utils;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Manages edit mode
 */
public class EditControl {

    private static boolean editMode = false;
    private static HashSet<EditModeChangedListener> listeners = new HashSet<>();

    public static boolean isEditMode() {
        return editMode;
    }

    public static void setEditMode(boolean editMode) {

        // Only do stuff if value is actually changed
        if (EditControl.editMode != editMode) {
            EditControl.editMode = editMode;
            for (EditModeChangedListener listener : listeners) {
                listener.OnEditModeChanged(editMode);
            }
        }
    }

    public static void toggleEditMode() {
        setEditMode(!editMode);
    }

    public static void addListener(EditModeChangedListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(EditModeChangedListener listener) {
        listeners.remove(listener);
    }

    private EditControl() {
        // Private constructor
    }

    public interface EditModeChangedListener {
        void OnEditModeChanged(boolean isEditMode);
    }
}

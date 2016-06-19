package be.sanderdecleer.dndapp.utils;

import android.view.View;

/**
 * OnClickListener class that differentiates its behaviour depending on edit mode
 * Might be obsolete if only one OnClickListener gets attached in Adapters
 */
public class OnClickListenerEditable implements View.OnClickListener {

    private View.OnClickListener defaultClickListener;
    private View.OnClickListener editClickListener;

    public OnClickListenerEditable(View.OnClickListener defaultClickListener,
                                   View.OnClickListener editClickListener) {
        this.defaultClickListener = defaultClickListener;
        this.editClickListener = editClickListener;
    }

    @Override
    public void onClick(View v) {

        // Swap between two setOnClickListener instances depending on state
        if (EditControl.isEditMode()) {
            if (editClickListener != null)
                editClickListener.onClick(v);
        } else if (defaultClickListener != null) {
            defaultClickListener.onClick(v);
        }


    }
}

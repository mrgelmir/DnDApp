package be.sanderdecleer.dndapp.utils;

import android.view.View;

import be.sanderdecleer.dndapp.activities.CharacterSheet;

/**
 * Created by SD on 2/06/2016.
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

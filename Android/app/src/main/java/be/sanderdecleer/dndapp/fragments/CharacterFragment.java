package be.sanderdecleer.dndapp.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.utils.CharacterProvider;

/**
 * A {@link Fragment} subclass.
 * Activities that contain this fragment or its children must implement the
 * {@link CharacterProvider} interface to provide character data.
 */

public abstract class CharacterFragment extends Fragment
        implements CharacterControl.Listener{

    // Lifetime cycle functions/callbacks

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get Character provider
        CharacterControl.getInstance().addListener(this);

        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CharacterControl.getInstance().removeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setup();
        onCharacterChanged();
    }



    // Abstract functions, need to be implemented
    protected abstract int getLayoutID();

    protected abstract void setup();

    public abstract void onCharacterChanged();


    // Helper functions

    /**
     * Finds a view that was identified by the id attribute
     * @return The view, if found
     */
    protected View findViewById(int id) {
        return getActivity().findViewById(id);
    }

}

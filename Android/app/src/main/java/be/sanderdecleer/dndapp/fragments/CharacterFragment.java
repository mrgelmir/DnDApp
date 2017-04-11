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

abstract class CharacterFragment extends Fragment
        implements CharacterControl.Listener{

    // The object (probably activity) which holds the character data
    // TODO make sure this survives screen rotation =O
    // (https://developer.android.com/guide/topics/resources/runtime-changes.html)
    protected CharacterProvider characterProvider;

    // Lifetime cycle functions/callbacks

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get Character provider
        characterProvider = CharacterControl.getInstance();
        characterProvider.addListener(this);

        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        characterProvider.removeListener(this);
        characterProvider = null;
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
     *
     * @return The view, if found
     */
    protected View findViewById(int id) {
        return getActivity().findViewById(id);
    }

}

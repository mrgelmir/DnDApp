package be.sanderdecleer.dndapp.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.utils.CharacterProvider;
import be.sanderdecleer.dndapp.utils.EditControl;

/**
 * A {@link Fragment} subclass.
 * Activities that contain this fragment or its children must implement the
 * {@link CharacterProvider} interface to provide character data.
 */

abstract class CharacterFragment extends Fragment
        implements CharacterControl.Listener,
        EditControl.EditModeChangedListener {

    // The object (probably activity) which holds the character data
    protected CharacterProvider characterProvider;

    // Lifetime cycle functions/callbacks

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Find out how to handle this

        if (context instanceof CharacterProvider) {
            // Get Character Provider
            characterProvider = (CharacterProvider) context;
        } else {
            // fall back on active character provider
            characterProvider = CharacterControl.getInstance();
        }

        characterProvider.addListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clean up
        characterProvider = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCharacterChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setup();
    }

    // EditModeChangedListener implementation
    @Override
    public void OnEditModeChanged(boolean isEditMode) {
        // For now update character to show edit specific things, maybe later change this
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

package be.sanderdecleer.dndapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.sanderdecleer.dndapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoCharacter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoCharacter extends Fragment {

    public NoCharacter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NoCharacter.
     */
    // TODO: Rename and change types and number of parameters
    public static NoCharacter newInstance() {
        NoCharacter fragment = new NoCharacter();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_character, container, false);
    }

}

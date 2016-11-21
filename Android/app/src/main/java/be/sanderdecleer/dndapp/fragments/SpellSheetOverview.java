package be.sanderdecleer.dndapp.fragments;

import android.support.v4.app.Fragment;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.utils.CharacterProvider;


/**
 * A {@link CharacterFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterProvider} interface to provide character data.
 * Use the {@link SpellSheetOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpellSheetOverview extends CharacterFragment {

    public SpellSheetOverview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment
     * @return A new instance of fragment SpellSheetOverview.
     */
    public static SpellSheetOverview newInstance() {
        return new SpellSheetOverview();
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_spell_sheet_overview;
    }

    @Override
    protected void setup() {
        // TODO
    }

    @Override
    public void onCharacterChanged() {
        // TODO
    }
}

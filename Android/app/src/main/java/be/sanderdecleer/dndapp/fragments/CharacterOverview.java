package be.sanderdecleer.dndapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.activities.CharacterSheet;
import be.sanderdecleer.dndapp.model.CharacterModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterOverview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharacterOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterOverview extends Fragment implements CharacterSheet.OnCharacterChangedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CHARACTER = "argument_character";

    // TODO: Rename and change types of parameters
    private CharacterModel characterModel = null;

    private OnFragmentInteractionListener mListener;

    public CharacterOverview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param character Character
     * @return A new instance of fragment CharacterOverview.
     */
    public static CharacterOverview newInstance(CharacterModel character) {
        CharacterOverview fragment = new CharacterOverview();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHARACTER, character);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get character parameter
            characterModel = getArguments().getParcelable(ARG_CHARACTER);

            // Set data
            onCharacterChanged(characterModel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_overview, container, false);

        // Set data in the view

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCharacterChanged(CharacterModel character) {

        // Save character reference
        characterModel = character;

        // Update data if character is valid
        if(characterModel == null){
            // TODO: 16/06/2016 Clear fields
            return;
        }

        // Update Ability scores
        setAbilityScore(R.id.ability_score_STR, R.string.ability_score_STR, characterModel.STR);
        setAbilityScore(R.id.ability_score_DEX, R.string.ability_score_DEX, characterModel.DEX);
        setAbilityScore(R.id.ability_score_CON, R.string.ability_score_CON, characterModel.CON);
        setAbilityScore(R.id.ability_score_INT, R.string.ability_score_INT, characterModel.INT);
        setAbilityScore(R.id.ability_score_WIS, R.string.ability_score_WIS, characterModel.WIS);
        setAbilityScore(R.id.ability_score_CHA, R.string.ability_score_CHA, characterModel.CHA);


        // Update other stats
        String formattedAC = String.format(getResources().getString(R.string.character_AC),
                characterModel.AC);
        ((TextView) getView().findViewById(R.id.character_AC)).setText(formattedAC);

        String formattedHP = String.format(getResources().getString(R.string.character_HP),
                characterModel.HP_current, characterModel.HP_max);
        ((TextView) getView().findViewById(R.id.character_HP)).setText(formattedHP);

        String formattedSpeed = String.format(getResources().getString(R.string.character_Speed),
                characterModel.speed);
        ((TextView) getView().findViewById(R.id.character_Speed)).setText(formattedSpeed);
    }

    private void setAbilityScore(int attributeViewID, int attributeStringId, int attributeValue) {

        View attributeView = getView().findViewById(attributeViewID);

        TextView attrNameView = (TextView) attributeView.findViewById(R.id.attribute_name);
        TextView attrScoreView = (TextView) attributeView.findViewById(R.id.attribute_score);

        attrNameView.setText(getResources().getText(attributeStringId));
        int attributeModifier = Math.round((attributeValue - 10) / 2);
        attrScoreView.setText(String.format(getResources().getString(R.string.ability_score_value),
                attributeValue, attributeModifier < 0 ? "" : "+", attributeModifier));
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name -> is this needed?

        void onCharacterChanged();
    }
}

package be.sanderdecleer.dndapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.activities.CharacterSheet;
import be.sanderdecleer.dndapp.adapters.BaseCharacterAdapter;
import be.sanderdecleer.dndapp.adapters.ExpendableAdapter;
import be.sanderdecleer.dndapp.adapters.FeatureAdapter;
import be.sanderdecleer.dndapp.adapters.WeaponAdapter;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.utils.OnClickListenerEditable;


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

    private ArrayList<BaseCharacterAdapter> characterAdapters;

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

        // Get bundle info
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Do setup here

        // ADAPTERS
        characterAdapters = new ArrayList<>(3);

        // Create feature adapter and link to view
        FeatureAdapter featureAdapter = new FeatureAdapter(getActivity(), R.layout.p_feature_view_item);
        characterAdapters.add(featureAdapter);
        AdapterView abilitiesView = (AdapterView) getActivity().findViewById(R.id.feature_list);
        if (abilitiesView != null) {
            abilitiesView.setAdapter(featureAdapter);
        }

        // Create weapon adapter and link to view
        WeaponAdapter weaponAdapter = new WeaponAdapter(getActivity(), R.layout.p_weapon_view_item);
        characterAdapters.add(weaponAdapter);
        AdapterView weaponView = (AdapterView) getActivity().findViewById(R.id.weapon_list);
        if (weaponView != null) {
            weaponView.setAdapter(weaponAdapter);
        }

        // Create expandable view and link to view
        ExpendableAdapter expendableAdapter = new ExpendableAdapter(getActivity(), R.layout.p_expendable_view);
        characterAdapters.add(expendableAdapter);
        AdapterView expendableView = (AdapterView) getActivity().findViewById(R.id.expendables_list);
        if (expendableView != null) {
            expendableView.setAdapter(expendableAdapter);
        }

        // Attach click listeners for both regular and edit use
        attachClickListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Find out how to handle this

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
        if (characterModel == null) {
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
        ((TextView) getActivity().findViewById(R.id.character_AC)).setText(formattedAC);

        String formattedHP = String.format(getResources().getString(R.string.character_HP),
                characterModel.HP_current, characterModel.HP_max);
        ((TextView) getActivity().findViewById(R.id.character_HP)).setText(formattedHP);

        String formattedSpeed = String.format(getResources().getString(R.string.character_Speed),
                characterModel.speed);
        ((TextView) getActivity().findViewById(R.id.character_Speed)).setText(formattedSpeed);

        // Update the adapters
        for (BaseCharacterAdapter adapter : characterAdapters) {
            adapter.setCharacter(characterModel);
        }
    }

    private void setAbilityScore(int attributeViewID, int attributeStringId, int attributeValue) {

        View attributeView = getActivity().findViewById(attributeViewID);

        TextView attrNameView = (TextView) attributeView.findViewById(R.id.attribute_name);
        TextView attrScoreView = (TextView) attributeView.findViewById(R.id.attribute_score);

        attrNameView.setText(getResources().getText(attributeStringId));
        int attributeModifier = Math.round((attributeValue - 10) / 2);
        attrScoreView.setText(String.format(getResources().getString(R.string.ability_score_value),
                attributeValue, attributeModifier < 0 ? "" : "+", attributeModifier));
    }

    private void attachClickListeners() {

        // TODO: add these to all elements
        final View strView = getActivity().findViewById(R.id.ability_score_STR);
        if (strView != null) {
            strView.setOnClickListener(new OnClickListenerEditable(
                    null,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "edit tap", Toast.LENGTH_SHORT).show();
                            Log.i("test", "edit tap");

                            LayoutUtils.showEditDialog(getActivity(), R.layout.edit_ability_score,
                                    getActivity().getString(R.string.ability_score_STR),
                                    new LayoutUtils.EditViewCallback() {
                                        @Override
                                        public void EditView(View view) {
                                            NumberPicker picker = (NumberPicker) view.findViewById(R.id.ability_edit_description);
                                            picker.setMinValue(0);
                                            picker.setMaxValue(24);
                                            picker.setWrapSelectorWheel(false);
                                        }
                                    }, new LayoutUtils.DismissDialogCallback() {
                                        @Override
                                        public void OnDialogDismissed(View view) {
                                            // TODO: 16/06/2016
                                        }
                                    });

                        }
                    }));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onCharacterChanged();
    }
}

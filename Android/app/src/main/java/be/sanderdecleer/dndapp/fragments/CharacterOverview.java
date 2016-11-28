package be.sanderdecleer.dndapp.fragments;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import be.sanderdecleer.dndapp.adapters.BaseItemAdapter;
import be.sanderdecleer.dndapp.model.BaseItem;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.model.ExpendableModel;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.utils.CharacterProvider;
import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.adapters.BaseCharacterAdapter;
import be.sanderdecleer.dndapp.utils.EditControl;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.utils.OnClickListenerEditable;


/**
 * A {@link CharacterFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterProvider} interface to provide character data.
 * Use the {@link CharacterOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterOverview extends CharacterFragment {

    // Keep track of all adapters that use character data
    private ArrayList<BaseCharacterAdapter> characterAdapters;

    private BaseItemAdapter baseItemAdapter;

    public CharacterOverview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment CharacterOverview.
     */
    public static CharacterOverview newInstance() {
        CharacterOverview fragment = new CharacterOverview();
        // Do initialization or bundle argument putting here if needed
        return fragment;
    }

    // Override methods
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_character_overview;
    }

    @Override
    protected void setup() {

        ArrayList<BaseItem> items = new ArrayList<>();
        if (characterProvider.getCharacter() != null) {
//            items.addAll(characterProvider.getCharacter().weapons);
            items.addAll(characterProvider.getCharacter().expendables);
            items.addAll(characterProvider.getCharacter().abilities);
        }
        baseItemAdapter = new BaseItemAdapter(getActivity(), R.layout.item_base, items);

        AdapterView adapterView = (AdapterView) findViewById(R.id.item_list);
        if (adapterView != null) {
            adapterView.setAdapter(baseItemAdapter);
        }

        adapterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("BROL", view.toString());
            }
        });

        /*

        // todo Attach click listeners for both regular and edit use
        attachClickListeners();

        */
    }

    @Override
    public void onCharacterChanged() {

        // Update data if character is valid
        if (characterProvider.getCharacter() == null) {
            // TODO: 16/06/2016 Clear fields? -> us a null character?
            return;
        }

        // Update Ability scores
        setAbilityScore(R.id.ability_score_STR, R.string.ability_score_STR,
                characterProvider.getCharacter().STR_base);
        setAbilityScore(R.id.ability_score_DEX, R.string.ability_score_DEX,
                characterProvider.getCharacter().DEX_base);
        setAbilityScore(R.id.ability_score_CON, R.string.ability_score_CON,
                characterProvider.getCharacter().CON_base);
        setAbilityScore(R.id.ability_score_INT, R.string.ability_score_INT,
                characterProvider.getCharacter().INT_base);
        setAbilityScore(R.id.ability_score_WIS, R.string.ability_score_WIS,
                characterProvider.getCharacter().WIS_base);
        setAbilityScore(R.id.ability_score_CHA, R.string.ability_score_CHA,
                characterProvider.getCharacter().CHA_base);


        // Update other stats
        String formattedAC = String.format(getResources().getString(R.string.character_AC),
                characterProvider.getCharacter().AC);
        ((TextView) findViewById(R.id.character_AC)).setText(formattedAC);

        String formattedHP = String.format(getResources().getString(R.string.character_HP),
                characterProvider.getCharacter().HP_current, characterProvider.getCharacter().HP_max);
        ((TextView) findViewById(R.id.character_HP)).setText(formattedHP);

        String formattedSpeed = String.format(getResources().getString(R.string.character_Speed),
                characterProvider.getCharacter().speed);
        ((TextView) findViewById(R.id.character_Speed)).setText(formattedSpeed);


//        // Update the adapters
//        for (BaseCharacterAdapter adapter : characterAdapters) {
//            adapter.setCharacter(characterProvider.getCharacter());
//    }
//

        ArrayList<BaseItem> items = new ArrayList<>();

        if (characterProvider.getCharacter() != null) {
//            items.addAll(characterProvider.getCharacter().weapons);
            items.addAll(characterProvider.getCharacter().expendables);
            items.addAll(characterProvider.getCharacter().abilities);
        }
        baseItemAdapter.clear();
        baseItemAdapter.addAll(items);
        baseItemAdapter.notifyDataSetChanged();

        findViewById(R.id.scroll_view).invalidate();
    }

    // Custom methods
    private void setAbilityScore(int attributeViewID, int attributeStringId, int attributeValue) {

        View attributeView = findViewById(attributeViewID);

        TextView attrNameView = (TextView) attributeView.findViewById(R.id.attribute_name);
        TextView attrScoreView = (TextView) attributeView.findViewById(R.id.attribute_value);

        attrNameView.setText(getResources().getText(attributeStringId));
        // Round down ability score (casting doesn't work in the negatives)
        int attributeModifier = (int) Math.floor((attributeValue - 10) / 2.0);
        attrScoreView.setText(String.format(getResources().getString(R.string.ability_score_value),
                attributeValue, attributeModifier < 0 ? "" : "+", attributeModifier));

    }

    private void attachClickListeners() {

        // Set up ability updating
        setupAbilityView(R.id.ability_score_STR, new AbilityAccessor() {
            @Override
            public int get() {
                return characterProvider.getCharacter().STR_base;
            }

            @Override
            public void set(int value) {
                characterProvider.getCharacter().STR_base = value;
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_STR);
            }
        });
        setupAbilityView(R.id.ability_score_DEX, new AbilityAccessor() {
            @Override
            public int get() {
                return characterProvider.getCharacter().DEX_base;
            }

            @Override
            public void set(int value) {
                characterProvider.getCharacter().DEX_base = value;
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_DEX);
            }
        });
        setupAbilityView(R.id.ability_score_CON, new AbilityAccessor() {
            @Override
            public int get() {
                return characterProvider.getCharacter().CON_base;
            }

            @Override
            public void set(int value) {
                characterProvider.getCharacter().CON_base = value;
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_CON);
            }
        });
        setupAbilityView(R.id.ability_score_INT, new AbilityAccessor() {
            @Override
            public int get() {
                return characterProvider.getCharacter().INT_base;
            }

            @Override
            public void set(int value) {
                characterProvider.getCharacter().INT_base = value;
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_INT);
            }
        });
        setupAbilityView(R.id.ability_score_WIS, new AbilityAccessor() {
            @Override
            public int get() {
                return characterProvider.getCharacter().WIS_base;
            }

            @Override
            public void set(int value) {
                characterProvider.getCharacter().WIS_base = value;
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_WIS);
            }
        });
        setupAbilityView(R.id.ability_score_CHA, new AbilityAccessor() {
            @Override
            public int get() {
                return characterProvider.getCharacter().CHA_base;
            }

            @Override
            public void set(int value) {
                characterProvider.getCharacter().CHA_base = value;
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_CHA);
            }
        });
    }

    private void setupAbilityView(int viewId, final AbilityAccessor abilityAccessor) {

        final View strView = findViewById(viewId);
        if (strView != null) {
            strView.setOnClickListener(new OnClickListenerEditable(
                    null, // No default click listeners
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // Only show dialog when a character is present
                            if (characterProvider.getCharacter() == null)
                                return;

                            LayoutUtils.showEditDialog(getActivity(), R.layout.edit_ability_score,
                                    abilityAccessor.getName(),
                                    new LayoutUtils.EditViewCallback() {
                                        @Override
                                        public void EditView(View view) {
                                            // Set values of the picker
                                            NumberPicker picker = (NumberPicker) view.findViewById(R.id.ability_edit_description);
                                            picker.setMinValue(0);
                                            picker.setMaxValue(24);
                                            picker.setWrapSelectorWheel(false);
                                            picker.setValue(abilityAccessor.get());
                                        }
                                    }, new LayoutUtils.DismissDialogCallback() {
                                        @Override
                                        public void OnDialogDismissed(View view) {
                                            // Confirm values
                                            NumberPicker picker = (NumberPicker) view.findViewById(R.id.ability_edit_description);
                                            abilityAccessor.set(picker.getValue());
                                            // TODO fix line below: character should listen for this itself?
                                            CharacterControl.setCurrentCharacter(CharacterControl.getCurrentCharacter());
                                        }
                                    });

                        }
                    }));
        }
    }

    interface AbilityAccessor {

        void set(int value);

        int get();

        String getName();
    }

}

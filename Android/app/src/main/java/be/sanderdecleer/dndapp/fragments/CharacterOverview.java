package be.sanderdecleer.dndapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import be.sanderdecleer.dndapp.adapters.BaseItemAdapter;
import be.sanderdecleer.dndapp.model.character.AbilityModel;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.model.character.FeatureModel;
import be.sanderdecleer.dndapp.model.character.WeaponModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.utils.CharacterProvider;
import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.views.AbilityView;
import be.sanderdecleer.dndapp.views.BaseItemView;


/**
 * A {@link CharacterFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharacterProvider} interface to provide character data.
 * Use the {@link CharacterOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterOverview extends CharacterFragment {

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

        // Create content
        ArrayList<BaseItem> items = new ArrayList<>();
        if (CharacterControl.hasCurrentCharacter()) {
            items.addAll(CharacterControl.getCurrentCharacter().weapons);
            items.addAll(CharacterControl.getCurrentCharacter().expendables);
            items.addAll(CharacterControl.getCurrentCharacter().abilities);
        }
        baseItemAdapter = new BaseItemAdapter(getActivity(), R.layout.item_base, items);

        AdapterView adapterView = (AdapterView) findViewById(R.id.item_list);
        if (adapterView != null) {
            adapterView.setAdapter(baseItemAdapter);

            // Handle item interaction
            adapterView.setOnItemClickListener(itemClickListener);
            adapterView.setOnItemLongClickListener(itemClickListener);
        }

        attachClickListeners();
    }

    @Override
    public void onCharacterChanged() {

        // Update data if character is valid
        if (!CharacterControl.hasCurrentCharacter()) {
            // TODO: 16/06/2016 Clear fields? -> use a null character?
            return;
        }

        // TEST -> this is the way to go, but different
//        LinearLayout strView = (LinearLayout) findViewById(R.id.ability_score_STR);
//        AbilityModel strModel = AbilityModel.getEmpty();
//        strModel.setName("STR");
//        strModel.setScore(CharacterControl.getCurrentCharacter().getSTRValue());
//        AbilityView strViewController = new AbilityView(getContext());
//        strViewController.setItem(strModel);
//        strViewController.setupItemView(strView);

        AbilityView strView = (AbilityView) findViewById(R.id.ability_score_STR);
        strView.setItem(CharacterControl.getCurrentCharacter().getSTR());
        strView.setupItemView(strView);

        // Update Ability scores
//        setAbilityScore(R.id.ability_score_STR, R.string.ability_score_STR,
//                CharacterControl.getCurrentCharacter().getSTRValue());
        setAbilityScore(R.id.ability_score_DEX, R.string.ability_score_DEX,
                CharacterControl.getCurrentCharacter().getDEXValue());
        setAbilityScore(R.id.ability_score_CON, R.string.ability_score_CON,
                CharacterControl.getCurrentCharacter().getCONValue());
        setAbilityScore(R.id.ability_score_INT, R.string.ability_score_INT,
                CharacterControl.getCurrentCharacter().getINTValue());
        setAbilityScore(R.id.ability_score_WIS, R.string.ability_score_WIS,
                CharacterControl.getCurrentCharacter().getWISValue());
        setAbilityScore(R.id.ability_score_CHA, R.string.ability_score_CHA,
                CharacterControl.getCurrentCharacter().getCHAValue());


        // Update other stats
        String formattedAC = String.format(getResources().getString(R.string.character_AC),
                CharacterControl.getCurrentCharacter().getAC());
        ((TextView) findViewById(R.id.character_AC)).setText(formattedAC);

        String formattedHP = String.format(getResources().getString(R.string.character_HP),
                CharacterControl.getCurrentCharacter().getHP_current(),
                CharacterControl.getCurrentCharacter().getHP_max());
        ((TextView) findViewById(R.id.character_HP)).setText(formattedHP);

        String formattedSpeed = String.format(getResources().getString(R.string.character_Speed),
                CharacterControl.getCurrentCharacter().getSpeed());
        ((TextView) findViewById(R.id.character_Speed)).setText(formattedSpeed);

        // Update items
        ArrayList<BaseItem> items = new ArrayList<>();
        if (CharacterControl.hasCurrentCharacter()) {
            items.addAll(CharacterControl.getCurrentCharacter().weapons);
            items.addAll(CharacterControl.getCurrentCharacter().expendables);
            items.addAll(CharacterControl.getCurrentCharacter().abilities);
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

        // TODO: 12/04/2017 Make this more pretty
        // Set up ability updating
        setupAbilityView(R.id.ability_score_STR, new AbilityAccessor() {
            @Override
            public int get() {
                return CharacterControl.getCurrentCharacter().getSTRValue();
            }

            @Override
            public void set(int value) {
                CharacterControl.getCurrentCharacter().setSTR(value);
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_STR);
            }
        });
        setupAbilityView(R.id.ability_score_DEX, new AbilityAccessor() {
            @Override
            public int get() {
                return CharacterControl.getCurrentCharacter().getDEXValue();
            }

            @Override
            public void set(int value) {
                CharacterControl.getCurrentCharacter().setDEX(value);
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_DEX);
            }
        });
        setupAbilityView(R.id.ability_score_CON, new AbilityAccessor() {
            @Override
            public int get() {
                return CharacterControl.getCurrentCharacter().getCONValue();
            }

            @Override
            public void set(int value) {
                CharacterControl.getCurrentCharacter().setCON(value);
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_CON);
            }
        });
        setupAbilityView(R.id.ability_score_INT, new AbilityAccessor() {
            @Override
            public int get() {
                return CharacterControl.getCurrentCharacter().getINTValue();
            }

            @Override
            public void set(int value) {
                CharacterControl.getCurrentCharacter().setINT(value);
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_INT);
            }
        });
        setupAbilityView(R.id.ability_score_WIS, new AbilityAccessor() {
            @Override
            public int get() {
                return CharacterControl.getCurrentCharacter().getWISValue();
            }

            @Override
            public void set(int value) {
                CharacterControl.getCurrentCharacter().setWIS(value);
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_WIS);
            }
        });
        setupAbilityView(R.id.ability_score_CHA, new AbilityAccessor() {
            @Override
            public int get() {
                return CharacterControl.getCurrentCharacter().getCHAValue();
            }

            @Override
            public void set(int value) {
                CharacterControl.getCurrentCharacter().setCHA(value);
            }

            @Override
            public String getName() {
                return getString(R.string.ability_score_CHA);
            }
        });

        final View.OnClickListener creationMenuToggleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
            }
        };

        // Floating Action Button setup
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        if (fab != null) {
            fab.setOnClickListener(creationMenuToggleListener);
        }

        // Creation menu setup
        final View creationMenu = findViewById(R.id.overview_create_menu);
        final Button createWeaponButton = (Button) findViewById(R.id.create_weapon);
        final Button createFeatureButton = (Button) findViewById(R.id.create_feature);
        final Button createExpendableButton = (Button) findViewById(R.id.create_expendable);
        final Button dismissButton = (Button) findViewById(R.id.create_dismiss);

        creationMenu.setVisibility(View.INVISIBLE);

        createWeaponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
                if (CharacterControl.hasCurrentCharacter()) {
                    CharacterControl.getCurrentCharacter().addWeapon(WeaponModel.getEmpty());
                    CharacterControl.tryCharacterChanged();
                }
            }
        });
        createFeatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
                if (CharacterControl.hasCurrentCharacter()) {
                    CharacterControl.getCurrentCharacter().addFeature(FeatureModel.getEmpty());
                    CharacterControl.tryCharacterChanged();
                }
            }
        });
        createExpendableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
                if (CharacterControl.hasCurrentCharacter()) {
                    CharacterControl.getCurrentCharacter().addExpendable(ExpendableModel.getEmpty());
                    CharacterControl.tryCharacterChanged();
                }
            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
            }
        });
    }

    private void toggleCreationMenu() {

//        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
//
//
//        // TODO move this to a custom FragmentDialog to utilise the back-stack
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        builder.setTitle("Create");
//
//        builder.setItems(new CharSequence[]{"Weapon", "Feature", "Resource"},
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Temp hardcoded switch
//                        if (CharacterControl.hasCurrentCharacter()) {
//
//                            switch (which) {
//                                case 0:
//                                    CharacterControl.getCurrentCharacter().addWeapon(WeaponModel.getEmpty());
//                                    break;
//                                case 1:
//                                    CharacterControl.getCurrentCharacter().addFeature(FeatureModel.getEmpty());
//                                    break;
//                                case 2:
//                                    CharacterControl.getCurrentCharacter().addExpendable(ExpendableModel.getEmpty());
//                                    break;
//                            }
//                            CharacterControl.tryCharacterChanged();
//                        }
//                        dialog.dismiss();
//                    }
//                });
//
//        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                fab.show();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//
//        dialog.show();
//        fab.hide();


        // This is fancy animation code: keep this for later
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        // Creation menu revel setup
        final View creationMenu = findViewById(R.id.overview_create_menu);

        if (creationMenu.getVisibility() != View.VISIBLE) {

            final int moveDuration = 200;

            int[] activatorLocation = new int[2];
            fab.getLocationOnScreen(activatorLocation);

            int[] menuLocation = new int[2];
            creationMenu.getLocationOnScreen(menuLocation);

            TranslateAnimation moveAnim = new TranslateAnimation(
                    0, menuLocation[0] - activatorLocation[0] + creationMenu.getWidth() / 2,
                    0, menuLocation[1] - activatorLocation[1] + creationMenu.getHeight() / 2);

            moveAnim.setDuration(moveDuration);

            fab.startAnimation(moveAnim);

            // get the center for the clipping circle
            int cx = creationMenu.getWidth() / 2;
            int cy = creationMenu.getHeight() / 2;

            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(cx, cy);

            final Animator revealAnim = ViewAnimationUtils.createCircularReveal(creationMenu, cx, cy, 0, finalRadius);


            revealAnim.setStartDelay(moveDuration);
            revealAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    creationMenu.setVisibility(View.VISIBLE);
//                    fab.hide();
                    fab.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            revealAnim.start();

            final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                    getResources().getColor(R.color.color_accent),
                    getResources().getColor(R.color.color_background));

            colorAnimation.setDuration(revealAnim.getDuration());
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    creationMenu.setBackgroundTintList(ColorStateList.valueOf(
                            (int) animation.getAnimatedValue()));
                }
            });
            colorAnimation.setStartDelay(moveDuration);
            colorAnimation.setDuration(revealAnim.getDuration());
            colorAnimation.start();


        } else {

            // get the center for the clipping circle
            int cx = creationMenu.getMeasuredWidth() / 2;
            int cy = creationMenu.getMeasuredHeight() / 2;

            // get the initial radius for the clipping circle
            int initialRadius = creationMenu.getWidth() / 2;

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(creationMenu, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    creationMenu.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
            fab.show();
        }
    }

    private void setupAbilityView(int viewId, final AbilityAccessor abilityAccessor) {

        final View abilityView = findViewById(viewId);
        if (abilityView != null) {

            abilityView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Only show dialog when a character is present
                    if (!CharacterControl.hasCurrentCharacter())
                        return false;

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
                    return true;
                }
            });

//            abilityView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    // Only show dialog when a character is present
//                    if (!CharacterControl.hasCurrentCharacter())
//                        return;
//
//                    LayoutUtils.showEditDialog(getActivity(), R.layout.edit_ability_score,
//                            abilityAccessor.getName(),
//                            new LayoutUtils.EditViewCallback() {
//                                @Override
//                                public void EditView(View view) {
//                                    // Set values of the picker
//                                    NumberPicker picker = (NumberPicker) view.findViewById(R.id.ability_edit_description);
//                                    picker.setMinValue(0);
//                                    picker.setMaxValue(24);
//                                    picker.setWrapSelectorWheel(false);
//                                    picker.setValue(abilityAccessor.get());
//                                }
//                            }, new LayoutUtils.DismissDialogCallback() {
//                                @Override
//                                public void OnDialogDismissed(View view) {
//                                    // Confirm values
//                                    NumberPicker picker = (NumberPicker) view.findViewById(R.id.ability_edit_description);
//                                    abilityAccessor.set(picker.getValue());
//                                    // TODO fix line below: character should listen for this itself?
//                                    CharacterControl.setCurrentCharacter(CharacterControl.getCurrentCharacter());
//                                }
//                            });
//
//                }
//            });
        }
    }

    private interface AbilityAccessor {

        void set(int value);

        int get();

        String getName();
    }

    private ItemClickListener itemClickListener = new ItemClickListener();

    private static class ItemClickListener
            implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BaseItemView itemView = (BaseItemView) view;
            if (itemView != null)
                itemView.onClick();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            BaseItemView itemView = (BaseItemView) view;
            if (itemView != null) {
                itemView.onLongClick();
                return true;
            }
            return false;
        }
    }
}

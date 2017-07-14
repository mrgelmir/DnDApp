package be.sanderdecleer.dndapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

import be.sanderdecleer.dndapp.adapters.BaseItemAdapter;
import be.sanderdecleer.dndapp.controllers.AbilityViewController;
import be.sanderdecleer.dndapp.controllers.ItemViewController;
import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.AbilityModel;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.CharacterModel;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.model.character.FeatureModel;
import be.sanderdecleer.dndapp.model.character.WeaponModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.utils.CharacterProvider;
import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.views.ArmorClassView;
import be.sanderdecleer.dndapp.views.HitPointsView;
import be.sanderdecleer.dndapp.views.ItemViewType;
import be.sanderdecleer.dndapp.views.SpeedView;


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
            // For now we just don't show this fragment without a character
            return;
        }

        // Get Character reference
        CharacterModel currentCharacter = CharacterControl.getCurrentCharacter();

        // Ability scores
        setAbilityScore(R.id.ability_score_STR, currentCharacter.getSTR());
        setAbilityScore(R.id.ability_score_DEX, currentCharacter.getDEX());
        setAbilityScore(R.id.ability_score_CON, currentCharacter.getCON());
        setAbilityScore(R.id.ability_score_INT, currentCharacter.getINT());
        setAbilityScore(R.id.ability_score_WIS, currentCharacter.getWIS());
        setAbilityScore(R.id.ability_score_CHA, currentCharacter.getCHA());

        // Armor class
        ArmorClassView armorClassView = (ArmorClassView) findViewById(R.id.character_AC);
        armorClassView.setupItemView(armorClassView);

        // Hit points
        HitPointsView hitPointsView = (HitPointsView) findViewById(R.id.character_HP);
        hitPointsView.setupItemView(hitPointsView);

        // Speed
        SpeedView speedView = (SpeedView) findViewById(R.id.character_Speed);
        speedView.setupItemView(speedView);

        // All other features, items, resources, spells...
        ArrayList<BaseItem> items = new ArrayList<>();
        if (CharacterControl.hasCurrentCharacter()) {
            items.addAll(currentCharacter.weapons);
            items.addAll(currentCharacter.expendables);
            items.addAll(currentCharacter.abilities);
        }
        baseItemAdapter.clear();
        baseItemAdapter.addAll(items);
        baseItemAdapter.notifyDataSetChanged();

        findViewById(R.id.scroll_view).invalidate();
    }

    // Custom methods
    private void setAbilityScore(int attributeViewID, final AbilityModel data) {

        ViewGroup abilityView = (ViewGroup) findViewById(attributeViewID);
        final AbilityViewController viewController = (AbilityViewController) data.getViewController();
        viewController.setupInfoView(abilityView);

        // Subscribe to clicks
        abilityView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ItemDialogFragment.createItemViewDialog(data, ItemViewType.EDIT)
                        .setConfirmListener(viewController)
                        .show(getContext());
                return true;
            }
        });
        abilityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDialogFragment.createItemViewDialog(data, ItemViewType.INFO).show(getContext());
            }
        });

    }

    private void attachClickListeners() {

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
                    // Create model and add it to the characters
                    WeaponModel weaponModel = WeaponModel.getEmpty();
                    CharacterControl.getCurrentCharacter().addWeapon(weaponModel);
                    CharacterControl.tryCharacterChanged();

                    // Show edit view for the model
                    ItemDialogFragment
                            .createItemViewDialog(weaponModel, ItemViewType.EDIT)
                            .show(getContext());

                }
            }
        });
        createFeatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
                if (CharacterControl.hasCurrentCharacter()) {

                    // Create model and add it to the character
                    FeatureModel featureModel = FeatureModel.getBasic(
                            getResources().getString(R.string.feature_default_title),
                            getResources().getString(R.string.feature_default_description));
                    CharacterControl.getCurrentCharacter().addFeature(featureModel);
                    CharacterControl.tryCharacterChanged();

                    // Show edit view for the model
                    ItemDialogFragment
                            .createItemViewDialog(featureModel, ItemViewType.EDIT)
                            .show(getContext());
                }
            }
        });
        createExpendableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCreationMenu();
                if (CharacterControl.hasCurrentCharacter()) {

                    // Create model and add to character
                    ExpendableModel expendableModel = ExpendableModel.getEmpty();
                    CharacterControl.getCurrentCharacter().addExpendable(expendableModel);
                    CharacterControl.tryCharacterChanged();

                    // Show edit view for the model
                    ItemDialogFragment
                            .createItemViewDialog(expendableModel, ItemViewType.EDIT)
                            .show(getContext());
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

            final int moveDuration = 50;

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

    private ItemClickListener itemClickListener = new ItemClickListener();

    private static class ItemClickListener
            implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get item
            BaseItem item = (BaseItem) parent.getAdapter().getItem(position);
            // Show info view
            ItemDialogFragment.createItemViewDialog(item, ItemViewType.INFO)
                    .show(parent.getContext());
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // Get item
            BaseItem item = (BaseItem) parent.getAdapter().getItem(position);
            // Show edit view
            if (item != null) {
                ItemDialogFragment.createItemViewDialog(item, ItemViewType.EDIT)
                        .show(parent.getContext());
                return true;
            }
            return false;
        }
    }
}

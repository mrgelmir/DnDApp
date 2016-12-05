package be.sanderdecleer.dndapp.activities;

import android.app.AlertDialog;
import android.support.design.widget.TabLayout;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import be.sanderdecleer.dndapp.adapters.CharacterSheetPageAdapter;
import be.sanderdecleer.dndapp.dialog_fragments.InfoDialogFragment;
import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.ExpendableModel;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.model.WeaponModel;
import be.sanderdecleer.dndapp.utils.CharacterFileUtil;
import be.sanderdecleer.dndapp.utils.EditControl;
import be.sanderdecleer.dndapp.utils.LayoutUtils;

public class CharacterSheet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CharacterControl.Listener,
        EditControl.EditModeChangedListener {

    private static final String CHARACTER_KEY = "CharacterSheet.CharacterKey";

    private ArrayList<CharacterControl.Listener> characterChangedListeners;

    private SubMenu characterSubMenu;
    private Menu optionsMenu;

    private ViewPager mPager;
    private FragmentPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private View mPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Set view
        setContentView(R.layout.activity_character_sheet);

        // Setup and hide Floating action button
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Link Activity name and option menu to toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add drawer to toolbar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null)
            drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);


        // FRAGMENTS

        // Viewpager setup
        mAdapter = new CharacterSheetPageAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Give the TabLayout the ViewPager
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mPager);

        // Get the placeholder
        mPlaceholder = findViewById(R.id.placeholder);

        // create character changed listeners array
        characterChangedListeners = new ArrayList<>();

        // TEMP
//        createTestData();

        // Load saved characters
        populateCharacterMenu();

        // Subscribe to edit mode changes
        EditControl.addListener(this);

        // Subscribe to character changed
        CharacterControl.getInstance().addListener(this);

        // Get character if it exists
        try {
            CharacterControl.setCurrentCharacter((CharacterModel) savedInstanceState.getParcelable(CHARACTER_KEY));
//            Parcel characterParcel = savedInstanceState.getParcelable(CHARACTER_KEY);
//            CharacterControl.getInstance().setCharacter(new CharacterModel(characterParcel));
        } catch (NullPointerException e) {
            onCharacterChanged();
            // No character -> todo
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CHARACTER_KEY, CharacterControl.getInstance().getCharacter());
    }

    @Override
    public void onBackPressed() {
        // Close drawer if opened, otherwise do native back action
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        // TODO: 21/11/2016 Interpose a back to main character sheet here before native action
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Save menu for later use
        this.optionsMenu = menu;

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.character_sheet, menu);

        updateOptionsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        boolean handled = false;

        switch (id) {
            // Toggle between regular and edit mode
            case R.id.action_edit:

                // toggle edit mode
                EditControl.toggleEditMode();

                // Set corresponding title.
                // TODO: Move this to an EditControl.EditModeChangedListener later
                item.setTitle(getString(EditControl.isEditMode() ?
                        R.string.action_edit_done :
                        R.string.action_edit));

                handled = true;
                break;

            // Save the character (Should this be automatic?)
            case R.id.action_save:

                // Save character to file
                CharacterFileUtil.saveCharacter(this, CharacterControl.getCurrentCharacter());
                handled = true;

                // If we are currently editing: Stop editing
                if (EditControl.isEditMode()) {
                    EditControl.setEditMode(false);
                }

                // Update the options menu to hide the save icon if nothing went wrong
                CharacterControl.getCurrentCharacter().hasChanges = false;
                updateOptionsMenu();

                // Just in case this is a newly added character -> Update menu
                populateCharacterMenu();
                break;

            // Rename character
            case R.id.action_edit_name:

                // Show name popup

                LayoutUtils.showEditDialog(this, R.layout.edit_text, getString(R.string.name_edit_title),
                        new LayoutUtils.EditViewCallback() {
                            @Override
                            public void EditView(View view) {
                                EditText textView = (EditText) view.findViewById(R.id.edit_field);
                                if (textView != null) {
                                    textView.setText(CharacterControl.getCurrentCharacter().name);
                                    textView.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                                    textView.selectAll();

                                    // TODO: 15/09/2016 Open keyboard
                                }
                            }
                        }, new LayoutUtils.DismissDialogCallback() {
                            @Override
                            public void OnDialogDismissed(View view) {
                                EditText textView = (EditText) view.findViewById(R.id.edit_field);
                                if (textView != null) {
                                    CharacterControl.getCurrentCharacter().name = textView.getText().toString();
                                    updateCharacter();
                                }
                            }
                        });

                handled = true;
                break;
            case R.id.action_delete:

                // TODO: 15/09/2016

                DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case AlertDialog.BUTTON_POSITIVE:
                                // set current character to null and remove file
                                CharacterFileUtil.deleteCharacter(CharacterSheet.this,
                                        CharacterControl.getCurrentCharacter());
                                CharacterControl.setCurrentCharacter(null);
                                // re-populate character menu
                                populateCharacterMenu();
                                break;
                            case AlertDialog.BUTTON_NEGATIVE:
                                // do nothing
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton(R.string.delete, deleteListener)
                        .setNegativeButton(R.string.cancel, deleteListener)
                        .show();


                handled = true;
                break;
        }

        // If handled, no need for underlying logic
        return handled || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Stop editing if editing
        if (EditControl.isEditMode())
            EditControl.setEditMode(false);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Catch specific actions here
        if (id == R.id.nav_add_character) {

            LayoutUtils.showEditTextDialog(this, getString(R.string.name_edit_title),
                    getString(R.string.name_default), new LayoutUtils.TextResultCallback() {
                        @Override
                        public void GetTextResult(String string) {
                            CharacterModel newModel = new CharacterModel(string);
                            CharacterControl.getInstance().setCharacter(newModel);
                        }
                    });

            EditControl.setEditMode(true);

        } else if (id == R.id.nav_clear_character) {

            // TODO: 15/09/2016 Show a popup requesting confirmation of deletion
            CharacterControl.getInstance().setCharacter(null);
        } else {
            // This is (probably) a character being selected
            // Load corresponding file and display

            // Do a dirty 'loop' over characterSubMenu children and un-check
            int index = 0;
            MenuItem otherItem = characterSubMenu.getItem(index);
            try {
                while (otherItem != null) {
                    otherItem.setChecked(false);
                    otherItem = characterSubMenu.getItem(index);
                    ++index;
                }
            } catch (IndexOutOfBoundsException e) {
                // silently fail here
            }

            // Check this item to see current
            item.setChecked(true);

            // TEMP load item from filename given
            CharacterModel loadedCharacter = CharacterFileUtil.loadCharacter(this, item.getTitle().toString());
            CharacterControl.getInstance().setCharacter(loadedCharacter);

            // Dirty way of keeping the save button hidden. Loaded characters have no changes
            CharacterControl.getInstance().getCharacter().hasChanges = false;
            updateOptionsMenu();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // CHARACTER MANAGEMENT

    // CharacterControl Listener Implementation
    @Override
    public void onCharacterChanged() {
        updateCharacter();
        mAdapter.notifyDataSetChanged();

        // Show pager or placeholder
        boolean hasCharacter = CharacterControl.getCurrentCharacter() != null;

        mPager.setVisibility(hasCharacter? View.VISIBLE : View.GONE);
        mPlaceholder.setVisibility(hasCharacter? View.GONE : View.VISIBLE);
        // Maybe animate this later
        mTabLayout.setVisibility(hasCharacter? View.VISIBLE : View.GONE);
    }

    private void updateCharacter() {

        // Set activeCharacter name as title
        if (CharacterControl.getInstance().getCharacter() != null) {
            getSupportActionBar().setTitle(CharacterControl.getInstance().getCharacter().name);
            CharacterControl.getInstance().getCharacter().hasChanges = true;
        } else {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        // Update the options menu accordingly
        updateOptionsMenu();

//        // Notify the listening fragments
//        for (CharacterControl.Listener characterChangedListener : characterChangedListeners) {
//            characterChangedListener.onCharacterChanged();
//        }

    }

    private void populateCharacterMenu() {

        // get the saved characters
        List<String> characters = CharacterFileUtil.getAvailableCharacters(this);

        // get a reference to the navigation
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        if (nav != null) {
            // This is using a hard-coded index for now: fix this later
            characterSubMenu = nav.getMenu().findItem(R.id.nav_character_list).getSubMenu();
            characterSubMenu.clear();

            for (int i = 0; i < characters.size(); ++i) {
                characterSubMenu.add(0, i, 0, characters.get(i));
            }

            // Make sure the menu redraws
            invalidateOptionsMenu();
        }
    }

    @Override
    public void OnEditModeChanged(boolean isEditMode) {
        updateCharacter();
    }

    // OPTION MENU FUNCTIONALITY
    private void updateOptionsMenu() {

        if (optionsMenu == null) {
            // TODO: 15/09/2016 Add error handling
            return;
        }

//        MenuItem editAction = optionsMenu.findItem(R.id.action_edit);
//        MenuItem editNameAction = optionsMenu.findItem(R.id.action_edit_name);
        MenuItem saveAction = optionsMenu.findItem(R.id.action_save);
        MenuItem editAction = optionsMenu.findItem(R.id.action_edit);

        optionsMenu.setGroupVisible(R.id.action_group, CharacterControl.getCurrentCharacter() != null);

        if (CharacterControl.getCurrentCharacter() == null) {
            // Disable menu

        } else {

            saveAction.setVisible(EditControl.isEditMode()
                    && CharacterControl.getCurrentCharacter().hasChanges);
            editAction.setVisible(!EditControl.isEditMode());
        }
    }

    // TEST

    // Not really needed -> (re-)move?
    private void createTestData() {

        // Test character 1
        CharacterModel character1 = new CharacterModel("Guy Stormcrow");
        character1.setAbilityScores(14, 16, 14, 11, 14, 13);
        character1.HP_current = 8;
        character1.HP_max = 13;
        character1.hitDice_max = "2d8";
        character1.AC = 15;
        character1.speed = 40;
        character1.addAbility(new FeatureModel("Martial Arts", "Do bad-ass stuff"));
        character1.addAbility(new FeatureModel("Wanderer", "Terrain stuff and such"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
        WeaponModel shortSword = WeaponModel.getEmpty(this);
        shortSword.nickname = "";
        shortSword.weaponType = "Short sword";
        shortSword.weaponDamage = "1d6 + 3 slashing";
        shortSword.weaponToHit = "+5";
        shortSword.weaponFeatures = "finesse, light";
        WeaponModel dagger = WeaponModel.getEmpty(this);
        dagger.weaponType = "Dagger";
        dagger.weaponDamage = "1d4 + 3 piercing";
        dagger.weaponToHit = "+5";
        dagger.weaponFeatures = "finesse, light, range(30/60ft)";
        character1.addWeapon(dagger);
        character1.addWeapon(shortSword);
        ExpendableModel kiExpendable = new ExpendableModel("KI points", 2, 1);
        ExpendableModel superiorityExpendable = new ExpendableModel("Superiority Dice", 10, 5);
        character1.addExpendable(kiExpendable);
        character1.addExpendable(superiorityExpendable);

        // Test character 2
        CharacterModel character2 = new CharacterModel("Derek the Dude");
        character2.setAbilityScores(10, 12, 8, 20, 17, 16);
        character2.HP_current = 8;
        character2.HP_max = 8;
        character2.hitDice_max = "2d6";
        character2.AC = 13;
        character2.speed = 30;
        character2.addAbility(new FeatureModel("Spell 1", "This is an even more bad-ass skill description"));
        character2.addAbility(new FeatureModel("Magic bolt", "This is an even more bad-ass,\nmulti-line,\nskill description"));
        WeaponModel dagger2 = WeaponModel.getEmpty(this);
        dagger2.nickname = "lil' edge";
        dagger2.weaponType = "Dagger";
        dagger2.weaponDamage = "1d4 + 3 piercing";
        dagger2.weaponToHit = "+5";
        dagger2.weaponFeatures = "finesse, light, range(30/60ft)";
        character2.addWeapon(dagger2);

        // Temp save these two characters as base characters
        CharacterFileUtil.saveCharacter(this, character1);
        CharacterFileUtil.saveCharacter(this, character2);

    }


}

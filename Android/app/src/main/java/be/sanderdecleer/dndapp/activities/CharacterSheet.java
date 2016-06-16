package be.sanderdecleer.dndapp.activities;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.sanderdecleer.dndapp.adapters.BaseCharacterAdapter;
import be.sanderdecleer.dndapp.adapters.ExpendableAdapter;
import be.sanderdecleer.dndapp.adapters.FeatureAdapter;
import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.adapters.WeaponAdapter;
import be.sanderdecleer.dndapp.fragments.CharacterOverview;
import be.sanderdecleer.dndapp.model.ExpendableModel;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.model.WeaponModel;
import be.sanderdecleer.dndapp.utils.CharacterFileUtil;
import be.sanderdecleer.dndapp.utils.OnClickListenerEditable;

public class CharacterSheet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CharacterModel activeCharacter;

    private ArrayList<OnCharacterChangedListener> characterChangedListeners;

    private SubMenu characterSubMenu;

    private CharacterOverview overviewFragment;

    private static boolean is_edit_mode = false;

    public static boolean isEditMode() {
        return is_edit_mode;
    }

    // TEMP

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: 1/06/2016 Implement View pager
//        ViewPager pager = new ViewPager(this);
//        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(getFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public int getCount() {
//                return 0;
//            }
//        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Link Activity name and option menu to toolbar
        setSupportActionBar(toolbar);

        // Setup and hide Floating action button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            fab.hide();
        }

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
        characterChangedListeners = new ArrayList<>();

        // TODO: 16/06/2016 maybe create fragment here, instead of finding it
        // -> some fragments won't be needed for some characters

        // get the overview fragment
        overviewFragment = (CharacterOverview) getFragmentManager().findFragmentById(R.id.fragment_character_overview);
        characterChangedListeners.add(overviewFragment);

        // Load saved characters
        populateCharacterMenu();

        // TEMP
//        createTestData();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.character_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_edit) {

            // Toggle between regular and edit mode
            is_edit_mode = !is_edit_mode;

            // Set corresponding title
            item.setTitle(getString(is_edit_mode ?
                    R.string.action_edit_done :
                    R.string.action_edit));

            return true;
        } else if (id == R.id.action_save) {
            // Save the character (Should this be automatic?)
            CharacterFileUtil.saveCharacter(this, activeCharacter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Catch specific actions here
        if (id == R.id.nav_add_character) {
            // TODO: create new character

        } else if (id == R.id.nav_clear_character) {
            setActiveCharacter(null);
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
            setActiveCharacter(loadedCharacter);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Set the current character and update all views/fragments
     *
     * @param character the character to display
     */
    public void setActiveCharacter(CharacterModel character) {
        this.activeCharacter = character;

        // Set activeCharacter name as title
        if (activeCharacter != null)
            getSupportActionBar().setTitle(activeCharacter.name);
        else
            getSupportActionBar().setTitle(R.string.app_name);

        // Update the listening fragments
        for (OnCharacterChangedListener characterChangedListener : characterChangedListeners) {
            characterChangedListener.onCharacterChanged(activeCharacter);
        }

        findViewById(R.id.scrollView).invalidate();

    }

    private void populateCharacterMenu() {

        // get the saved characters
        List<String> characters = CharacterFileUtil.getAvailableCharacters(this);

        // get a reference to the navigation
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        if (nav != null) {
            // This is using a hard-coded index for now: fix this later
            characterSubMenu = nav.getMenu().findItem(R.id.nav_character_list).getSubMenu();

            for (int i = 0; i < characters.size(); ++i) {
                characterSubMenu.add(0, i, 0, characters.get(i));
            }

            // Make sure the menu redraws
            invalidateOptionsMenu();
        }
    }

    // Not really needed -> move
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
        WeaponModel shortSword = new WeaponModel();
        shortSword.nickname = "";
        shortSword.weaponType = "Short sword";
        shortSword.weaponDamage = "1d6 + 3 slashing";
        shortSword.weaponToHit = "+5";
        shortSword.weaponFeatures = "finesse, light";
        WeaponModel dagger = new WeaponModel();
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
        WeaponModel dagger2 = new WeaponModel();
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

    public interface OnCharacterChangedListener {
        void onCharacterChanged(CharacterModel character);
    }
}

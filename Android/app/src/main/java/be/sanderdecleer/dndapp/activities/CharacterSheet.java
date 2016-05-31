package be.sanderdecleer.dndapp.activities;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.sanderdecleer.dndapp.adapters.BaseCharacterAdapter;
import be.sanderdecleer.dndapp.adapters.ExpendableAdapter;
import be.sanderdecleer.dndapp.adapters.FeatureAdapter;
import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.adapters.WeaponAdapter;
import be.sanderdecleer.dndapp.model.ExpendableModel;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.model.WeaponModel;
import be.sanderdecleer.dndapp.utils.CharacterFileUtil;

public class CharacterSheet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CharacterModel activeCharacter;

    private ArrayList<BaseCharacterAdapter> characterAdapters;

    private SubMenu characterSubMenu;

    // TEMP
    private CharacterModel character1;
    private CharacterModel character2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        // ADAPTERS
        characterAdapters = new ArrayList<>(3);

        // Create feature adapter and link to view
        FeatureAdapter featureAdapter = new FeatureAdapter(this, R.layout.p_ability_view_item);
        characterAdapters.add(featureAdapter);
        AdapterView abilitiesView = (AdapterView) findViewById(R.id.feature_list);
        if (abilitiesView != null) {
            abilitiesView.setAdapter(featureAdapter);
        }

        // Create weapon adapter and link to view
        WeaponAdapter weaponAdapter = new WeaponAdapter(this, R.layout.p_weapon_view_item);
        characterAdapters.add(weaponAdapter);
        AdapterView weaponView = (AdapterView) findViewById(R.id.weapon_list);
        if (weaponView != null) {
            weaponView.setAdapter(weaponAdapter);
        }

        // Create expandable view and link to view
        ExpendableAdapter expendableAdapter = new ExpendableAdapter(this, R.layout.p_expendable_view);
        characterAdapters.add(expendableAdapter);
        AdapterView expendableView = (AdapterView) findViewById(R.id.expendables_list);
        if (expendableView != null)
            expendableView.setAdapter(expendableAdapter);

        // TEMP

        // Test character 1
        character1 = new CharacterModel("Guy Stormcrow");
        character1.setAbilityScores(14, 16, 14, 11, 14, 13);
        character1.HP_current = 8;
        character1.HP_max = 13;
        character1.hitDice_max = "2d8";
        character1.AC = 15;
        character1.speed = 40;
        character1.addAbility(new FeatureModel("Martial Arts", "Do bad-ass stuff"));
        character1.addAbility(new FeatureModel("Wanderer", "Terrain stuff and such"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
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
        character1.addExpendable(kiExpendable);
        character1.addExpendable(superiorityExpendable);
        character1.addExpendable(kiExpendable);
        character1.addExpendable(superiorityExpendable);

        // Test character 2
        character2 = new CharacterModel("Derek the Dude");
        character2.setAbilityScores(10, 12, 8, 20, 17, 16);
        character2.HP_current = 8;
        character2.HP_max = 8;
        character1.hitDice_max = "2d6";
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

        // Temp save these two characters
        CharacterFileUtil.saveCharacter(this, character1);
        CharacterFileUtil.saveCharacter(this, character2);

        // Temp check saved characters
        List<String> characters = CharacterFileUtil.getAvailableCharacters(this);

        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        if (nav != null) {
            // This is hard-coded for now: fix this later
            characterSubMenu = nav.getMenu().getItem(1).getSubMenu();

            for (int i = 0; i < characters.size(); ++i) {
                characterSubMenu.add(0, i, 0, characters.get(i));
            }

            // Make sure the menu redraws
            invalidateOptionsMenu();
        }

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

        // noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_edit) {
            return true;
        } else if (id == R.id.action_save) {
            CharacterFileUtil.saveCharacter(this, activeCharacter);
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
            setActiveCharacter(CharacterFileUtil.loadCharacter(this, item.getTitle().toString()));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Set the current character and update all views
     *
     * @param character the character to display
     */
    public void setActiveCharacter(CharacterModel character) {
        this.activeCharacter = character;

        // Set activeCharacter name
        getSupportActionBar().setTitle(activeCharacter.name);

        // Update Ability scores
        setAbilityScore(R.id.ability_score_STR, R.string.ability_score_STR, activeCharacter.STR);
        setAbilityScore(R.id.ability_score_DEX, R.string.ability_score_DEX, activeCharacter.DEX);
        setAbilityScore(R.id.ability_score_CON, R.string.ability_score_CON, activeCharacter.CON);
        setAbilityScore(R.id.ability_score_INT, R.string.ability_score_INT, activeCharacter.INT);
        setAbilityScore(R.id.ability_score_WIS, R.string.ability_score_WIS, activeCharacter.WIS);
        setAbilityScore(R.id.ability_score_CHA, R.string.ability_score_CHA, activeCharacter.CHA);

        // Update other stats
        String formattedAC = String.format(getResources().getString(R.string.character_AC),
                activeCharacter.AC);
        ((TextView) findViewById(R.id.character_AC)).setText(formattedAC);

        String formattedHP = String.format(getResources().getString(R.string.character_HP),
                activeCharacter.HP_current, activeCharacter.HP_max);
        ((TextView) findViewById(R.id.character_HP)).setText(formattedHP);

        String formattedSpeed = String.format(getResources().getString(R.string.character_Speed), activeCharacter.speed);
        ((TextView) findViewById(R.id.character_Speed)).setText(formattedSpeed);


        // Update the adapters
        for (BaseCharacterAdapter adapter : characterAdapters) {
            adapter.setCharacter(activeCharacter);
        }

        findViewById(R.id.scrollView).invalidate();

    }

    private void setAbilityScore(int attributeViewID, int attributeStringId, int attributeValue) {

        View attributeView = findViewById(attributeViewID);

        TextView attrNameView = (TextView) attributeView.findViewById(R.id.attribute_name);
        TextView attrScoreView = (TextView) attributeView.findViewById(R.id.attribute_score);

        attrNameView.setText(getResources().getText(attributeStringId));
        int attributeModifier = Math.round((attributeValue - 10) / 2);
        attrScoreView.setText(String.format(getResources().getString(R.string.ability_score_value),
                attributeValue, attributeModifier < 0 ? "" : "+", attributeModifier));
    }
}

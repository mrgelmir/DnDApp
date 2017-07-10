package be.sanderdecleer.dndapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.adapters.CharacterSheetPageAdapter;
import be.sanderdecleer.dndapp.model.CharacterDescription;
import be.sanderdecleer.dndapp.model.character.CharacterModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.utils.CharacterFileUtil;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.utils.TestCharacterProvider;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class CharacterSheet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CharacterControl.Listener {

    private static final String CHARACTER_KEY = "CharacterSheet.CharacterKey";

    private SubMenu characterSubMenu;
    private Menu optionsMenu;

    private ViewPager mPager;
    private CharacterSheetPageAdapter mAdapter;
    private TabLayout mTabLayout;
    private View mPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Set view
        setContentView(R.layout.activity_character_sheet);

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

        // Listen to navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);


        // Get the characters saved on the device
        final List<CharacterDescription> characterDescriptions =
                CharacterFileUtil.getAvailableCharacters(this);

        // FRAGMENTS

        // Viewpager setup
        mAdapter = new CharacterSheetPageAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Give the TabLayout the ViewPager
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mPager);
        // Tabs gone for now: no spells
        mTabLayout.setVisibility(View.GONE);

        // Set up the placeholder view
        updatePlaceholderView(characterDescriptions);

        // Load saved characters
        populateCharacterMenu(characterDescriptions);

        // Subscribe to character changed
        CharacterControl.getInstance().addListener(this);

        // Get character if it exists
        try {
            CharacterControl.setCurrentCharacter(
                    (CharacterModel) savedInstanceState.getParcelable(CHARACTER_KEY));
        } catch (NullPointerException e) {
            onCharacterChanged();
            // No character -> todo
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (CharacterControl.getInstance().hasCharacter()) {
            outState.putParcelable(CHARACTER_KEY, CharacterControl.getInstance().getCharacter());
        }
    }

    @Override
    public void onBackPressed() {

        boolean backConsumed = false;

        // Close drawer if opened, otherwise do native back action
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            backConsumed = true;
        }

        if (!backConsumed) {
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

                // Not used for now

                handled = true;
                break;

            // Save the character (Should this be automatic?)
            case R.id.action_save:

                // Save character to file
                CharacterFileUtil.saveCharacter(this, CharacterControl.getCurrentCharacter());
                handled = true;

                CharacterControl.getCurrentCharacter().hasChanges = false;

                // Just in case this is a newly added character -> Update menu
//                populateCharacterMenu();

                // Hide button
                item.setEnabled(false);

                handled = true;
                break;

            // Rename character
            case R.id.action_edit_name:

                // Show name popup
                LayoutUtils.showEditTextDialog(this, getString(R.string.name_edit_title),
                        CharacterControl.getCurrentCharacter().getName(), new LayoutUtils.TextResultCallback() {
                            @Override
                            public void GetTextResult(String string) {

                                // Delete character, rename and save again. Might go wrong ...
                                CharacterFileUtil.deleteCharacter(
                                        CharacterSheet.this,
                                        CharacterControl.getCurrentCharacter());
                                CharacterControl.getCurrentCharacter().setName(string);
                                CharacterFileUtil.saveCharacter(
                                        CharacterSheet.this,
                                        CharacterControl.getCurrentCharacter());

                                updateCharacter();
                                updateAvailableCharacters();
                            }
                        });

                handled = true;
                break;
            case R.id.action_delete:

                DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case AlertDialog.BUTTON_POSITIVE:
                                // set current character to null and remove file
                                CharacterFileUtil.deleteCharacter(CharacterSheet.this,
                                        CharacterControl.getCurrentCharacter());
                                CharacterControl.setCurrentCharacter(null);

                                updateAvailableCharacters();
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

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_add_character:
                ShowCreateCharacterDialog();
                break;
            case R.id.nav_clear_character:
                // TODO: 15/09/2016 Show a popup requesting confirmation of deletion
                CharacterControl.getInstance().setCharacter(null);
                break;

            case R.id.nav_create_test_data:
                // Create test data

                // Temp save characters as base characters
                CharacterFileUtil.saveCharacter(this, TestCharacterProvider.createTestMonk());
                CharacterFileUtil.saveCharacter(this, TestCharacterProvider.createTestSpellcaster());

                updateAvailableCharacters();
                break;
            default:

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

                // Get the action provider and run the default action we added
                // (ie. loading the character)
                MenuItemCompat.getActionProvider(item).onPerformDefaultAction();

                // Dirty way of keeping the save button hidden. Loaded characters have no changes
                CharacterControl.getInstance().getCharacter().hasChanges = false;
                updateOptionsMenu();
                break;
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
        boolean hasCharacter = CharacterControl.hasCurrentCharacter();

        mPager.setVisibility(hasCharacter ? View.VISIBLE : View.GONE);
        mPlaceholder.setVisibility(hasCharacter ? View.GONE : View.VISIBLE);

        // Maybe animate this later
        mTabLayout.setVisibility(hasCharacter ? View.VISIBLE : View.GONE);
        // TODO remove (this is because spell tab isn't implemented)
        mTabLayout.setVisibility(View.GONE);

        // Hide tab layout on landscape until another tab layout option is found (header too big)
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            mTabLayout.setVisibility(View.GONE);
        }

        // Show save icon
        if (optionsMenu != null) {
            MenuItem saveItem = optionsMenu.findItem(R.id.action_save);

            int i = 5;

            saveItem.setEnabled(true);
        }
    }

    private void updateCharacter() {

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);

        // Set activeCharacter name as title
        if (CharacterControl.getInstance().hasCharacter()) {
            getSupportActionBar().setTitle(CharacterControl.getInstance().getCharacter().getName());
            CharacterControl.getInstance().getCharacter().hasChanges = true;

            if (fab != null)
            fab.show();
        } else {
            getSupportActionBar().setTitle(R.string.app_name);
            if (fab != null)
                fab.setVisibility(View.INVISIBLE);
        }

        // Update the options menu accordingly
        updateOptionsMenu();

    }

    private void populateCharacterMenu(List<CharacterDescription> characterDescriptions) {

        // get a reference to the navigation
        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        if (nav != null) {
            // This is using a hard-coded index for now: fix this later
            characterSubMenu = nav.getMenu().findItem(R.id.nav_character_list).getSubMenu();
            characterSubMenu.clear();

            for (int i = 0; i < characterDescriptions.size(); ++i) {

                final CharacterDescription characterDescription = characterDescriptions.get(i);
                MenuItem menuItem = characterSubMenu.add(0, i, 0, characterDescription.getCharacterName());

                // Set an actionProvider and use this to hack our load action in
                // Not the correct way to do this I think
                MenuItemCompat.setActionProvider(menuItem, new ActionProvider(this) {
                    @Override
                    public View onCreateActionView() {
                        return null;
                    }

                    @Override
                    public boolean onPerformDefaultAction() {
                        CharacterModel loadedCharacter = CharacterFileUtil.
                                loadCharacter(characterDescription.getCharacterFile());
                        CharacterControl.getInstance().setCharacter(loadedCharacter);
                        return true;
                    }
                });

            }

            // Make sure the menu redraws
            invalidateOptionsMenu();
        }
    }

    private void updateOptionsMenu() {

        if (optionsMenu == null) {
            // TODO: 15/09/2016 Add error handling
            return;
        }

//        MenuItem editNameAction = optionsMenu.findItem(R.id.action_edit_name);
        MenuItem saveAction = optionsMenu.findItem(R.id.action_save);
        MenuItem editAction = optionsMenu.findItem(R.id.action_edit);

        optionsMenu.setGroupVisible(R.id.action_group, CharacterControl.getCurrentCharacter() != null);

        if (!CharacterControl.hasCurrentCharacter()) {
            saveAction.setEnabled(false);
        }

        //For now do not show edit items: edit on long tap\
        editAction.setVisible(false);

    }

    // PLACEHOLDER VIEW
    private void updatePlaceholderView(List<CharacterDescription> characterDescriptions) {
        // Get the placeholder
        mPlaceholder = findViewById(R.id.placeholder);


        // Set up the new Character button
        final Button createButton = (Button) mPlaceholder.findViewById(R.id.no_character_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCreateCharacterDialog();
            }
        });




        // Load existing character view
        final LinearLayout characterSelector = (LinearLayout) mPlaceholder.findViewById(R.id.no_character_load);
        final TextView characterSelectorLabel = (TextView) mPlaceholder.findViewById(R.id.load_character_title);

        if (characterDescriptions.size() <= 0) {
            // Hide if no characters are available
            characterSelector.setVisibility(View.GONE);
            characterSelectorLabel.setVisibility(View.GONE);
        } else {
            // Show if characters are available
            characterSelector.setVisibility(View.VISIBLE);
            characterSelectorLabel.setVisibility(View.VISIBLE);

            // Clear current children
            characterSelector.removeAllViews();

            // Populate if available
            for (final CharacterDescription c : characterDescriptions) {

//                final Button loadButton = new Button(CharacterSheet.this, null, 0,
//                        R.style.Widget_AppCompat_Button_Borderless);
                final Button loadButton = new Button(new ContextThemeWrapper(this, R.style.Widget_AppCompat_Button_Borderless));

                // Dirty hack for the background creation
                loadButton.setBackground(createButton.getBackground().getConstantState().newDrawable());

                loadButton.setText(c.getCharacterName());
                loadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharacterControl.setCurrentCharacter(CharacterFileUtil.loadCharacter(
                                c.getCharacterFile()));
                    }
                });

                final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                characterSelector.addView(loadButton, layoutParams);

            }
        }
    }

    // HELPERS
    private void ShowCreateCharacterDialog() {
        LayoutUtils.showEditTextDialog(this, getString(R.string.name_edit_title),
                getString(R.string.name_default), new LayoutUtils.TextResultCallback() {
                    @Override
                    public void GetTextResult(String string) {
                        CharacterModel newModel = new CharacterModel(string);
                        CharacterControl.getInstance().setCharacter(newModel);
                        CharacterFileUtil.saveCharacter(
                                CharacterSheet.this,
                                CharacterControl.getCurrentCharacter());
                        updateAvailableCharacters();
                    }
                });
    }

    private void updateAvailableCharacters() {
        // Update sidebar and placeholder view
        List<CharacterDescription> availableCharacters =
                CharacterFileUtil.getAvailableCharacters(CharacterSheet.this);
        populateCharacterMenu(availableCharacters);
        updatePlaceholderView(availableCharacters);
    }
}

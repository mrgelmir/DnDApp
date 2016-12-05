package be.sanderdecleer.dndapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import be.sanderdecleer.dndapp.fragments.CharacterOverview;
import be.sanderdecleer.dndapp.fragments.SpellSheetOverview;

/**
 * Created by SD on 29/11/2016.
 */
public class CharacterSheetPageAdapter extends FragmentPagerAdapter {

    // todo make this dependant on the availability of spells, items ...
    private static final int NUM_ITEMS = 2;

    public CharacterSheetPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            default:
            case 0:
                return CharacterOverview.newInstance();
            case 1:
                return SpellSheetOverview.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            default:
            case 0:
                return "Overview";
            case 1:
                return "Spells";
        }
    }
}

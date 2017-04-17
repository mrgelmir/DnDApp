package be.sanderdecleer.dndapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import java.util.Map;

import be.sanderdecleer.dndapp.fragments.CharacterFragment;
import be.sanderdecleer.dndapp.fragments.CharacterOverview;
import be.sanderdecleer.dndapp.fragments.SpellSheetOverview;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 29/11/2016.
 */
public class CharacterSheetPageAdapter extends FragmentPagerAdapter {

    // TODO make this dependant on the availability of spells, items ...
    private int NUM_ITEMS = 1;

    private Map<Integer, CharacterFragment> activeFragmentMap;


    public CharacterSheetPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        activeFragmentMap = new ArrayMap<>();
    }


    public boolean backPressed() {

        //  Make character fragments do back press

        return false;
    }

    @Override
    public int getCount() {

        // TODO check if spells are available
//        if(CharacterControl.hasCurrentCharacter()){
//            return CharacterControl.getCurrentCharacter().
//        }
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
    public Object instantiateItem(ViewGroup container, int position) {
        CharacterFragment f = (CharacterFragment) super.instantiateItem(container, position);
        activeFragmentMap.put(position, f);
        return f;
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

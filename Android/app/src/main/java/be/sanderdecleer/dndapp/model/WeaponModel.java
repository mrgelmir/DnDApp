package be.sanderdecleer.dndapp.model;

import android.content.Context;

import be.sanderdecleer.dndapp.R;

/**
 * Created by SD on 23/05/2016.
 */
public class WeaponModel {


    public String nickname;
    public String weaponType;
    public String weaponToHit;
    public String weaponDamage; // maybe swap to a dice representation later
    public String weaponFeatures;

    public String getDisplayName() {
        if (nickname != null && !nickname.isEmpty()) {
            return nickname;
        } else {
            return weaponType;
        }
    }

    public static WeaponModel getEmpty(Context context) {
        WeaponModel weaponModel = new WeaponModel();
        weaponModel.weaponType = context.getString(R.string.weapon_default_type);
        weaponModel.weaponToHit = context.getString(R.string.weapon_default_to_hit);
        weaponModel.weaponDamage = context.getString(R.string.weapon_default_damage);
        return weaponModel;
    }

}

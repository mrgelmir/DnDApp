package be.sanderdecleer.dndapp.model;

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

}

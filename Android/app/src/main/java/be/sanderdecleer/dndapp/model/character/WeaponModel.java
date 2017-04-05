package be.sanderdecleer.dndapp.model.character;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import be.sanderdecleer.dndapp.R;

/**
 * Created by SD on 23/05/2016.
 * Holds all data of a weapon.
 */
public class WeaponModel extends BaseItem {

    public String nickname;
    public String weaponType;
    public String weaponToHit; // TODO calculate from bonuses
    public String weaponDamage; // TODO swap to a dice representation
    public String weaponFeatures;

    // TODO move this method to a factory. The context should not be used here
    public static WeaponModel getEmpty(Context context) {
        WeaponModel weaponModel = new WeaponModel();
        weaponModel.weaponType = context.getString(R.string.weapon_default_type);
        weaponModel.weaponToHit = context.getString(R.string.weapon_default_to_hit);
        weaponModel.weaponDamage = context.getString(R.string.weapon_default_damage);
        return weaponModel;
    }

    public static WeaponModel getEmpty() {
        WeaponModel weaponModel = new WeaponModel();
        // Add default things here
        weaponModel.nickname = "nickname";
        weaponModel.weaponType = "weaponType";


        return weaponModel;
    }

    private WeaponModel() {
        // Empty private constructor
    }

    private WeaponModel(Parcel in) {
        nickname = in.readString();
        weaponType = in.readString();
        weaponToHit = in.readString();
        weaponDamage = in.readString();
        weaponFeatures = in.readString();
    }

    /***
     * Shows the name of the weapon fit for display purposes.
     *
     * @return nickname, if present. Else weapon type.
     */
    public String getDisplayName() {
        if (nickname != null && !nickname.isEmpty()) {
            return nickname;
        } else {
            return weaponType;
        }
    }

    @Override
    public Type getType() {
        return Type.Weapon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeString(weaponType);
        dest.writeString(weaponToHit);
        dest.writeString(weaponDamage);
        dest.writeString(weaponFeatures);
    }

    public static final Parcelable.Creator<WeaponModel> CREATOR
            = new Creator<WeaponModel>() {
        @Override
        public WeaponModel createFromParcel(Parcel source) {
            return new WeaponModel(source);
        }

        @Override
        public WeaponModel[] newArray(int size) {
            return new WeaponModel[size];
        }
    };
}

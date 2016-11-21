package be.sanderdecleer.dndapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SD on 20/05/2016.
 */
public class CharacterModel implements Parcelable {

    // Should this all be public?

    public String name;

    public List<FeatureModel> abilities = new ArrayList<>();
    public List<WeaponModel> weapons = new ArrayList<>();
    public List<ExpendableModel> expendables = new ArrayList<>();

    // TODO: 21/11/2016 Change all the public variables to privates to allow for modifications
    // via modifiers, items, skills, boons...

    // Ability scores
    public int STR_base;
    public int DEX_base;
    public int CON_base;
    public int INT_base;
    public int WIS_base;
    public int CHA_base;

    public int speed;
    public int AC;
    public int HP_max;
    public int HP_current;

    /**
     * Does this character model have unsaved changes?
     */
    public boolean hasChanges = false;

    // TODO: 29/05/2016 replace with a list of hitDice objects
    public String hitDice_max;

    public CharacterModel(String name) {
        this.name = name;
    }

    public void setAbilityScores(int STR, int DEX, int CON, int INT, int WIS, int CHA) {
        this.STR_base = STR;
        this.DEX_base = DEX;
        this.CON_base = CON;
        this.INT_base = INT;
        this.WIS_base = WIS;
        this.CHA_base = CHA;
    }

    public void addAbility(FeatureModel ability) {
        abilities.add(ability);
    }

    public void addWeapon(WeaponModel weapon) {
        weapons.add(weapon);
    }

    public void addExpendable(ExpendableModel expendable) {
        expendables.add(expendable);
    }

    // Parcelable implementations below

    public static final Parcelable.Creator<CharacterModel> CREATOR
            = new Parcelable.Creator<CharacterModel>() {
        public CharacterModel createFromParcel(Parcel in) {
            return new CharacterModel(in);
        }

        public CharacterModel[] newArray(int size) {
            return new CharacterModel[size];
        }
    };

    private CharacterModel(Parcel in) {
        // Read data
        name = in.readString();
        STR_base = in.readInt();
        DEX_base = in.readInt();
        CON_base = in.readInt();
        INT_base = in.readInt();
        WIS_base = in.readInt();
        CHA_base = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write all data
        dest.writeString(name);
        dest.writeInt(STR_base);
        dest.writeInt(DEX_base);
        dest.writeInt(CON_base);
        dest.writeInt(INT_base);
        dest.writeInt(WIS_base);
        dest.writeInt(CHA_base);

        // TODO: All the rest :)
    }

    @Override
    public boolean equals(Object o) {

        // TODO
        // Question: what do we use here?
        // - fast version
        // - super detailed version (for testing)

        // For now, we use the name

        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        CharacterModel other = (CharacterModel ) o;

        return other.name.equals(name);
    }
}

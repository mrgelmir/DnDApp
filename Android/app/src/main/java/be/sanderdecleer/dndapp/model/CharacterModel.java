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

    // Ability scores
    public int STR;
    public int DEX;
    public int CON;
    public int INT;
    public int WIS;
    public int CHA;

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
        this.STR = STR;
        this.DEX = DEX;
        this.CON = CON;
        this.INT = INT;
        this.WIS = WIS;
        this.CHA = CHA;
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
        STR = in.readInt();
        DEX = in.readInt();
        CON = in.readInt();
        INT = in.readInt();
        WIS = in.readInt();
        CHA = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write all data
        dest.writeString(name);
        dest.writeInt(STR);
        dest.writeInt(DEX);
        dest.writeInt(CON);
        dest.writeInt(INT);
        dest.writeInt(WIS);
        dest.writeInt(CHA);
    }

    @Override
    public boolean equals(Object o) {

        // TODO
        // Question: what do we use here?
        // - fast version
        // - super detailed version (for testing)




        return super.equals(o);
    }
}

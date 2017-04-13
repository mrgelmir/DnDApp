package be.sanderdecleer.dndapp.model.character;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SD on 20/05/2016.
 * Uses the builder pattern by returning this on all edit calls
 */
public class CharacterModel implements Parcelable {

    // Should this all be public?

    private String name;

    public List<FeatureModel> abilities = new ArrayList<>();
    public List<WeaponModel> weapons = new ArrayList<>();
    public List<ExpendableModel> expendables = new ArrayList<>();

    // Ability scores
    private int STR_base;
    private int DEX_base;
    private int CON_base;
    private int INT_base;
    private int WIS_base;
    private int CHA_base;

    private int speed;
    private int AC;
    private int HP_max;
    private int HP_current;

    /**
     * Does this character model have unsaved changes?
     */
    public boolean hasChanges = false;

    // TODO: 29/05/2016 replace with a list of hitDice objects
    private String hitDice_max;

    public CharacterModel(String name) {
        this.setName(name);
    }

    // Builder methods below

    public CharacterModel setAbilityScores(int STR, int DEX, int CON, int INT, int WIS, int CHA) {
        this.setSTR(STR);
        this.setDEX(DEX);
        this.setCON(CON);
        this.setINT(INT);
        this.setWIS(WIS);
        this.setCHA(CHA);

        return this;
    }

    public CharacterModel addFeature(FeatureModel featureModel) {
        abilities.add(featureModel);
        return this;
    }

    public void removeFeature(FeatureModel ability) {
        abilities.remove(ability);
    }

    public CharacterModel addWeapon(WeaponModel weapon) {
        weapons.add(weapon);
        return this;
    }

    public void removeWeapon (WeaponModel weaponModel) {
        weapons.remove(weaponModel);
    }

    public CharacterModel addExpendable(ExpendableModel expendable) {
        expendables.add(expendable);
        return this;
    }

    public void removeExpendable(ExpendableModel expendable) {
        expendables.remove(expendable);
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

    public CharacterModel(Parcel in) {

        // Read data
        setName(in.readString());

        setSTR(in.readInt());
        setDEX(in.readInt());
        setCON(in.readInt());
        setINT(in.readInt());
        setWIS(in.readInt());
        setCHA(in.readInt());

        setSpeed(in.readInt());
        setAC(in.readInt());
        setHP_max(in.readInt());
        setHP_current(in.readInt());

        // TODO Read children
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // Write all data
        // Do not use getters or setters because these may account for modifications
        dest.writeString(name);

        dest.writeInt(STR_base);
        dest.writeInt(DEX_base);
        dest.writeInt(CON_base);
        dest.writeInt(INT_base);
        dest.writeInt(WIS_base);
        dest.writeInt(CHA_base);

        dest.writeInt(speed);
        dest.writeInt(AC);
        dest.writeInt(HP_max);
        dest.writeInt(HP_current);

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

        return other.getName().equals(getName());
    }


    // Getters and Setters below

    public String getName() {
        return name;
    }

    public CharacterModel setName(String name) {
        this.name = name;
        return this;
    }

    public int getSTR() {
        return STR_base;
    }

    public CharacterModel setSTR(int STR_base) {
        this.STR_base = STR_base;
        return this;
    }

    public int getDEX() {
        return DEX_base;
    }

    public CharacterModel setDEX(int DEX_base) {
        this.DEX_base = DEX_base;
        return this;
    }

    public int getCON() {
        return CON_base;
    }

    public CharacterModel setCON(int CON_base) {
        this.CON_base = CON_base;
        return this;
    }

    public int getINT() {
        return INT_base;
    }

    public CharacterModel setINT(int INT_base) {
        this.INT_base = INT_base;
        return this;
    }

    public int getWIS() {
        return WIS_base;
    }

    public CharacterModel setWIS(int WIS_base) {
        this.WIS_base = WIS_base;
        return this;
    }

    public int getCHA() {
        return CHA_base;
    }

    public CharacterModel setCHA(int CHA_base) {
        this.CHA_base = CHA_base;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public CharacterModel setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public int getAC() {
        return AC;
    }

    public CharacterModel setAC(int AC) {
        this.AC = AC;
        return this;
    }

    public int getHP_max() {
        return HP_max;
    }

    public CharacterModel setHP_max(int HP_max) {
        this.HP_max = HP_max;
        return this;
    }

    public int getHP_current() {
        return HP_current;
    }

    public CharacterModel setHP_current(int HP_current) {
        this.HP_current = HP_current;
        return this;
    }

    public String getHitDice_max() {
        return hitDice_max;
    }

    public CharacterModel setHitDice_max(String hitDice_max) {
        this.hitDice_max = hitDice_max;
        return this;
    }
}

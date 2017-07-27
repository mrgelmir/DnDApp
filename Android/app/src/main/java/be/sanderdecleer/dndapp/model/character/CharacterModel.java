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
    private AbilityModel STR = AbilityModel.getForAbility("STR");
    private AbilityModel DEX = AbilityModel.getForAbility("DEX");
    private AbilityModel CON = AbilityModel.getForAbility("CON");
    private AbilityModel INT = AbilityModel.getForAbility("INT");
    private AbilityModel WIS = AbilityModel.getForAbility("WIS");
    private AbilityModel CHA = AbilityModel.getForAbility("CHA");

    private int speed = 30;
    private int AC = 10;
    private int HP_max = 10;
    private int HP_current = 10;

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

    public void removeWeapon(WeaponModel weaponModel) {
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

        STR = in.readParcelable(AbilityModel.class.getClassLoader());
        DEX = in.readParcelable(AbilityModel.class.getClassLoader());
        CON = in.readParcelable(AbilityModel.class.getClassLoader());
        INT = in.readParcelable(AbilityModel.class.getClassLoader());
        WIS = in.readParcelable(AbilityModel.class.getClassLoader());
        CHA = in.readParcelable(AbilityModel.class.getClassLoader());

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

        dest.writeParcelable(STR, 0);
        dest.writeParcelable(DEX, 0);
        dest.writeParcelable(CON, 0);
        dest.writeParcelable(INT, 0);
        dest.writeParcelable(WIS, 0);
        dest.writeParcelable(CHA, 0);

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
        CharacterModel other = (CharacterModel) o;

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

    public AbilityModel getSTR() {
        return STR;
    }

    public AbilityModel getDEX() {
        return DEX;
    }

    public AbilityModel getCON() {
        return CON;
    }

    public AbilityModel getINT() {
        return INT;
    }

    public AbilityModel getWIS() {
        return WIS;
    }

    public AbilityModel getCHA() {
        return CHA;
    }

    public CharacterModel setSTR(int str_score) {
        STR.setScore(str_score);
        return this;
    }

    public CharacterModel setDEX(int DEX_score) {
        DEX.setScore(DEX_score);
        return this;
    }

    public CharacterModel setCON(int CON_score) {
        CON.setScore(CON_score);
        return this;
    }

    public CharacterModel setINT(int INT_score) {
        INT.setScore(INT_score);
        return this;
    }

    public CharacterModel setWIS(int WIS_score) {
        WIS.setScore(WIS_score);
        return this;
    }

    public CharacterModel setCHA(int CHA_score) {
        CHA.setScore(CHA_score);
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

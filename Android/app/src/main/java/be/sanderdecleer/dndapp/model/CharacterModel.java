package be.sanderdecleer.dndapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SD on 20/05/2016.
 */
public class CharacterModel {

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

}

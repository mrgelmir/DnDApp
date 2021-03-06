package be.sanderdecleer.dndapp.utils;

import be.sanderdecleer.dndapp.model.character.CharacterModel;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.model.character.FeatureModel;
import be.sanderdecleer.dndapp.model.character.WeaponModel;

/**
 * This class provides test characters for populating the app easily
 * Created by SD on 5/04/2017.
 */

public class TestCharacterProvider {

    // Not really needed -> (re-)move?
    public static CharacterModel createTestMonk() {

        // Test character 1
        CharacterModel character = new CharacterModel("Max the Mad Monk")
                .setAbilityScores(14, 16, 14, 11, 14, 13)
                .setHP_current(8)
                .setHP_max(13)
                .setAC(15)
                .setSpeed(40)
                .setHitDice_max("2d8");

        character
                .addFeature(new FeatureModel("Martial Arts", "Do bad-ass stuff"))
                .addFeature(new FeatureModel("Wanderer", "Terrain stuff and such"))
                .addFeature(new FeatureModel("Unarmored defense", "can't touch this"));

        WeaponModel shortSword = WeaponModel.getEmpty();
        shortSword.nickname = "";
        shortSword.weaponType = "Short sword";
        shortSword.weaponDamage = "1d6 + 3 slashing";
        shortSword.weaponToHit = "+5";
        shortSword.weaponFeatures = new String[]{"finesse", "light"};

        WeaponModel dagger = WeaponModel.getEmpty();
        dagger.weaponType = "Dagger";
        dagger.weaponDamage = "1d4 + 3 piercing";
        dagger.weaponToHit = "+5";
        dagger.weaponFeatures = new String []{"finesse", "light", "range(30/60ft)"};
        character.addWeapon(dagger);
        character.addWeapon(shortSword);

        ExpendableModel kiExpendable = new ExpendableModel("KI points", 2, 1);
        ExpendableModel superiorityExpendable = new ExpendableModel("Superiority Dice", 10, 5);
        character.addExpendable(kiExpendable);
        character.addExpendable(superiorityExpendable);

        return character;
    }

    public static CharacterModel createTestSpellcaster() {

        // Test character 2
        CharacterModel character = new CharacterModel("Mike the Magic Mage")
                .setAbilityScores(10, 12, 8, 20, 17, 16)
                .setHP_current(8)
                .setHP_max(8)
                .setAC(13)
                .setSpeed(30)
                .setHitDice_max("2d6");

        character.addFeature(new FeatureModel("Spell 1", "This is an even more bad-ass skill description"));
        character.addFeature(new FeatureModel("Magic bolt", "This is an even more bad-ass,\nmulti-line,\nskill description"));

        WeaponModel dagger2 = WeaponModel.getEmpty();
        dagger2.nickname = "lil' edge";
        dagger2.weaponType = "Dagger";
        dagger2.weaponDamage = "1d4 + 3 piercing";
        dagger2.weaponToHit = "+5";
        dagger2.weaponFeatures = new String []{"finesse", "light", "range(30/60ft)"};
        character.addWeapon(dagger2);

        return character;


    }


}

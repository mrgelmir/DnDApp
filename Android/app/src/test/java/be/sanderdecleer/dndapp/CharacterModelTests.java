package be.sanderdecleer.dndapp;

import android.os.Parcel;

import org.junit.Test;

import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.model.ExpendableModel;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.model.WeaponModel;

import static org.junit.Assert.*;

/**
 * Created by SD on 2/06/2016.
 */
public class CharacterModelTests {

    // TODO: 2/06/2016 Figure out instrumentation testing (since Parcel isn't mocked)
//    @Test
//    public void TestParcels() {
//
//        // Test Parcelable
//
//        Parcel p1 = Parcel.obtain();
//        Parcel p2 = Parcel.obtain();
//        getTestCharacter1().writeToParcel(p1, 0);
//        getTestCharacter2().writeToParcel(p2, 0);
//
//        p1.setDataPosition(0);
//        p2.setDataPosition(0);
//
//        CharacterModel pc1 = CharacterModel.CREATOR.createFromParcel(p1);
//        CharacterModel pc2 = CharacterModel.CREATOR.createFromParcel(p2);
//        assertEquals(getTestCharacter1(), pc1);
//        assertEquals(getTestCharacter2(), pc2);
//    }

    @Test
    public void TestPlaceholder() {
        assertEquals(1, 1);
    }

    private CharacterModel getTestCharacter1() {
        // Test character 1
        CharacterModel character1 = new CharacterModel("Guy Stormcrow");
        character1.setAbilityScores(14, 16, 14, 11, 14, 13);
        character1.HP_current = 8;
        character1.HP_max = 13;
        character1.hitDice_max = "2d8";
        character1.AC = 15;
        character1.speed = 40;
        character1.addAbility(new FeatureModel("Martial Arts", "Do bad-ass stuff"));
        character1.addAbility(new FeatureModel("Wanderer", "Terrain stuff and such"));
        character1.addAbility(new FeatureModel("Unarmored defense", "can't touch this"));
        WeaponModel shortSword = new WeaponModel();
        shortSword.nickname = "";
        shortSword.weaponType = "Short sword";
        shortSword.weaponDamage = "1d6 + 3 slashing";
        shortSword.weaponToHit = "+5";
        shortSword.weaponFeatures = "finesse, light";
        WeaponModel dagger = new WeaponModel();
        dagger.weaponType = "Dagger";
        dagger.weaponDamage = "1d4 + 3 piercing";
        dagger.weaponToHit = "+5";
        dagger.weaponFeatures = "finesse, light, range(30/60ft)";
        character1.addWeapon(dagger);
        character1.addWeapon(shortSword);
        ExpendableModel kiExpendable = new ExpendableModel("KI points", 2, 1);
        ExpendableModel superiorityExpendable = new ExpendableModel("Superiority Dice", 10, 5);
        character1.addExpendable(kiExpendable);
        character1.addExpendable(superiorityExpendable);

        return character1;
    }

    private CharacterModel getTestCharacter2() {
        CharacterModel character2 = new CharacterModel("Derek the Dude");
        character2.setAbilityScores(10, 12, 8, 20, 17, 16);
        character2.HP_current = 8;
        character2.HP_max = 8;
        character2.hitDice_max = "2d6";
        character2.AC = 13;
        character2.speed = 30;
        character2.addAbility(new FeatureModel("Spell 1", "This is an even more bad-ass skill description"));
        character2.addAbility(new FeatureModel("Magic bolt", "This is an even more bad-ass,\nmulti-line,\nskill description"));
        WeaponModel dagger2 = new WeaponModel();
        dagger2.nickname = "lil' edge";
        dagger2.weaponType = "Dagger";
        dagger2.weaponDamage = "1d4 + 3 piercing";
        dagger2.weaponToHit = "+5";
        dagger2.weaponFeatures = "finesse, light, range(30/60ft)";
        character2.addWeapon(dagger2);

        return character2;
    }

}

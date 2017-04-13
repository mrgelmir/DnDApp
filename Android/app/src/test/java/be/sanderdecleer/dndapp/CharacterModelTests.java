package be.sanderdecleer.dndapp;

import org.junit.Test;

import be.sanderdecleer.dndapp.model.character.CharacterModel;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.model.character.FeatureModel;
import be.sanderdecleer.dndapp.model.character.WeaponModel;

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



}

package be.sanderdecleer.dndapp.model.character;

import android.os.Parcelable;

import be.sanderdecleer.dndapp.utils.ViewControllerProvider;
import be.sanderdecleer.dndapp.controllers.ItemViewController;

/**
 * Created by SD on 22/11/2016.
 * Base class of which all features inherit from
 */

public abstract class BaseItem implements Parcelable {

    public enum Type {
        Weapon(0),
        Feature(1),
        Expendable(2),
        // Spell(3),
        Item(4),
        AbilityScore(10),
        ArmorClass(11),
        Speed(12);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public abstract Type getType();

    public ItemViewController getViewController(){
        return ViewControllerProvider.getViewController(this);
    }

}

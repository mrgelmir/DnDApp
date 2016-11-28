package be.sanderdecleer.dndapp.model;

/**
 * Created by SD on 22/11/2016.
 * Base class of which all features inherit from
 */

public abstract class BaseItem {

    public enum Type {
        Weapon(0), Feature(1), Expendable(2), Item(4); //,Spell(3);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public abstract Type getType();
}

package be.sanderdecleer.dndapp.utils;

import be.sanderdecleer.dndapp.model.CharacterModel;

/**
 * Created by SD on 17/06/2016.
 */
public interface CharacterProvider {

    CharacterModel getCharacter();

    void setCharacter(CharacterModel character);

    void addListener(Listener listener);

    void removeListener(Listener listener);

    interface Listener {

        /**
         * Notify that the character has changed
         */
        void onCharacterChanged();
    }
}


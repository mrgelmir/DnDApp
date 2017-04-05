package be.sanderdecleer.dndapp.utils;

import be.sanderdecleer.dndapp.model.character.CharacterModel;

/**
 * Created by SD on 17/06/2016.
 */
public interface CharacterProvider {

    boolean hasCharacter();

    /**
     *
     * @return The current character if available
     * @throws NullPointerException if no character is available
     */
    CharacterModel getCharacter() throws NullPointerException;

    void setCharacter(CharacterModel character);

    void addListener(Listener listener);

    void removeListener(Listener listener);

    void characterChanged();

    interface Listener {

        /**
         * Notify that the character has changed
         */
        void onCharacterChanged();
    }
}


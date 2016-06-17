package be.sanderdecleer.dndapp;

import be.sanderdecleer.dndapp.model.CharacterModel;

/**
 * Created by SD on 17/06/2016.
 */
public interface CharacterProvider {
    CharacterModel getCharacter();

    void setCharacter(CharacterModel character);

    void CharacterUpdated();
}
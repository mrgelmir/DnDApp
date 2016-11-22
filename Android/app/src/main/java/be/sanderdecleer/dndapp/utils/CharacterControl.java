package be.sanderdecleer.dndapp.utils;

import java.util.HashSet;

import be.sanderdecleer.dndapp.model.CharacterModel;

/**
 * Created by SD on 21/11/2016.
 * Manages the current selected character
 */

public class CharacterControl implements CharacterProvider {

    private CharacterModel currentCharacter = null;
    private HashSet<Listener> listeners;

    private static CharacterControl instance = null;

    public static CharacterControl getInstance() {
        if (instance == null) {
            instance = new CharacterControl();
        }
        return instance;
    }

    public static CharacterModel getCurrentCharacter() {
        return getInstance().currentCharacter;
    }

    public static void setCurrentCharacter(CharacterModel character) {
        getInstance().setCharacter(character);
    }

    private CharacterControl() {
        // Private constructor for singleton
        instance = this;
        listeners = new HashSet<>();
    }

    public void characterChanged() {
        for (Listener l:listeners){
            l.onCharacterChanged();
        }
    }

    @Override
    public CharacterModel getCharacter() {
        return currentCharacter;
    }

    @Override
    public void setCharacter(CharacterModel character) {
        currentCharacter = character;
        characterChanged();
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }


}
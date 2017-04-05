package be.sanderdecleer.dndapp.model;

import java.io.File;

/**
 * Created by SD on 5/04/2017.
 * This holds information about the character, before fully loading it
 */

public class CharacterDescription {

    private String characterName;
    private File characterFile;

    public CharacterDescription(File characterFile, String characterName) {
        this.characterName = characterName;
        this.characterFile = characterFile;
    }

    public String getCharacterName() {
        return characterName;
    }

    public File getCharacterFile() {
        return characterFile;
    }
}

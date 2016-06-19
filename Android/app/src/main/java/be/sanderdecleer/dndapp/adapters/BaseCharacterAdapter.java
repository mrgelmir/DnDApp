package be.sanderdecleer.dndapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import be.sanderdecleer.dndapp.model.CharacterModel;

/**
 * Base Adapter for character-based collections
 * Keeps a reference to a given character and updates
 */
public class BaseCharacterAdapter<T> extends ArrayAdapter<T> {

    protected int layoutResourceId;
    protected CharacterModel character = null;

    public BaseCharacterAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        this.layoutResourceId = layoutResourceId;
    }

    public void setCharacter(CharacterModel character) {
        this.character = character;

        // TODO: 19/06/2016 Use a CharacterProvider instead?
        
        notifyDataSetChanged();
    }
}

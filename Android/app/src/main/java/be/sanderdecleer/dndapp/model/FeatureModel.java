package be.sanderdecleer.dndapp.model;

import android.content.Context;

import be.sanderdecleer.dndapp.R;

/**
 * Created by SD on 20/05/2016.
 * Holds all data of a feature.
 */
public class FeatureModel extends BaseItem {

    public String title;
    public String description;

    // TODO add some function if needed

    public FeatureModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static FeatureModel getEmpty(Context context) {
        return new FeatureModel(context.getString(R.string.feature_default_title),
                context.getString(R.string.feature_default_description));
    }

    @Override
    public Type getType() {
        return Type.Feature;
    }
}

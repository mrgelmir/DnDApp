package be.sanderdecleer.dndapp.model;

import android.content.Context;

import be.sanderdecleer.dndapp.R;

/**
 * Created by SD on 20/05/2016.
 */
public class FeatureModel {

    public String title;
    public String description;

    public FeatureModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static FeatureModel getEmpty(Context context) {
        return new FeatureModel(context.getString(R.string.feature_default_title),
                context.getString(R.string.feature_default_description));
    }
}

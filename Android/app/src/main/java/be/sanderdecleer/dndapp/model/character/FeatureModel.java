package be.sanderdecleer.dndapp.model.character;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import be.sanderdecleer.dndapp.R;

/**
 * Created by SD on 20/05/2016.
 * Holds all data of a feature.
 */
public class FeatureModel extends BaseItem {

    public String title;
    public String shortDescription;
    public String description;

    // TODO maybe remove constructors and use builder pattern or static creation methods
    public FeatureModel(String title, String description) {
        this(title, description, null);
    }

    public FeatureModel(String title, String description, String shortDescription) {

        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
    }

    private FeatureModel(Parcel in) {
        title = in.readString();
        shortDescription = in.readString();
        description = in.readString();
    }

    public static FeatureModel getEmpty(Context context) {
        return new FeatureModel(context.getString(R.string.feature_default_title),
                context.getString(R.string.feature_default_description));
    }

    @Override
    public Type getType() {
        return Type.Feature;
    }

    public String getShortDescription() {
        if (shortDescription == null || shortDescription.isEmpty()) {
            return description;
        } else {
            return shortDescription;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(shortDescription);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<FeatureModel> CREATOR
            = new Creator<FeatureModel>() {
        @Override
        public FeatureModel createFromParcel(Parcel source) {
            return new FeatureModel(source);
        }

        @Override
        public FeatureModel[] newArray(int size) {
            return new FeatureModel[size];
        }
    };
}

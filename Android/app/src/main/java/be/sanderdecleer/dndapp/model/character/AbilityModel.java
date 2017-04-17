package be.sanderdecleer.dndapp.model.character;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SD on 13/04/2017.
 * Holds data for an Ability
 */

public class AbilityModel extends BaseItem {

    private String name;
    private int score;

    public static AbilityModel getEmpty() {
        return getForAbility("XXX");
    }

    public static AbilityModel getForAbility(String ability){
        AbilityModel abilityModel = new AbilityModel();
        abilityModel.name = ability;
        abilityModel.score = 10;
        return abilityModel;
    }

    private AbilityModel() {

    }

    private AbilityModel(Parcel in) {
        name = in.readString();
        score = in.readInt();
    }

    @Override
    public Type getType() {
        return Type.AbilityScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
    }

    public static final Parcelable.Creator<AbilityModel> CREATOR
            = new Creator<AbilityModel>() {
        @Override
        public AbilityModel createFromParcel(Parcel source) {
            return new AbilityModel(source);
        }

        @Override
        public AbilityModel[] newArray(int size) {
            return new AbilityModel[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getModifier() {
        return (int) Math.floor((score - 10) / 2.0);
    }
}

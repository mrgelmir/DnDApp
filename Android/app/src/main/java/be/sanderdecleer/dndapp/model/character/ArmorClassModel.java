package be.sanderdecleer.dndapp.model.character;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SD on 17/04/2017.
 * Holds data for the Armor class
 */

public class ArmorClassModel extends BaseItem {

    private int baseAC;

    public static ArmorClassModel getEmpty() {
        ArmorClassModel armorClassModel = new ArmorClassModel();
        armorClassModel.baseAC = 10;
        return armorClassModel;
    }

    private ArmorClassModel() {

    }

    private ArmorClassModel(Parcel in) {
        baseAC = in.readInt();
    }

    @Override
    public Type getType() {
        return Type.ArmorClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(baseAC);
    }

    public static final Parcelable.Creator<ArmorClassModel> CREATOR
            = new Creator<ArmorClassModel>() {
        @Override
        public ArmorClassModel createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public ArmorClassModel[] newArray(int size) {
            return new ArmorClassModel[size];
        }
    };

    public int getBaseAC() {
        return baseAC;
    }

    public void setBaseAC(int baseAC) {
        this.baseAC = baseAC;
    }
}

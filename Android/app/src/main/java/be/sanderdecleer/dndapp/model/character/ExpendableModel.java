package be.sanderdecleer.dndapp.model.character;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SD on 29/05/2016.
 * Holds all data of an Expendable
 */
public class ExpendableModel extends BaseItem {

    public String title;
    public int expendables_max;
    public int expendables_current;
    /**
     * The format in which the expendable is presented
     * eg. "%1$d/%2$d" (default)
     */
    public String format;

    public ExpendableModel(String title, int maxExpendables, int currentExpendables) {
        this(title, maxExpendables, currentExpendables, "%1$d/%2$d");
    }

    public ExpendableModel(String title, int maxExpendables, int currentExpendables, String format) {
        this.title = title;
        this.expendables_max = maxExpendables;
        this.expendables_current = currentExpendables;
        this.format = format;
    }

    private ExpendableModel(Parcel in) {
        title = in.readString();
        expendables_max = in.readInt();
        expendables_current = in.readInt();
        format = in.readString();
    }

    public void decrease() {
        if (expendables_current > 0)
            --expendables_current;
    }

    public void increase() {
        if (expendables_current < expendables_max)
            ++expendables_current;
    }

    public void reset() {
        expendables_current = expendables_max;
    }

    public static ExpendableModel getEmpty() {
        return new ExpendableModel("temp", 1, 1);
    }

    @Override
    public Type getType() {
        return Type.Expendable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(expendables_max);
        dest.writeInt(expendables_current);
        dest.writeString(format);
    }

    public static final Parcelable.Creator<ExpendableModel> CREATOR
            = new Creator<ExpendableModel>() {
        @Override
        public ExpendableModel createFromParcel(Parcel source) {
            return new ExpendableModel(source);
        }

        @Override
        public ExpendableModel[] newArray(int size) {
            return new ExpendableModel[size];
        }
    };
}

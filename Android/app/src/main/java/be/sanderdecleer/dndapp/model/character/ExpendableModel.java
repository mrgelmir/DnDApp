package be.sanderdecleer.dndapp.model.character;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SD on 29/05/2016.
 */
public class ExpendableModel extends BaseItem {

    public String title;
    public int expendables_max;
    public int expendables_current;

    public ExpendableModel(String title, int maxExpendables, int currentExpendables) {
        this.title = title;
        this.expendables_max = maxExpendables;
        this.expendables_current = currentExpendables;
    }

    private ExpendableModel(Parcel in) {
        title = in.readString();
        expendables_max = in.readInt();
        expendables_current = in.readInt();
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

    public static ExpendableModel getEmpty(Context context) {
        return new ExpendableModel("temp", 0, 0);
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

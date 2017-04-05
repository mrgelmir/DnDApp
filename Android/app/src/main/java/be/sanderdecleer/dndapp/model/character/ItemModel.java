package be.sanderdecleer.dndapp.model.character;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SD on 22/11/2016.
 */

public class ItemModel extends BaseItem {

    public String itemName;
    public String itemDescription;

    public static ItemModel getEmpty() {
        ItemModel newItem = new ItemModel();
        newItem.itemName = "Item name";
        newItem.itemDescription = "Item description";

        return newItem;
    }

    private ItemModel() {
        // Empty base constructor
    }

    private ItemModel(Parcel in) {
        itemName = in.readString();
        itemDescription = in.readString();
    }

    @Override
    public Type getType() {
        return Type.Item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemDescription);
    }

    public static final Parcelable.Creator<ItemModel> CREATOR
            = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel source) {
            return new ItemModel(source);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };
}

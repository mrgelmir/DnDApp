package be.sanderdecleer.dndapp.model;

/**
 * Created by SD on 22/11/2016.
 */

public class ItemModel extends BaseItem {

    public String ItemName;
    public String ItemDescription;

    public static ItemModel getEmpty() {
        ItemModel newItem = new ItemModel();
        newItem.ItemName = "Item name";
        newItem.ItemDescription = "Item description";

        return newItem;
    }

    @Override
    public Type getType() {
        return Type.Item;
    }
}

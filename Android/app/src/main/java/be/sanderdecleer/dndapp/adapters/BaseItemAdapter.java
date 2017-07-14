package be.sanderdecleer.dndapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.utils.LayoutFactory;
import be.sanderdecleer.dndapp.views.ItemViewType;

/**
 * Created by SD on 22/11/2016.
 * Adapter for showing all different items (Features, weapons, spells ...)
 */
public class BaseItemAdapter extends ArrayAdapter<BaseItem>
        implements View.OnClickListener {
    public BaseItemAdapter(Context context, int resource, List<BaseItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public int getViewTypeCount() {
        // There are as many types of view as there are types
        return BaseItem.Type.values().length;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        BaseItem item = getItem(position);
        return item == null ? -1 : item.getType().getValue();
    }

    // Get a View that displays the data at the specified position in the data set.
    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final BaseItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate new view
        ViewGroup itemView = (ViewGroup) convertView;
        if (itemView == null) {
            itemView = (ViewGroup) LayoutFactory.createView(
                    LayoutInflater.from(getContext()), parent,
                    item, ItemViewType.ITEM);
        }

        // Apply the new data to the view
        if (item != null)
            item.getViewController().setupItemView(itemView);

        // Return the completed view to render on screen
        return itemView;
    }

    @Override
    public void onClick(View v) {
        Log.i("BaseItemAdapter", v.getId() + "tapped");
    }


}

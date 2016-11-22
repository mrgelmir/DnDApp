package be.sanderdecleer.dndapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.BaseItem;

/**
 * Created by SD on 22/11/2016.
 */

public class BaseItemAdapter extends ArrayAdapter<BaseItem> {
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
        return getItem(position).getType().getValue();
    }

    // Get a View that displays the data at the specified position in the data set.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BaseItem feature = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // Get the data item type for this position
            int type = getItemViewType(position);
            // Inflate XML layout based on the type
            convertView = getInflatedLayoutForType(type);
        }
//        // Lookup view for data population
//        TextView tvLabel = (TextView) convertView.findViewById(R.id.tvLabel);
//        if (tvLabel != null) {
//            // Populate the data into the template view using the data object
//            tvLabel.setText(color.label);
//        }
        // Return the completed view to render on screen
        return convertView;
    }

    private View getInflatedLayoutForType(int type) {

        // Temporarily do this here, should be abstracted later
        int resourceID;

        if (type == BaseItem.Type.Weapon.getValue()) {
            resourceID = R.layout.item_weapon_view;
        } else if (type == BaseItem.Type.Feature.getValue()) {
            resourceID = R.layout.item_feature_view;
        } else if (type == BaseItem.Type.Expendable.getValue()) {
            resourceID = R.layout.item_expendable_view;
        } else {
            return null;
        }

        return LayoutInflater.from(getContext()).inflate(resourceID, null);
    }

    // TODO: 22/11/2016 ViewHolders for each type
}

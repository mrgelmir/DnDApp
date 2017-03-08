package be.sanderdecleer.dndapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import be.sanderdecleer.dndapp.model.BaseItem;
import be.sanderdecleer.dndapp.views.BaseItemView;
import be.sanderdecleer.dndapp.views.ExpendableView;
import be.sanderdecleer.dndapp.views.FeatureView;
import be.sanderdecleer.dndapp.views.WeaponView;

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
        return getItem(position).getType().getValue();
    }

    // Get a View that displays the data at the specified position in the data set.
    @Override @NonNull
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BaseItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        BaseItemView itemView = (BaseItemView) convertView;
        if (itemView == null) {
            itemView = getInflatedLayoutForItem(item);
        }

        // set the data on the view now
        itemView.setItem(item);
        itemView.setListener(new BaseItemView.DataUpdateListener() {
            @Override
            public void DataUpdated(BaseItem data) {
                // TODO listen to data updates and propagate
            }
        });

        // Return the completed view to render on screen
        return itemView;
    }

    private BaseItemView getInflatedLayoutForItem(BaseItem item) {

        BaseItem.Type type = item.getType();
        BaseItemView view;

        // TODO: 29/11/2016 Make this more flexible
        switch (type) {
            case Expendable:
                view = new ExpendableView(getContext());
                break;
            case Feature:
                view = new FeatureView(getContext());
                break;
            case Weapon:
                view = new WeaponView(getContext());
                break;
            default:
            case Item:
                return null;
        }

        view.setMinimumHeight(150);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.i("BaseItemAdapter", v.getId() + "tapped");
    }


}

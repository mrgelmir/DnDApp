package be.sanderdecleer.dndapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.BaseItem;

/**
 * Created by SD on 22/11/2016.
 * Holds te view element of a feature
 */

public class FeatureView extends BaseItemView {
    public FeatureView(Context context) {
        super(context);
    }

    public FeatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeatureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setItem(BaseItem item) {
        super.setItem(item);

        // Check if item is of desired type

        // Do actual setup
    }

    @Override
    public void setupChildren() {
        super.setupChildren();
    }

    @Override
    public int getResourceId() {
        return R.layout.item_feature_view;
    }
}

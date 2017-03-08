package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.BaseItem;
import be.sanderdecleer.dndapp.model.FeatureModel;

/**
 * Created by SD on 22/11/2016.
 * Holds te view element of a {@link FeatureModel}
 */

public class FeatureView extends BaseItemView<FeatureModel> {

    private TextView titleLabel;
    private TextView descriptionLabel;

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

        // Do actual setup
        titleLabel.setText(data.title);
        descriptionLabel.setText(data.getShortDescription());
    }

    @Override
    public void setupItemView() {
        super.setupItemView();

        titleLabel = (TextView) findViewById(R.id.ability_title);
        descriptionLabel = (TextView) findViewById(R.id.ability_description);
    }

    @Override
    public void setupInfoView(View view) {
        super.setupInfoView(view);
        TextView descriptionLabel = (TextView) view.findViewById(R.id.ability_description);
        descriptionLabel.setText(data.description);
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String getTitle() {
        return data.title;
    }

    @Override
    public int getResourceId() {
        return R.layout.item_feature_view;
    }

    @Override
    public int getDialogResourceId() {
        return R.layout.info_feature_view;
    }
}

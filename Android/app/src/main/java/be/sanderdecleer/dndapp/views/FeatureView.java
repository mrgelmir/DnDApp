package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.FeatureModel;

/**
 * Created by SD on 22/11/2016.
 * Controls the view element of a {@link FeatureModel} via the {@link BaseItemView}
 */

public class FeatureView implements ItemViewController {

    private FeatureModel data;

    @Override
    public void setDataChangedListener(DataChangedListener listener) {

    }

    @Override
    public void setItem(BaseItem item) {
        data = (FeatureModel) item;
    }

    @Override
    public void setupItemView(View itemView) {

        TextView titleLabel = (TextView) itemView.findViewById(R.id.ability_title);
        TextView descriptionLabel = (TextView) itemView.findViewById(R.id.ability_description);

        // Do actual setup
        titleLabel.setText(data.title);
        descriptionLabel.setText(data.getShortDescription());
    }

    @Override
    public void setupInfoView(View infoView) {

        TextView descriptionLabel = (TextView) infoView.findViewById(R.id.ability_description);
        descriptionLabel.setText(data.description);
    }

    @Override
    public void setupEditView(View editView) {

    }

    @Override
    public void resolveEditView(View editView) {

    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_feature_view;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.info_feature_view;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.edit_feature_view;
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String getTitle() {
        return data.title;
    }

}

package be.sanderdecleer.dndapp.views;

import android.view.View;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.AbilityModel;
import be.sanderdecleer.dndapp.model.character.BaseItem;

/**
 * Created by SD on 13/04/2017.
 */

public class AbilityView implements ItemViewController {

    AbilityModel data;

    @Override
    public void setItem(BaseItem item) {
        data = (AbilityModel) item;
    }

    @Override
    public void setupItemView(View itemView) {
        final TextView attributeName = (TextView) itemView.findViewById(R.id.attribute_name);
        final TextView attributeScore = (TextView) itemView.findViewById(R.id.attribute_value);

        attributeName.setText(data.getName());
        attributeScore.setText(String.format(
                itemView.getContext().getText(R.string.ability_score_value).toString(),
                data.getScore(),
                data.getModifier() >= 0 ? "+" : "-",
                Math.abs(data.getModifier())));

    }

    @Override
    public void setupInfoView(View infoView) {

    }

    @Override
    public void setupEditView(View editView) {

    }

    @Override
    public void resolveEditView(View editView) {

    }

    @Override
    public void remove() {

    }

    @Override
    public boolean hasTitle() {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getItemResourceId() {
        return R.layout.p_ability_view;
    }

    @Override
    public int getInfoResourceId() {
        return 0;
    }

    @Override
    public int getEditResourceId() {
        return 0;
    }
}

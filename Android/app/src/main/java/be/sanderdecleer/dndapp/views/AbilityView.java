package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.AbilityModel;
import be.sanderdecleer.dndapp.model.character.BaseItem;

/**
 * Created by SD on 13/04/2017.
 */

public class AbilityView extends LinearLayout implements ItemViewController {

    AbilityModel data;

    public AbilityView(Context context) {
        super(context);
    }

    public AbilityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbilityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbilityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setup() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


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

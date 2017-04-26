package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.AbilityModel;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 13/04/2017.
 * Controls the view elements of a {@link AbilityModel}
 */

public class AbilityView extends LinearLayout implements ItemViewController {

    AbilityModel data;

    public AbilityView(Context context) {
        super(context);
        setup();
    }

    public AbilityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public AbilityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public AbilityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    private void setup() {

        final ItemViewController thisInstance = this;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDialogFragment.showItemViewDialog(getContext(), thisInstance,
                        ItemDialogFragment.VIEW_TYPE_INFO);
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ItemDialogFragment.showItemViewDialog(getContext(), thisInstance,
                        ItemDialogFragment.VIEW_TYPE_EDIT);
                return true;
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
        final TextView attributeScore = (TextView) itemView.findViewById(R.id.edit_value);

        attributeName.setText(data.getName());
        attributeScore.setText(String.format(
                itemView.getContext().getText(R.string.ability_score_value).toString(),
                data.getScore(),
                data.getModifier() >= 0 ? "+" : "-",
                Math.abs(data.getModifier())));

    }

    @Override
    public void setupInfoView(View infoView) {
        setupItemView(infoView);
    }

    @Override
    public void setupEditView(View editView) {
        final NumberPicker statPicker = (NumberPicker) editView.findViewById(R.id.edit_value);

        statPicker.setMinValue(0);
        statPicker.setMaxValue(24);
        statPicker.setValue(data.getScore());
    }

    @Override
    public void resolveEditView(View editView) {
        final NumberPicker statPicker = (NumberPicker) editView.findViewById(R.id.edit_value);

        data.setScore(statPicker.getValue());
        CharacterControl.tryCharacterChanged();
    }

    @Override
    public boolean canRemove() {
        return false;
    }

    @Override
    public void remove() {
        // lol nope
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String getTitle() {
        return data.getName();
    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_ability_view;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.item_ability_view;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.edit_single_number;
    }
}

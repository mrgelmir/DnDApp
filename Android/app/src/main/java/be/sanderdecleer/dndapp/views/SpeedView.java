package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 26/04/2017.
 */

public class SpeedView extends RelativeLayout implements ItemViewController {

    public SpeedView(Context context) {
        super(context);
        setup();
    }

    public SpeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    private void setup() {
        final SpeedView thisInstance = this;

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
        // No local data used, pull this from current character
    }

    @Override
    public void setupItemView(View itemView) {
        TextView label = (TextView) itemView.findViewById(R.id.speed_label);
        label.setText(getFormattedSpeed());
    }

    @Override
    public void setupInfoView(View infoView) {
        TextView label = (TextView) infoView.findViewById(R.id.info_label);
        label.setText(getFormattedSpeed());

    }

    // TODO: 27/04/2017 fix this
    private static final String[] speedValues = {"0", "5", "10", "15", "20", "25", "30", "35",
            "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100", "105",
            "110", "115", "120"};

    @Override
    public void setupEditView(View editView) {
        final NumberPicker picker = (NumberPicker) editView.findViewById(R.id.edit_value);

        picker.setMaxValue(speedValues.length - 1);
        picker.setDisplayedValues(speedValues);

        // match value to the speedValues
        picker.setValue(CharacterControl.getCurrentCharacter().getSpeed() / 5);
    }

    @Override
    public void resolveEditView(View editView) {
        final NumberPicker picker = (NumberPicker) editView.findViewById(R.id.edit_value);

        CharacterControl.getCurrentCharacter().setSpeed(
                Integer.parseInt(speedValues[picker.getValue()]));
        CharacterControl.tryCharacterChanged();
    }

    @Override
    public void remove() {
        // nope
        // TODO: 26/04/2017 should this be part of another interface?
    }

    @Override
    public boolean canRemove() {
        return false;
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.character_speed_title);
    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_speed;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.info_single_label;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.edit_single_number;
    }

    private String getFormattedSpeed() {
        return String.format(getResources().getString(R.string.character_speed_format),
                CharacterControl.getCurrentCharacter().getSpeed());
    }
}

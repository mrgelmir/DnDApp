package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.controllers.ItemViewController;
import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 27/04/2017.
 */

public class HitPointsView extends RelativeLayout implements ItemViewController {

    public HitPointsView(Context context) {
        super(context);
        setup();
    }

    public HitPointsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public HitPointsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public HitPointsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    private void setup() {
        final HitPointsView thisInstance = this;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDialogFragment.createItemViewDialog(getContext(), thisInstance,
                        ItemViewType.INFO);
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ItemDialogFragment.createItemViewDialog(getContext(), thisInstance,
                        ItemViewType.EDIT);
                return true;
            }
        });
    }

    @Override
    public void setItem(BaseItem item) {
        // Not needed here
    }

    @Override
    public void setupItemView(View itemView) {
        TextView label = (TextView) itemView.findViewById(R.id.hit_points_label);
        label.setText(getFormattedHitPoints());
    }

    @Override
    public void setupInfoView(View infoView) {
        final TextView label = (TextView) infoView.findViewById(R.id.info_label);
        label.setText(getFormattedHitPoints());
    }

    @Override
    public void setupEditView(View editView) {
        final NumberPicker current = (NumberPicker) editView.findViewById(R.id.hit_points_current);
        final NumberPicker maximum = (NumberPicker) editView.findViewById(R.id.hit_points_max);

        int hp_current = CharacterControl.getCurrentCharacter().getHP_current();
        int hp_max = CharacterControl.getCurrentCharacter().getHP_max();

        current.setMaxValue(hp_max);
        current.setMinValue(0);
        current.setValue(hp_current);

        maximum.setMinValue(6);
        maximum.setMaxValue(300); // Totally arbitrary number for now
        maximum.setValue(hp_max);

        maximum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Change to new maximum value
                current.setMaxValue(newVal);
            }
        });
    }

    @Override
    public void resolveEditView(View editView) {
        final NumberPicker current = (NumberPicker) editView.findViewById(R.id.hit_points_current);
        final NumberPicker maximum = (NumberPicker) editView.findViewById(R.id.hit_points_max);

        CharacterControl.getCurrentCharacter().setHP_current(current.getValue());
        CharacterControl.getCurrentCharacter().setHP_max(maximum.getValue());

        CharacterControl.tryCharacterChanged();
    }

    @Override
    public void remove() {
        // nope
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
        return getResources().getString(R.string.character_HP_title);
    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_hit_points;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.info_single_label;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.edit_hit_points;
    }

    private String getFormattedHitPoints() {
        return String.format(getResources().getString(R.string.character_HP_format),
                CharacterControl.getCurrentCharacter().getHP_current(),
                CharacterControl.getCurrentCharacter().getHP_max());
    }
}

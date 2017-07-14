package be.sanderdecleer.dndapp.controllers;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 22/11/2016.
 * ViewController for the {@link ExpendableModel} class
 */

public class ExpendableViewController implements ItemViewController {

    private ExpendableModel data;

    @Override
    public void setupItemView(View itemView) {

        TextView titleView = (TextView) itemView.findViewById(R.id.expendable_title);
        TextView valueView = (TextView) itemView.findViewById(R.id.expendable_value);
        ImageButton increaseButton = (ImageButton) itemView.findViewById(R.id.expendable_btn_increase);
        ImageButton decreaseButton = (ImageButton) itemView.findViewById(R.id.expendable_btn_decrease);

        // Do actual setup
        titleView.setText(data.title);

        valueView.setText(String.format(data.format, data.expendables_current, data.expendables_max));

        // Hook up buttons
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.increase();
                if (CharacterControl.hasCurrentCharacter())
                    CharacterControl.getInstance().characterChanged();
            }
        });
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.decrease();
                CharacterControl.tryCharacterChanged();
            }
        });
    }

    @Override
    public void setupInfoView(View infoView) {

        final NumberPicker currentAmount = (NumberPicker) infoView.findViewById(R.id.expendable_current);
        final TextView maxAmount = (TextView) infoView.findViewById(R.id.expendable_max);
        final Button resetButton = (Button) infoView.findViewById(R.id.expendable_reset);

        currentAmount.setMinValue(0);
        currentAmount.setMaxValue(data.expendables_max);
        currentAmount.setValue(data.expendables_current);

        maxAmount.setText(String.format (Locale.getDefault() ,"%1$d", data.expendables_max));

        currentAmount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                data.expendables_current = newVal;
                CharacterControl.tryCharacterChanged();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.reset();
                currentAmount.setValue(data.expendables_current);
                CharacterControl.tryCharacterChanged();
            }
        });
    }

    @Override
    public void setupEditView(View editView) {
        final EditText titleLabel = (EditText) editView.findViewById(R.id.expendable_title);
        final NumberPicker currentAmount = (NumberPicker) editView.findViewById(R.id.expendable_current);
        final NumberPicker maxAmount = (NumberPicker) editView.findViewById(R.id.expendable_max);

        titleLabel.setText(data.title);

        currentAmount.setMinValue(0);
        currentAmount.setMaxValue(data.expendables_max);
        currentAmount.setValue(data.expendables_current);

        maxAmount.setMinValue(1);
        maxAmount.setMaxValue(100);
        maxAmount.setValue(data.expendables_max);

        maxAmount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentAmount.setValue(Math.min(currentAmount.getValue(), newVal));
                currentAmount.setMaxValue(newVal);
            }
        });

        titleLabel.requestFocus();

    }

    @Override
    public void resolveEditView(View editView) {
        final EditText titleLabel = (EditText) editView.findViewById(R.id.expendable_title);
        final NumberPicker currentAmount = (NumberPicker) editView.findViewById(R.id.expendable_current);
        final NumberPicker maxAmount = (NumberPicker) editView.findViewById(R.id.expendable_max);

        data.title = titleLabel.getText().toString();
        data.expendables_current = currentAmount.getValue();
        data.expendables_max = maxAmount.getValue();

        CharacterControl.tryCharacterChanged();
    }

    @Override
    public boolean canRemove() {
        return true;
    }

    @Override
    public void remove() {
        if(CharacterControl.hasCurrentCharacter()) {
            CharacterControl.getCurrentCharacter().removeExpendable(data);
            CharacterControl.getInstance().characterChanged();
        }
    }

    @Override
    public void setItem(BaseItem item) {
        data = (ExpendableModel) item;


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
    public int getItemResourceId() {
        return R.layout.item_expendable_view;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.info_expendable_view;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.edit_expendable_view;
    }

}

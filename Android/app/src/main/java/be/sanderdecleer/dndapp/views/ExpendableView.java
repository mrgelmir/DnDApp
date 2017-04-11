package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.model.character.FeatureModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 22/11/2016.
 * View for the {@link ExpendableModel} class
 */

public class ExpendableView implements ItemViewController {

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
                if(CharacterControl.hasCurrentCharacter())
                    CharacterControl.getInstance().characterChanged();
            }
        });
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.decrease();
                if(CharacterControl.hasCurrentCharacter())
                    CharacterControl.getInstance().characterChanged();
            }
        });
    }

    @Override
    public void setupInfoView(View view) {

    }

    @Override
    public void setupEditView(View editView) {

    }

    @Override
    public void resolveEditView(View editView) {

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

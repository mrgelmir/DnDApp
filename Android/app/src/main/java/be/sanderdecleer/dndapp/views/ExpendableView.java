package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.ExpendableModel;
import be.sanderdecleer.dndapp.model.character.FeatureModel;

/**
 * Created by SD on 22/11/2016.
 * View for the {@link ExpendableModel} class
 */

public class ExpendableView implements ItemViewController {

    private ExpendableModel data;

    @Override
    public void setDataChangedListener(DataChangedListener listener) {

    }

    @Override
    public void setupItemView(View itemView) {

        TextView titleView = (TextView) itemView.findViewById(R.id.expendable_title);
        TextView valueView = (TextView) itemView.findViewById(R.id.expendable_value);
        ImageButton increaseButton = (ImageButton) itemView.findViewById(R.id.expendable_btn_increase);
        ImageButton decreaseButton = (ImageButton) itemView.findViewById(R.id.expendable_btn_decrease);

        // Do actual setup
        titleView.setText(data.title);

        // TODO
//        valueView.setText(String.format(getContext().getString(R.string.expendable_value),
//                data.expendables_current, data.expendables_max));

        // TODO: hook up buttons
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.expendables_current += 1;
                dataUpdated();
            }
        });
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.expendables_current -= 1;
                dataUpdated();
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

    private void dataUpdated() {

        // TODO: remove. Adapter should decide to redraw if needed
//        valueView.setText(String.format(getContext().getString(R.string.expendable_value),
//                data.expendables_current, data.expendables_max));
    }
}

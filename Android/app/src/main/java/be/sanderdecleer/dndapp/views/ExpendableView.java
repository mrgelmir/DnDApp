package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.BaseItem;
import be.sanderdecleer.dndapp.model.ExpendableModel;

/**
 * Created by SD on 22/11/2016.
 * View for the {@link ExpendableModel} class
 */

public class ExpendableView extends BaseItemView<ExpendableModel> {

    private TextView titleView;
    private TextView valueView;
    ImageButton increaseButton;
    ImageButton decreaseButton;

    public ExpendableView(Context context) {
        super(context);
    }

    public ExpendableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpendableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setupItemView() {
        super.setupItemView();
        // Do stuff here
        titleView = (TextView) findViewById(R.id.expendable_title);
        valueView = (TextView) findViewById(R.id.expendable_value);
        increaseButton = (ImageButton) findViewById(R.id.expendable_btn_increase);
        decreaseButton = (ImageButton) findViewById(R.id.expendable_btn_decrease);
    }

    @Override
    public void setupInfoView(View view) {
        super.setupInfoView(view);

    }

    @Override
    public void setItem(BaseItem item) {
        super.setItem(item);

        // Do actual setup
        titleView.setText(data.title);
        valueView.setText(String.format(getContext().getString(R.string.expendable_value),
                data.expendables_current, data.expendables_max));

        // TODO: hook up buttons
        increaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                data.expendables_current += 1;
                dataUpdated();
            }
        });
        decreaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                data.expendables_current -= 1;
                dataUpdated();
            }
        });
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
        return R.layout.item_expendable_view;
    }

    @Override
    public int getDialogResourceId() {
        return super.getDialogResourceId();
    }

    @Override
    protected void dataUpdated() {
        super.dataUpdated();
        // TODO: remove. Adapter should decide to redraw if needed
        valueView.setText(String.format(getContext().getString(R.string.expendable_value),
                data.expendables_current, data.expendables_max));
    }
}

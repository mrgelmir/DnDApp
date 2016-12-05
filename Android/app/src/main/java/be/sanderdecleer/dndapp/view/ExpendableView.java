package be.sanderdecleer.dndapp.view;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.AttributeSet;
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
    public void setItem(BaseItem item) {
        super.setItem(item);

        // Do actual setup
        titleView.setText(data.title);
        valueView.setText(String.format(getContext().getString(R.string.expendable_value),
                data.expendables_current, data.expendables_max));

        // TODO: hook up buttons
    }

    @Override
    public int getResourceId() {
        return R.layout.item_expendable_view;
    }
}

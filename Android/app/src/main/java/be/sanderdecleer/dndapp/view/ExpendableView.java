package be.sanderdecleer.dndapp.view;

import android.content.Context;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.BaseItem;
import be.sanderdecleer.dndapp.model.ExpendableModel;

/**
 * Created by SD on 22/11/2016.
 */

public class ExpendableView extends BaseItemView {

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
    public void setupChildren() {
        super.setupChildren();
        // Do stuff here
        titleView = (TextView) findViewById(R.id.expendable_title);
        valueView = (TextView) findViewById(R.id.expendable_value);
        increaseButton = (ImageButton) findViewById(R.id.expendable_btn_increase);
        decreaseButton = (ImageButton) findViewById(R.id.expendable_btn_decrease);
    }

    @Override
    public void setItem(BaseItem item) {
        super.setItem(item);

        ExpendableModel data = this.getData(item);
        // TODO: 24/11/2016 throw error or something
        if(data == null)
            return;

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

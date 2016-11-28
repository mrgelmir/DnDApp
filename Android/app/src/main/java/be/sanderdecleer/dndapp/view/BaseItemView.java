package be.sanderdecleer.dndapp.view;

import android.content.Context;
import android.test.suitebuilder.annotation.Suppress;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.BaseItem;

/**
 * Created by SD on 22/11/2016.
 */

public class BaseItemView extends RelativeLayout {

    public BaseItemView(Context context) {
        super(context);
        setup(context);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public BaseItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context);
    }

    private void setup(Context context) {
        LayoutInflater.from(context).inflate(getResourceId(), this, true);
        setupChildren();
    }

    public void setItem(BaseItem item) {
    }

    public void setupChildren() {
    }

    public int getResourceId() {
        return R.layout.item_empty;
    }

    protected <T extends BaseItem> T getData(BaseItem item) {
        T data;

        // Check if item is of desired type
//        if (item instanceof T) {
//            // Type cast to DerivedNode to access bar
//            data = (T) item;
//        } else {
//            // Throw exception or what ever
//            throw new RuntimeException("Invalid Object Type");
//        }

        try {
            // how to fix this...
            data = (T) item;
            return data;
        }
        catch (ClassCastException e){
            return null;
        }

    }
}

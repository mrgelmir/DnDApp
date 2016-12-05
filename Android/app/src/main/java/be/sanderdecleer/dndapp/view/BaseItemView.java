package be.sanderdecleer.dndapp.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.dialog_fragments.InfoDialogFragment;
import be.sanderdecleer.dndapp.model.BaseItem;

/**
 * Created by SD on 22/11/2016.
 * The base for all listView items
 */

public class BaseItemView<T extends BaseItem> extends RelativeLayout implements InfoDialogFragment.ViewSetup {

    T data;

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
        setupItemView();
    }

    public void setItem(BaseItem item) {
        // Check if item is of desired type
        data = getData(item);
        if (data == null)
            return;
    }

    public void setupItemView() {
    }

    @Override
    public void setupInfoView(View view) {
    }

    public void onClick() {
//        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Get activity from context
        FragmentActivity a = (FragmentActivity) getContext();
        // Get fragment transaction
        FragmentTransaction ft = a.getSupportFragmentManager().beginTransaction();

        // Create dialog using inherited resources and show
        InfoDialogFragment newFragment = InfoDialogFragment.newInstance(getDialogResourceId());
        newFragment.setViewSetup(this);
        newFragment.show(ft, "info_dialog");

    }

    public int getResourceId() {
        return R.layout.item_empty;
    }

    public int getDialogResourceId() {
        return R.layout.item_empty;
    }

    protected T getData(BaseItem item) {
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
        } catch (ClassCastException e) {
            return null;
        }

    }
}

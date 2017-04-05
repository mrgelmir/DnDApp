package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.dialog_fragments.InfoDialogFragment;
import be.sanderdecleer.dndapp.model.character.BaseItem;

/**
 * Created by SD on 22/11/2016.
 * The base for all listView items
 */

public class BaseItemView<T extends BaseItem> extends RelativeLayout implements InfoDialogFragment.ViewSetup {

    T data;
    DataUpdateListener<T> listener;

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
        // TODO Check if item is of desired type and throw error
        data = getData(item);
        if (data == null)
            return;
    }

    public void setupItemView() {
    }

    @Override
    public void setupInfoView(View view) {
    }

    @Override
    public boolean hasTitle() {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    public void onClick() {

        // Get activity from context
        FragmentActivity a = (FragmentActivity) getContext();
        // Get fragment transaction
        FragmentTransaction ft = a.getSupportFragmentManager().beginTransaction();

        // Create dialog using inherited resources and show
        InfoDialogFragment newFragment = InfoDialogFragment.newInstance(getDialogResourceId());
        newFragment.setViewSetup(this);
        newFragment.show(ft, "info_dialog");

    }

    public void onLongClick() {
        // open edit panel
        Toast.makeText(getContext(), "EDIT VIEW HERE", Toast.LENGTH_SHORT).show();
    }

    public int getResourceId() {
        return R.layout.item_empty;
    }

    public int getDialogResourceId() {
        return R.layout.item_empty;
    }

    public void setListener(DataUpdateListener<T> listener) {
        this.listener = listener;
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

    protected void dataUpdated() {
        // TODO figure out how to propagate the changes to the model
    }

    public interface DataUpdateListener<U extends BaseItem> {
        void DataUpdated(U data);
    }
}

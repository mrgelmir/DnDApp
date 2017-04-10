package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.BaseItem;

/**
 * Created by SD on 22/11/2016.
 * The base for all listView items
 */

public final class BaseItemView<T extends BaseItem> extends RelativeLayout
        implements ItemViewController.DataChangedListener {

    /**
     * The view controller determining which views will be loaded and how they will be set up.
     */
    private ItemViewController viewController;

    DataUpdateListener<T> listener;

    public BaseItemView(Context context) {
        super(context);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(ItemViewController viewController) {
        this.viewController = viewController;
        this.viewController.setDataChangedListener(this);
        LayoutInflater.from(getContext()).inflate(viewController.getItemResourceId(), this, true);
    }

    public void setItem(BaseItem item) {
        viewController.setItem(item);
        viewController.setupItemView(this);
    }

    public void onClick() {
        CreateDialog(viewController.getInfoResourceId(), ItemDialogFragment.VIEW_TYPE_INFO, "info_dialog");
    }

    public void onLongClick() {
        CreateDialog(viewController.getEditResourceId(), ItemDialogFragment.VIEW_TYPE_EDIT, "edit_dialog");
    }

    private void CreateDialog(@LayoutRes int resourceId, @ItemDialogFragment.ViewType int viewType, String tag) {

        // Get FragmentManager
        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();

        // Create dialog using inherited resources
        ItemDialogFragment itemDialogFragment = ItemDialogFragment.newInstance(resourceId, viewType);
        itemDialogFragment.setViewSetup(viewController);

        // TODO: 10/04/2017 move to resources or something
        boolean fullScreen = false;

        // Get fragment transaction
        if (fullScreen) {
            // TODO: 10/04/2017 Add a background here
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.add(android.R.id.content, itemDialogFragment)
                    .addToBackStack(null).commit();
        } else {
            itemDialogFragment.show(fragmentManager, tag);
        }


    }


    // TODO move data stuff to viewController

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

    public void dataChanged(BaseItem data) {
        // TODO figure out how to propagate the changes to the model
        if (listener != null)
            listener.DataUpdated((T) data);
    }

    public interface DataUpdateListener<U extends BaseItem> {
        void DataUpdated(U data);
    }
}

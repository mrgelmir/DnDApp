package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.BaseItem;

/**
 * Created by SD on 22/11/2016.
 * The base for all listView items
 */

public final class BaseItemView<T extends BaseItem> extends RelativeLayout {

    /**
     * The view controller determining which views will be loaded and how they will be set up.
     */
    private ItemViewController viewController;

    public BaseItemView(Context context) {
        super(context);
        init();
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init (){
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    public void setup(ItemViewController viewController) {
        this.viewController = viewController;
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
}

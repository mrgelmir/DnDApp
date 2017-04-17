package be.sanderdecleer.dndapp.views;

import android.content.Context;
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

public final class BaseItemView extends RelativeLayout {

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

    private void init() {
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
        ItemDialogFragment.showItemViewDialog(getContext(), viewController,
                ItemDialogFragment.VIEW_TYPE_INFO);
    }

    public void onLongClick() {
        ItemDialogFragment.showItemViewDialog(getContext(), viewController,
                ItemDialogFragment.VIEW_TYPE_EDIT);
    }

}

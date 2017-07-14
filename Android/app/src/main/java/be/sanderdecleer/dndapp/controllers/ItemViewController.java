package be.sanderdecleer.dndapp.controllers;

import android.support.annotation.LayoutRes;
import android.support.v7.view.menu.MenuView;
import android.view.View;

import be.sanderdecleer.dndapp.model.character.BaseItem;

/**
 * Created by SD on 6/04/2017.
 * Base class responsible for displaying items @{@link BaseItem} derivatives with custom functionality
 * Stores resource id of and functions for setting up:
 * - ItemView
 * - InfoView
 * - EditView
 */

public interface ItemViewController {

    /**
     * Set the data associated with the item
     *
     * @param item a BaseItem which will be converted to the appropriate subclass
     */
    void setItem(BaseItem item);

    /**
     * Passes the item view for initialisation
     *
     * @param itemView the item View, inflated from getInfoResourceId
     */
    void setupItemView(View itemView);

    /**
     * Passes the item view for initialisation
     *
     * @param infoView the info View, inflated from getInfoResourceId
     */
    void setupInfoView(View infoView);

    /**
     * Passes the edit view for initialisation
     *
     * @param editView
     */
    void setupEditView(View editView);

    /**
     * Passes the edit view to get final values
     *
     * @param editView the given View
     */
    void resolveEditView(View editView);



    @LayoutRes
    int getItemResourceId();

    @LayoutRes
    int getInfoResourceId();

    @LayoutRes
    int getEditResourceId();


    // TODO: Figure out if functionality below is really needed

    /**
     * Removes this object from the current character
     */
    void remove();

    boolean canRemove();

    boolean hasTitle();

    String getTitle();

}


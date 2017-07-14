package be.sanderdecleer.dndapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import be.sanderdecleer.dndapp.controllers.ItemViewController;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.views.ItemViewType;

/**
 * Created by SD on 14/07/2017.
 * This class is responsible for creating the correct layout when given a @{@link be.sanderdecleer.dndapp.model.character.BaseItem}
 */

public class LayoutFactory {

    public static View createView(LayoutInflater inflater, ViewGroup container,
                                  BaseItem item, ItemViewType viewType) {
        return createView(inflater, container, item, viewType, false);
    }

    public static View createView(LayoutInflater inflater, ViewGroup container,
                                  BaseItem item, ItemViewType viewType, boolean attachToRoot) {

        ItemViewController viewController = item.getViewController();

        View view = null;
        switch (viewType) {
            case ITEM:
                view = inflater.inflate(viewController.getItemResourceId(), container, attachToRoot);
                viewController.setupItemView(view);
                break;
            case INFO:
                view = inflater.inflate(viewController.getInfoResourceId(), container, attachToRoot);
                viewController.setupInfoView(view);
                break;
            case EDIT:
                view = inflater.inflate(viewController.getEditResourceId(), container, attachToRoot);
                viewController.setupEditView(view);
                break;
        }

        return view;
    }

}

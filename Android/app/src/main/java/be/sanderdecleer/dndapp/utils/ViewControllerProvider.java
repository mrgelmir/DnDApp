package be.sanderdecleer.dndapp.utils;

import be.sanderdecleer.dndapp.controllers.AbilityViewController;
import be.sanderdecleer.dndapp.controllers.ExpendableViewController;
import be.sanderdecleer.dndapp.controllers.FeatureViewController;
import be.sanderdecleer.dndapp.controllers.WeaponViewController;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.controllers.ItemViewController;

/**
 * Created by SD on 14/07/2017.
 * Provides a ViewController from an item model
 */

public class ViewControllerProvider {

    public static ItemViewController getViewController(BaseItem item) {

        ItemViewController controller = null;

        // Get corresponding controller if it exists
        switch (item.getType()) {
            case Weapon:
                controller = new WeaponViewController();
                break;
            case Feature:
                controller = new FeatureViewController();
                break;
            case Expendable:
                controller = new ExpendableViewController();
                break;
            case AbilityScore:
                controller = new AbilityViewController();
                break;
            // TODO below
            default:
            case Item:
            case ArmorClass:
            case Speed:
                controller = null;
                break;
        }

        // Set the model
        if (null != controller) {
            controller.setItem(item);
        }

        return controller;
    }
}

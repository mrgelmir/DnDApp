package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.WeaponModel;

/**
 * Created by SD on 22/11/2016.
 * Holds the view elements of a {@link WeaponModel}
 */

public class WeaponView extends BaseItemView<WeaponModel> {

    private TextView displayLabel;
    private TextView toHitLabel;
    private TextView damageLabel;

    public WeaponView(Context context) {
        super(context);
    }

    public WeaponView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeaponView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setItem(BaseItem item) {
        super.setItem(item);

        // Do actual setup
        displayLabel.setText(data.getDisplayName());
        damageLabel.setText(data.weaponDamage);
        toHitLabel.setText(data.weaponToHit);
    }

    @Override
    public void setupItemView() {
        super.setupItemView();
        displayLabel = (TextView) findViewById(R.id.weapon_display_name);
        toHitLabel = (TextView) findViewById(R.id.weapon_to_hit);
        damageLabel = (TextView) findViewById(R.id.weapon_damage);
    }



    @Override
    public int getResourceId() {
        return R.layout.item_weapon_view;
    }
}

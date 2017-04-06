package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.WeaponModel;

/**
 * Created by SD on 22/11/2016.
 * Controls the view elements of a {@link WeaponModel} via the {@link BaseItemView}
 */

public class WeaponView implements ItemViewController {

    private WeaponModel data;

    // ItemView views
    private TextView displayLabel;
    private TextView toHitLabel;
    private TextView damageLabel;

    @Override
    public void setItem(BaseItem item) {
        data = (WeaponModel) item;

        // Do actual setup
        displayLabel.setText(data.getDisplayName());
        damageLabel.setText(data.weaponDamage);
        toHitLabel.setText(data.weaponToHit);
    }

    @Override
    public void setupItemView(View itemView) {
        displayLabel = (TextView) itemView.findViewById(R.id.weapon_display_name);
        toHitLabel = (TextView) itemView.findViewById(R.id.weapon_to_hit);
        damageLabel = (TextView) itemView.findViewById(R.id.weapon_damage);
    }

    @Override
    public void setupInfoView(View infoView) {
    }

    @Override
    public void setupEditView(View editView) {

    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_weapon_view;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.info_weapon_view;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.edit_weapon_view;
    }

    @Override
    public boolean hasTitle() {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }
}

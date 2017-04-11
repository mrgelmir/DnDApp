package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.WeaponModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 22/11/2016.
 * Controls the view elements of a {@link WeaponModel} via the {@link BaseItemView}
 */

public class WeaponView implements ItemViewController {

    // Reference to data
    private WeaponModel data;

    @Override
    public void setItem(BaseItem item) {
        data = (WeaponModel) item;
    }

    @Override
    public void setupItemView(View itemView) {
        TextView displayLabel = (TextView) itemView.findViewById(R.id.weapon_display_name);
        TextView toHitLabel = (TextView) itemView.findViewById(R.id.weapon_to_hit);
        TextView damageLabel = (TextView) itemView.findViewById(R.id.weapon_damage);

        // Do actual setup
        displayLabel.setText(data.getDisplayName());
        damageLabel.setText(data.weaponDamage);
        toHitLabel.setText(data.weaponToHit);
    }

    @Override
    public void setupInfoView(View infoView) {
        TextView typeLabel = (TextView) infoView.findViewById(R.id.weapon_type);
        TextView toHitLabel = (TextView) infoView.findViewById(R.id.weapon_to_hit);
        TextView damageLabel = (TextView) infoView.findViewById(R.id.weapon_damage);
        TextView featureLabel = (TextView) infoView.findViewById(R.id.weapon_features);

        typeLabel.setText(data.weaponType);
        toHitLabel.setText(data.weaponToHit);
        damageLabel.setText(data.weaponDamage);

        String s = "";
        for (int i = 0; i < data.weaponFeatures.length; i++) {
            s += data.weaponFeatures[i];
            if (i < (data.weaponFeatures.length - 1))
                s += ", ";
        }
        featureLabel.setText(s);
    }

    @Override
    public void setupEditView(View editView) {

        // Set field data
        EditText nicknameLabel = (EditText) editView.findViewById(R.id.weapon_edit_nickname);
        EditText typeLabel = (EditText) editView.findViewById(R.id.weapon_edit_type);
        EditText toHitLabel = (EditText) editView.findViewById(R.id.weapon_edit_to_hit);
        EditText damageLabel = (EditText) editView.findViewById(R.id.weapon_edit_damage);

        nicknameLabel.setText(data.nickname);
        typeLabel.setText(data.weaponType);
        toHitLabel.setText(data.weaponToHit);
        damageLabel.setText(data.weaponDamage);
    }

    @Override
    public void resolveEditView(View editView) {

//        CharacterControl.getCurrentCharacter().removeWeapon(data);

        // Get data from fields
        EditText nicknameLabel = (EditText) editView.findViewById(R.id.weapon_edit_nickname);
        EditText typeLabel = (EditText) editView.findViewById(R.id.weapon_edit_type);
        EditText toHitLabel = (EditText) editView.findViewById(R.id.weapon_edit_to_hit);
        EditText damageLabel = (EditText) editView.findViewById(R.id.weapon_edit_damage);

        data.nickname = nicknameLabel.getText().toString();
        data.weaponType = typeLabel.getText().toString();
        data.weaponToHit = toHitLabel.getText().toString();
        data.weaponDamage = damageLabel.getText().toString();

        // Propagate changes
        if(CharacterControl.hasCurrentCharacter()){
            CharacterControl.getInstance().characterChanged();
        }
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
        return data.getDisplayName();
    }
}

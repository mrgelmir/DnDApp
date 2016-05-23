package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.model.WeaponModel;
import be.sanderdecleer.dndapp.utils.LayoutUtils;

/**
 * Created by SD on 23/05/2016.
 */
public class WeaponAdapter extends ArrayAdapter<WeaponModel> {

    private int layoutResourceId;
    private CharacterModel character = null;

    public WeaponAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        if (character != null && character.weapons != null) {
            return character.weapons.size();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder vh;

        // If convertView is null: create new one
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            vh = new ViewHolder();

            vh.nameView = (TextView) convertView.findViewById(R.id.weapon_display_name);
            vh.toHitView = (TextView) convertView.findViewById(R.id.weapon_to_hit);
            vh.damageView = (TextView) convertView.findViewById(R.id.weapon_damage);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Get Data object
        final WeaponModel weaponData = character.weapons.get(position);

        // Set data in view
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_weapon_view_full, weaponData.getDisplayName(), new LayoutUtils.EditViewCallback() {
                    @Override
                    public void EditView(View view) {
                        // Do view editing here
                        TextView typeView = (TextView) view.findViewById(R.id.weapon_type);
                        TextView toHitView = (TextView) view.findViewById(R.id.weapon_to_hit);
                        TextView damageView = (TextView) view.findViewById(R.id.weapon_damage);
                        TextView featureView = (TextView) view.findViewById(R.id.weapon_features);

                        typeView.setText(weaponData.weaponType);
                        toHitView.setText(weaponData.weaponToHit);
                        damageView.setText(weaponData.weaponDamage);
                        featureView.setText(weaponData.weaponFeatures);
                    }
                });

            }
        });

        vh.nameView.setText(weaponData.getDisplayName());
        vh.toHitView.setText(weaponData.weaponToHit);
        vh.damageView.setText(weaponData.weaponDamage);

        return convertView;
    }

    public void setCharacter(CharacterModel character) {
        this.character = character;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView nameView;
        public TextView toHitView;
        public TextView damageView;
    }
}

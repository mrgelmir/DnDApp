package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.WeaponModel;
import be.sanderdecleer.dndapp.utils.EditControl;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.utils.OnClickListenerEditable;

/**
 * Adapter for weapon views
 */
@Deprecated
public class WeaponAdapter extends BaseCharacterAdapter<WeaponModel>
        implements EditControl.EditModeChangedListener {


    public WeaponAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        EditControl.addListener(this);
    }

    @Override
    public int getCount() {
        if (character != null && character.weapons != null) {
            return character.weapons.size() + (EditControl.isEditMode() ? 1 : 0);
        }
        return 0;
    }

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ADD = 1;

    @Override
    public int getItemViewType(int position) {
        return character.weapons.size() == position ? VIEW_TYPE_ADD : VIEW_TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder vh;

        int type = getItemViewType(position);

        // If convertView is null: create new one
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            vh = new ViewHolder();

            switch (type) {
                default:
                case VIEW_TYPE_ITEM:
                    convertView = inflater.inflate(layoutResourceId, parent, false);

                    vh.nameView = (TextView) convertView.findViewById(R.id.weapon_display_name);
                    vh.toHitView = (TextView) convertView.findViewById(R.id.weapon_to_hit);
                    vh.damageView = (TextView) convertView.findViewById(R.id.weapon_damage);
                    break;
                case VIEW_TYPE_ADD:
                    convertView = inflater.inflate(R.layout.item_add, parent, false);

                    // Doesn't really fit, but I can live with it
                    vh.nameView = (TextView) convertView.findViewById(R.id.ability_title);
                    break;

            }

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Special case: Adding a new item
        if (type == VIEW_TYPE_ADD) {

            // Set correct title
            vh.nameView.setText(R.string.weapon_edit_add);

            // add click listeners
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create new feature model
                    character.weapons.add(WeaponModel.getEmpty(getContext()));
                    character.hasChanges = true;
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

        // Get Data object
        final WeaponModel weaponData = character.weapons.get(position);

        // Set data in view
        convertView.setOnClickListener(new OnClickListenerEditable(
                new WeaponClickListener(weaponData),
                new WeaponClickListenerEdit(weaponData)));

        vh.nameView.setText(weaponData.getDisplayName());
        vh.toHitView.setText(weaponData.weaponToHit);
        vh.damageView.setText(weaponData.weaponDamage);

        return convertView;
    }

    @Override
    public void OnEditModeChanged(boolean isEditMode) {
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView nameView;
        public TextView toHitView;
        public TextView damageView;
    }

    private class WeaponClickListener implements View.OnClickListener {

        private WeaponModel weaponData;

        public WeaponClickListener(WeaponModel weaponData) {
            this.weaponData = weaponData;
        }

        /**
         * Show the dialog containing data
         */
        @Override
        public void onClick(View v) {
            LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_weapon_view_full, weaponData.getDisplayName(),
                    new LayoutUtils.EditViewCallback() {
                        @Override
                        public void EditView(View view) {
                            TextView typeView = (TextView) view.findViewById(R.id.weapon_type);
                            TextView toHitView = (TextView) view.findViewById(R.id.weapon_to_hit);
                            TextView damageView = (TextView) view.findViewById(R.id.weapon_damage);
                            TextView featureView = (TextView) view.findViewById(R.id.weapon_features);

                            typeView.setText(weaponData.weaponType);
                            toHitView.setText(weaponData.weaponToHit);
                            damageView.setText(weaponData.weaponDamage);
                            featureView.setText(weaponData.weaponFeatures);

                            // Hide weapon type if no nickname is present
                            if (weaponData.weaponType.equals(weaponData.getDisplayName())) {
                                typeView.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    private class WeaponClickListenerEdit implements View.OnClickListener {

        private WeaponModel weaponData;

        public WeaponClickListenerEdit(WeaponModel weaponData) {
            this.weaponData = weaponData;
            character.hasChanges = true;
        }

        /**
         * Show the dialog for editing data
         */
        @Override
        public void onClick(View v) {
            LayoutUtils.showEditDialog((Activity) getContext(), R.layout.edit_weapon_view, weaponData.getDisplayName(),
                    new LayoutUtils.EditViewCallback() {
                        @Override
                        public void EditView(View view) {
                            // Set up view for display
                            EditText nickNameView = (EditText) view.findViewById(R.id.weapon_edit_nickname);
                            EditText weaponTypeView = (EditText) view.findViewById(R.id.weapon_edit_type);
                            EditText toHitView = (EditText) view.findViewById(R.id.weapon_edit_to_hit);
                            EditText damageView = (EditText) view.findViewById(R.id.weapon_edit_damage);

                            nickNameView.setText(weaponData.nickname);
                            weaponTypeView.setText(weaponData.weaponType);
                            toHitView.setText(weaponData.weaponToHit);
                            damageView.setText(weaponData.weaponDamage);
                        }
                    }, new LayoutUtils.DismissDialogCallback() {
                        @Override
                        public void OnDialogDismissed(View view) {
                            // Save edited data
                            EditText nickNameView = (EditText) view.findViewById(R.id.weapon_edit_nickname);
                            EditText weaponTypeView = (EditText) view.findViewById(R.id.weapon_edit_type);
                            EditText toHitView = (EditText) view.findViewById(R.id.weapon_edit_to_hit);
                            EditText damageView = (EditText) view.findViewById(R.id.weapon_edit_damage);

                            weaponData.nickname = nickNameView.getText().toString();
                            weaponData.weaponType = weaponTypeView.getText().toString();
                            weaponData.weaponToHit = toHitView.getText().toString();
                            weaponData.weaponDamage = damageView.getText().toString();

                            notifyDataSetChanged();
                        }
                    }, new LayoutUtils.OnDeleteCallback() {
                        @Override
                        public void OnDelete(View view) {
                            // Delete this instance
                            character.weapons.remove(weaponData);
                            notifyDataSetChanged();
                        }
                    });
        }
    }
}

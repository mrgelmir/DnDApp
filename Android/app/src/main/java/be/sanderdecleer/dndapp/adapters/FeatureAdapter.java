package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.activities.CharacterSheet;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.utils.OnClickListenerEditable;

/**
 * Adapter for feature views
 */
public class FeatureAdapter extends BaseCharacterAdapter<FeatureModel> {

    public FeatureAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
    }

    @Override
    public int getCount() {
        if (character != null && character.abilities != null) {
            return character.abilities.size() + (CharacterSheet.isEditMode() ? 1 : 0);
        }
        return 0;
    }

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ADD = 1;

    @Override
    public int getItemViewType(int position) {
        return character.abilities.size() == position ? VIEW_TYPE_ADD : VIEW_TYPE_ITEM;
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

            switch (type){
                case VIEW_TYPE_ADD:
                    convertView = inflater.inflate(R.layout.p_add_item, parent, false);
                    vh.titleView = (TextView) convertView.findViewById(R.id.ability_title);
                    break;
                default:
                case VIEW_TYPE_ITEM:
                    convertView = inflater.inflate(layoutResourceId, parent, false);
                    vh.titleView = (TextView) convertView.findViewById(R.id.ability_title);
                    break;
            }

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Special case: add a new feature view
        if (type == VIEW_TYPE_ADD) {

            vh.titleView.setText("Add new thingy");

            // add click listeners
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create new feature model
                    character.abilities.add(FeatureModel.getEmpty(getContext()));
                    notifyDataSetInvalidated();
                }
            });

            return convertView;
        }

        // Default case below

        // Get Data object
        final FeatureModel featureData = character.abilities.get(position);

        // TODO: 2/06/2016 find out how to do this somewhat more legible.

        // Set data in view
        convertView.setOnClickListener(new OnClickListenerEditable(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_feature_view_full,
                                featureData.title, new LayoutUtils.EditViewCallback() {
                                    @Override
                                    public void EditView(View view) {
                                        TextView descriptionView = (TextView) view.findViewById(R.id.ability_description);
                                        descriptionView.setText(featureData.description);
                                    }
                                });
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LayoutUtils.showEditDialog((Activity) getContext(), R.layout.edit_feature_view,
                                featureData.title, new LayoutUtils.EditViewCallback() {
                                    @Override
                                    public void EditView(View view) {

                                        TextView titleView = (TextView) view.findViewById(R.id.feature_edit_title);
                                        titleView.setText(featureData.title);
                                        TextView descriptionView = (TextView) view.findViewById(R.id.feature_edit_description);
                                        descriptionView.setText(featureData.description);
                                    }
                                }, new LayoutUtils.DismissDialogCallback() {
                                    @Override
                                    public void OnDialogDismissed(View view) {

                                        TextView titleView = (TextView) view.findViewById(R.id.feature_edit_title);
                                        featureData.title = titleView.getText().toString();
                                        TextView descriptionView = (TextView) view.findViewById(R.id.feature_edit_description);
                                        featureData.description = descriptionView.getText().toString();
                                    }
                                }, new LayoutUtils.OnDeleteCallback() {
                                    @Override
                                    public void OnDelete(View view) {
                                        character.abilities.remove(featureData);
                                        notifyDataSetInvalidated();
                                    }
                                });

                    }
                }));

        vh.titleView.setText(featureData.title);

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleView;
    }

    // TODO: 3/06/2016  
    private class InfoDialogListener implements View.OnClickListener {

        private FeatureModel featureData;

        public InfoDialogListener(FeatureModel featureData) {
            this.featureData = featureData;
        }

        @Override
        public void onClick(View v) {
            LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_feature_view_full,
                    featureData.title, new LayoutUtils.EditViewCallback() {
                        @Override
                        public void EditView(View view) {
                            TextView descriptionView = (TextView) view.findViewById(R.id.ability_description);
                            descriptionView.setText(featureData.description);
                        }
                    });
        }
    }
}

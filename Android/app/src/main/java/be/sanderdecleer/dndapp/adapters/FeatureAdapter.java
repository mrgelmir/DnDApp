package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.utils.EditControl;
import be.sanderdecleer.dndapp.utils.LayoutUtils;
import be.sanderdecleer.dndapp.utils.OnClickListenerEditable;

/**
 * Adapter for feature views
 */
public class FeatureAdapter extends BaseCharacterAdapter<FeatureModel>
        implements EditControl.EditModeChangedListener {

    public FeatureAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        EditControl.addListener(this);
    }

    @Override
    public int getCount() {
        if (character != null && character.abilities != null) {
            return character.abilities.size() + (EditControl.isEditMode() ? 1 : 0);
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

            switch (type) {
                case VIEW_TYPE_ADD:
                    convertView = inflater.inflate(R.layout.item_add, parent, false);
                    vh.titleView = (TextView) convertView.findViewById(R.id.ability_title);
                    break;
                default:
                case VIEW_TYPE_ITEM:
                    convertView = inflater.inflate(layoutResourceId, parent, false);
                    vh.titleView = (TextView) convertView.findViewById(R.id.ability_title);
                    vh.descriptionView = (TextView) convertView.findViewById(R.id.ability_description);
                    break;
            }

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Special case: add a new feature view
        if (type == VIEW_TYPE_ADD) {

            // Set correct title
            vh.titleView.setText(R.string.feature_edit_add);

            // Add click listeners
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create new feature model
                    character.abilities.add(FeatureModel.getEmpty(getContext()));
                    character.hasChanges = true;
                    notifyDataSetChanged();
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
                new FeatureClickListener(featureData),
                new FeatureClickListenerEdit(featureData)));

        vh.titleView.setText(featureData.title);
        vh.descriptionView.setText(featureData.description);

        return convertView;
    }

    @Override
    public void OnEditModeChanged(boolean isEditMode) {
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView titleView;
        public TextView descriptionView;
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

    private class FeatureClickListener implements View.OnClickListener {

        private FeatureModel featureData;

        public FeatureClickListener(FeatureModel featureData) {
            this.featureData = featureData;
        }

        /**
         * Show the dialog containing data
         */
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

    private class FeatureClickListenerEdit implements View.OnClickListener {

        private FeatureModel featureData;

        public FeatureClickListenerEdit(FeatureModel featureData) {
            this.featureData = featureData;
            character.hasChanges = true;
        }

        /**
         * Show the dialog for editing data
         */
        @Override
        public void onClick(View v) {
            LayoutUtils.showEditDialog((Activity) getContext(), R.layout.edit_feature_view,
                    featureData.title, new LayoutUtils.EditViewCallback() {
                        @Override
                        public void EditView(View view) {
                            // Set up view for display
                            EditText titleView = (EditText) view.findViewById(R.id.feature_edit_title);
                            EditText descriptionView = (EditText) view.findViewById(R.id.feature_edit_description);

                            titleView.setText(featureData.title);
                            descriptionView.setText(featureData.description);
                        }
                    }, new LayoutUtils.DismissDialogCallback() {
                        @Override
                        public void OnDialogDismissed(View view) {
                            // Save edited data
                            EditText titleView = (EditText) view.findViewById(R.id.feature_edit_title);
                            EditText descriptionView = (EditText) view.findViewById(R.id.feature_edit_description);

                            featureData.title = titleView.getText().toString();
                            featureData.description = descriptionView.getText().toString();
                            notifyDataSetChanged();
                        }
                    }, new LayoutUtils.OnDeleteCallback() {
                        @Override
                        public void OnDelete(View view) {
                            // Delete this instance
                            character.abilities.remove(featureData);
                            notifyDataSetChanged();
                        }
                    });
        }
    }
}

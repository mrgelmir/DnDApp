package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
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
            return character.abilities.size();
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
            vh.titleView = (TextView) convertView.findViewById(R.id.ability_title);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Get Data object
        final FeatureModel featureData = character.abilities.get(position);

        // TODO: 2/06/2016 find out how to do this somewhat more legible.

        // Set data in view
        convertView.setOnClickListener(new OnClickListenerEditable(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_ability_view_full,
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

                        LayoutUtils.showEditDialog((Activity) getContext(), R.layout.p_ability_view_edit,
                                featureData.title, new LayoutUtils.EditViewCallback() {
                                    @Override
                                    public void EditView(View view) {

                                        TextView titleView = (TextView) view.findViewById(R.id.ability_edit_title);
                                        titleView.setText(featureData.title);
                                        TextView descriptionView = (TextView) view.findViewById(R.id.ability_edit_description);
                                        descriptionView.setText(featureData.description);
                                    }
                                }, new LayoutUtils.DismissDialogCallback() {
                                    @Override
                                    public void OnDialogDismissed(View view) {

                                        TextView titleView = (TextView) view.findViewById(R.id.ability_edit_title);
                                        featureData.title = titleView.getText().toString();
                                        TextView descriptionView = (TextView) view.findViewById(R.id.ability_edit_description);
                                        featureData.description = descriptionView.getText().toString();
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
            LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_ability_view_full,
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

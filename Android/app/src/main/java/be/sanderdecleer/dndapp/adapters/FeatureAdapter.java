package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.model.CharacterModel;
import be.sanderdecleer.dndapp.utils.LayoutUtils;

/**
 * Created by SD on 20/05/2016.
 */
public class FeatureAdapter extends ArrayAdapter<FeatureModel> {

    private int layoutResourceId;
    private CharacterModel character = null;

    public FeatureAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        if(character != null && character.abilities != null) {
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
        final FeatureModel abilityData = character.abilities.get(position);
//        FeatureModel abilityData = new FeatureModel("title " + position, "description");

        // Set data in view
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutUtils.showInfoDialog((Activity) getContext(), R.layout.p_ability_view_full,
                        abilityData.title, new LayoutUtils.EditViewCallback() {
                            @Override
                            public void EditView(View view) {
                                TextView descriptionView = (TextView) view.findViewById(R.id.ability_description);
                                descriptionView.setText(abilityData.description);
                            }
                        });
            }
        });

        vh.titleView.setText(abilityData.title);

        return convertView;
    }

    public void setCharacter(CharacterModel character){
        this.character = character;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView titleView;
    }
}

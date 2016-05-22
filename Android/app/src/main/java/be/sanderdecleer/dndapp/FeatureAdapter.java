package be.sanderdecleer.dndapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import be.sanderdecleer.dndapp.model.FeatureModel;
import be.sanderdecleer.dndapp.model.CharacterModel;

/**
 * Created by SD on 20/05/2016.
 */
public class FeatureAdapter extends ArrayAdapter<FeatureModel> {

    public int layoutResourceId;
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
            vh.descriptionView = (TextView) convertView.findViewById(R.id.ability_description);
            vh.descriptionView.setVisibility(View.GONE);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Get Data object
        FeatureModel abilityData = character.abilities.get(position);
//        FeatureModel abilityData = new FeatureModel("title " + position, "description");

        // Set data in view
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.descriptionView.setVisibility(vh.descriptionView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                parent.forceLayout();
            }
        });

        vh.titleView.setText(abilityData.title);
        vh.descriptionView.setText(abilityData.description);

        return convertView;
    }

    public void setCharacter(CharacterModel character){
        this.character = character;
    }

    private static class ViewHolder {
        public TextView titleView;
        public TextView descriptionView;
    }
}

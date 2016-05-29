package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.model.ExpendableModel;

/**
 * Created by SD on 29/05/2016.
 */
public class ExpendableAdapter extends BaseCharacterAdapter<ExpendableModel> {

    public ExpendableAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
    }

    @Override
    public int getCount() {
        if (character != null && character.expendables != null) {
            return character.expendables.size();        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            vh = new ViewHolder();
            vh.titleView = (TextView) convertView.findViewById(R.id.expendable_title);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ExpendableModel expendableData = character.expendables.get(position);

        vh.titleView.setText(expendableData.title);

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleView;
        public LinearLayout expendableCollectionView;
    }
}

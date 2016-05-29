package be.sanderdecleer.dndapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
            return character.expendables.size();
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            vh = new ViewHolder();
            vh.titleView = (TextView) convertView.findViewById(R.id.expendable_title);
            vh.valueView = (TextView) convertView.findViewById(R.id.expendable_value);
            vh.increaseButton = (ImageButton) convertView.findViewById(R.id.expendable_btn_increase);
            vh.decreaseButton = (ImageButton) convertView.findViewById(R.id.expendable_btn_decrease);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ExpendableModel expendableData = character.expendables.get(position);

        vh.titleView.setText(expendableData.title);
        setValueFormatted(vh.valueView, expendableData);
        vh.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.expendables.get(position).increase();
                setValueFormatted(vh.valueView, expendableData);
            }
        });
        vh.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.expendables.get(position).decrease();
                setValueFormatted(vh.valueView, expendableData);
            }
        });


        return convertView;
    }

    private void setValueFormatted(TextView valueView, ExpendableModel expendableData) {
        valueView.setText(String.format(getContext().getString(R.string.expendable_value),
                expendableData.expendables_current, expendableData.expendables_max));
    }

    private static class ViewHolder {
        public TextView titleView;
        public TextView valueView;
        public ImageButton increaseButton;
        public ImageButton decreaseButton;
    }
}

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
import be.sanderdecleer.dndapp.model.WeaponModel;
import be.sanderdecleer.dndapp.utils.EditControl;

/**
 * Adapter for Expendables
 */
public class ExpendableAdapter extends BaseCharacterAdapter<ExpendableModel>
        implements EditControl.EditModeChangedListener {

    public ExpendableAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        EditControl.addListener(this);
    }

    @Override
    public int getCount() {
        if (character != null && character.expendables != null) {
            return character.expendables.size() + (EditControl.isEditMode() ? 1 : 0);
        }
        return 0;
    }

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ADD = 1;

    @Override
    public int getItemViewType(int position) {
        return character.expendables.size() == position ? VIEW_TYPE_ADD : VIEW_TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        int type = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            vh = new ViewHolder();

            switch (type) {
                default:
                case VIEW_TYPE_ITEM:
                    convertView = inflater.inflate(layoutResourceId, parent, false);

                    vh.titleView = (TextView) convertView.findViewById(R.id.expendable_title);
                    vh.valueView = (TextView) convertView.findViewById(R.id.expendable_value);
                    vh.increaseButton = (ImageButton) convertView.findViewById(R.id.expendable_btn_increase);
                    vh.decreaseButton = (ImageButton) convertView.findViewById(R.id.expendable_btn_decrease);
                    break;
                case VIEW_TYPE_ADD:
                    convertView = inflater.inflate(R.layout.p_add_item, parent, false);
                    break;

            }

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // Special case: add expendable button
        if(type == VIEW_TYPE_ADD) {

            // add click listeners
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create new feature model
                    character.expendables.add(ExpendableModel.getEmpty(getContext()));
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

        // Default case below

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

    @Override
    public void OnEditModeChanged(boolean isEditMode) {
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView titleView;
        public TextView valueView;
        public ImageButton increaseButton;
        public ImageButton decreaseButton;
    }
}

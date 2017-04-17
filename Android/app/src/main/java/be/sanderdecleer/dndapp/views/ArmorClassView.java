package be.sanderdecleer.dndapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.dialog_fragments.ItemDialogFragment;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.model.character.CharacterModel;
import be.sanderdecleer.dndapp.utils.CharacterControl;

/**
 * Created by SD on 17/04/2017.
 */

public class ArmorClassView extends RelativeLayout implements ItemViewController {

    public ArmorClassView(Context context) {
        super(context);
        setup();
    }

    public ArmorClassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public ArmorClassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public ArmorClassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    private void setup() {
        final ArmorClassView thisInstance = this;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDialogFragment.showItemViewDialog(getContext(), thisInstance,
                        ItemDialogFragment.VIEW_TYPE_INFO);
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ItemDialogFragment.showItemViewDialog(getContext(), thisInstance,
                        ItemDialogFragment.VIEW_TYPE_EDIT);
                return true;
            }
        });
    }

    @Override
    public void setItem(BaseItem item) {
        // This view does not use data, but pulls it all from the current character?
    }

    @Override
    public void setupItemView(View itemView) {
        TextView acLabel = (TextView) itemView.findViewById(R.id.armor_class_label);
        acLabel.setText(String.format(getResources().getString(R.string.character_AC),
                CharacterControl.getCurrentCharacter().getAC()));
    }

    @Override
    public void setupInfoView(View infoView) {
        setupItemView(infoView);
    }

    @Override
    public void setupEditView(View editView) {
        setupItemView(editView);
    }

    @Override
    public void resolveEditView(View editView) {

    }

    @Override
    public void remove() {
        // lol nope
    }

    @Override
    public boolean hasTitle() {
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_armor_class;
    }

    @Override
    public int getInfoResourceId() {
        return R.layout.item_armor_class;
    }

    @Override
    public int getEditResourceId() {
        return R.layout.item_armor_class;
    }
}

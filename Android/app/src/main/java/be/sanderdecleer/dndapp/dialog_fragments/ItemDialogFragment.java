package be.sanderdecleer.dndapp.dialog_fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.utils.CharacterControl;
import be.sanderdecleer.dndapp.views.ItemViewController;


/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link ItemDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDialogFragment extends DialogFragment {

    @IntDef({VIEW_TYPE_EDIT, VIEW_TYPE_INFO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    public static final int VIEW_TYPE_INFO = 1;
    public static final int VIEW_TYPE_EDIT = 2;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESOURCE_ID = "resource_id";
    private static final String ARG_VIEW_TYPE = "view_type";
    private static final String ARG_CAN_REMOVE = "can_remove";

    private int resourceId;
    private int viewType;
    private boolean canRemove;

    private ItemViewController viewSetup = null;
    private View contentView = null;

    public static void showItemViewDialog(Context context, ItemViewController viewController,
                                          @ItemDialogFragment.ViewType int viewType) {
        // Get FragmentManager
        final FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        // Switch some data depending on type
        final int resourceId;
        final String tag;
        switch (viewType) {
            default:
            case VIEW_TYPE_INFO:
                resourceId = viewController.getInfoResourceId();
                tag = "info_view";
                break;

            case VIEW_TYPE_EDIT:
                resourceId = viewController.getEditResourceId();
                tag = "edit_view";
                break;
        }

        // Create dialog using inherited resources
        final ItemDialogFragment itemDialogFragment =
                ItemDialogFragment.newInstance(resourceId, viewType, viewController.canRemove());
        itemDialogFragment.setViewSetup(viewController);

        // TODO: 10/04/2017 move to resources or something
        final boolean fullScreen = false;

        // Get fragment transaction
        if (fullScreen) {
            // TODO: 10/04/2017 Add/set a background here
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(android.R.id.content, itemDialogFragment)
                    .addToBackStack(null).commit();
        } else {
            itemDialogFragment.show(fragmentManager, tag);
        }
    }

    public ItemDialogFragment() {
        // Required empty public constructor
    }

    public void setViewSetup(ItemViewController viewSetup) {
        this.viewSetup = viewSetup;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resourceId Resource Id.
     * @param viewType   the type of view, determines setup function called
     *                   (Choose from VIEW_TYPE_INFO or VIEW_TYPE_EDIT)
     * @param canRemove  can the item backing this view be removed
     * @return A new instance of fragment ItemDialogFragment.
     */
    public static ItemDialogFragment newInstance(@LayoutRes int resourceId, @ViewType int viewType,
                                                 boolean canRemove) {
        ItemDialogFragment fragment = new ItemDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESOURCE_ID, resourceId);
        args.putInt(ARG_VIEW_TYPE, viewType);
        args.putBoolean(ARG_CAN_REMOVE, canRemove);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            resourceId = getArguments().getInt(ARG_RESOURCE_ID);
            viewType = getArguments().getInt(ARG_VIEW_TYPE);
            canRemove = getArguments().getBoolean(ARG_CAN_REMOVE);
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_info_dialog, container, false);

        // Inflate child view
        contentView = parentView.findViewById(R.id.info_content);
        inflater.inflate(resourceId, (ViewGroup) contentView);

        if (viewSetup != null) {
            // Handle the title
            TextView title = (TextView) parentView.findViewById(R.id.info_title);
            if (viewSetup.hasTitle()) {
                title.setText(viewSetup.getTitle());
            } else {
                title.setVisibility(View.GONE);
            }

            // Get buttons
            final Button dismissBtn = (Button) parentView.findViewById(R.id.button_dismiss);
            final Button confirmBtn = (Button) parentView.findViewById(R.id.button_positive);
            final Button cancelBtn = (Button) parentView.findViewById(R.id.button_negative);
            final Button removeButton = (Button) parentView.findViewById(R.id.button_remove);

            // Let the setup manage its own content
            // Enable/disable buttons depending on type
            switch (viewType) {
                default:
                case VIEW_TYPE_INFO:
                    // Show dismiss button
                    confirmBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.GONE);
                    dismissBtn.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.GONE);

                    // Hook up buttons
                    dismissBtn.setOnClickListener(dismissClickListener);

                    viewSetup.setupInfoView(contentView);
                    break;
                case VIEW_TYPE_EDIT:
                    // Show cancel and confirm buttons
                    dismissBtn.setVisibility(View.GONE);
                    confirmBtn.setVisibility(View.VISIBLE);
                    cancelBtn.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(canRemove ? View.VISIBLE : View.INVISIBLE);

                    // Hook up buttons
                    cancelBtn.setOnClickListener(dismissClickListener);
                    confirmBtn.setOnClickListener(saveClickListener);
                    removeButton.setOnClickListener(removeClickListener);

                    viewSetup.setupEditView(contentView);
                    break;
            }
        }

        return parentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {

            // Make the dialog fit the content
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners));
        }
    }

    private View.OnClickListener dismissClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private View.OnClickListener saveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (contentView != null)
                viewSetup.resolveEditView(contentView);
            dismiss();
        }
    };

    private View.OnClickListener removeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 13/04/2017 remove this from the character
            viewSetup.remove();
            dismiss();
        }
    };


}

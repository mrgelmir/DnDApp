package be.sanderdecleer.dndapp.dialog_fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.controllers.ItemViewController;
import be.sanderdecleer.dndapp.model.character.BaseItem;
import be.sanderdecleer.dndapp.utils.LayoutFactory;
import be.sanderdecleer.dndapp.views.ItemViewType;


/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link ItemDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDialogFragment extends DialogFragment {

    // the fragment initialization parameters
    private static final String ARG_RESOURCE_ID = "resource_id";
    private static final String ARG_VIEW_TYPE = "view_type";

    private ItemViewType viewType;

    private BaseItem item;

    private ViewGroup contentView;

    private DismissListener dismissListener = null;
    private ConfirmListener confirmListener = null;
    private CancelListener cancelListener = null;
    private RemoveListener removeListener = null;

    /**
     * Helper method for showing an ItemViewDialog
     *
     * @param item     the base item thet will be passed to the LayoutFactory
     * @param viewType The type of view we want to show, this will determine the buttons for now
     *                 TODO: add an internal parameter later for better button control
     */
    public static ItemDialogFragment createItemViewDialog(BaseItem item, ItemViewType viewType) {

        // Set up data
        ItemDialogFragment fragment = new ItemDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIEW_TYPE, viewType.toInt());
        fragment.setArguments(args);
        fragment.setItem(item);


        return fragment;
    }

    @Deprecated
    public static void createItemViewDialog(Context context, ItemViewController viewController,
                                            ItemViewType viewType) {
        // Get FragmentManager
        final FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        // Switch some data depending on type
        final int resourceId;
        final String tag;
        switch (viewType) {
            default:
            case INFO:
                resourceId = viewController.getInfoResourceId();
                tag = "info_view";
                break;

            case EDIT:
                resourceId = viewController.getEditResourceId();
                tag = "edit_view";
                break;
        }

        // Create dialog using inherited resources
        final ItemDialogFragment itemDialogFragment =
                ItemDialogFragment.newInstance(resourceId, viewType, viewController.canRemove());
//        itemDialogFragment.setViewSetup(viewController);

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

    public ItemDialogFragment show(Context context) {

        // TODO: 10/04/2017 get this from resources or something
        final boolean fullScreen = false;

        // Get FragmentManager
        final FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        if (fullScreen) {
            // Get fragment transaction
            // TODO: 10/04/2017 Add/set a background here
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(android.R.id.content, this)
                    .addToBackStack(null).commit();
        } else {
            // Plain old fragment stuff
            this.show(fragmentManager, item.getType().toString());
        }

        return this;
    }

    public ItemDialogFragment setConfirmListener(ItemDialogFragment.ConfirmListener listener) {
        confirmListener = listener;
        return this;
    }

    public ItemDialogFragment setCancelListener(ItemDialogFragment.CancelListener listener) {
        cancelListener = listener;
        return this;
    }

    public ItemDialogFragment setConfirmListener(ItemDialogFragment.RemoveListener listener) {
        removeListener = listener;
        return this;
    }

    public ItemDialogFragment setConfirmListener(ItemDialogFragment.DismissListener listener) {
        dismissListener = listener;
        return this;
    }

    public void setItem(BaseItem item) {
        this.item = item;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resourceId Resource Id.
     * @param viewType   the type of view, determines button layout
     * @param canRemove  can the item backing this view be removed
     * @return A new instance of fragment ItemDialogFragment.
     */
    public static ItemDialogFragment newInstance(@LayoutRes int resourceId, ItemViewType viewType,
                                                 boolean canRemove) {
        ItemDialogFragment fragment = new ItemDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESOURCE_ID, resourceId);
        args.putInt(ARG_VIEW_TYPE, viewType.toInt());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This call is to retain the item reference
        setRetainInstance(true);
        if (getArguments() != null) {
            int resourceId = getArguments().getInt(ARG_RESOURCE_ID);
            viewType = ItemViewType.fromInt(getArguments().getInt(ARG_VIEW_TYPE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_info_dialog, container, false);

        // Inflate child view
        contentView = (ViewGroup) parentView.findViewById(R.id.info_content);
        LayoutFactory.createView(inflater, contentView, item, viewType, true);
//        inflater.inflate(resourceId, (ViewGroup) contentView);


        // Title setup
        // TODO see how to handle this, for now no titles (maybe pass as argument)
        TextView title = (TextView) parentView.findViewById(R.id.info_title);
        title.setVisibility(View.GONE);

        // Get buttons
        final Button dismissBtn = (Button) parentView.findViewById(R.id.button_dismiss);
        final Button confirmBtn = (Button) parentView.findViewById(R.id.button_positive);
        final Button cancelBtn = (Button) parentView.findViewById(R.id.button_negative);
        final Button removeButton = (Button) parentView.findViewById(R.id.button_remove);

        // Hook up buttons
        dismissBtn.setOnClickListener(dismissClickListener);
        cancelBtn.setOnClickListener(cancelClickListener);
        confirmBtn.setOnClickListener(saveClickListener);
        removeButton.setOnClickListener(removeClickListener);

        // Enable/disable buttons  based on listeners
        // TODO might be a flawed system
        confirmBtn.setVisibility(confirmListener == null ? View.GONE : View.VISIBLE);
        cancelBtn.setVisibility(cancelListener == null ? View.GONE : View.VISIBLE);
        removeButton.setVisibility(removeListener == null ? View.GONE : View.VISIBLE);
        // Always show dismiss
        dismissBtn.setVisibility(View.VISIBLE);

//        // Enable/disable buttons depending on viewType
//        switch (viewType) {
//            default:
//            case ITEM:
//            case INFO:
//                // Only show dismiss button
//                confirmBtn.setVisibility(View.GONE);
//                cancelBtn.setVisibility(View.GONE);
//                dismissBtn.setVisibility(View.VISIBLE);
//                removeButton.setVisibility(View.GONE);
//                break;
//            case EDIT:
//                // Show cancel and confirm buttons
//                confirmBtn.setVisibility(View.VISIBLE);
//                dismissBtn.setVisibility(View.GONE);
//                cancelBtn.setVisibility(View.VISIBLE);
//                removeButton.setVisibility(View.VISIBLE);
//                break;
//        }

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
            dialogWindow.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.rounded_corners, null));
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dismissListener != null)
            dismissListener.dismiss();
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
//            if (contentView != null)
//                viewSetup.resolveEditView(contentView);
            if (confirmListener != null)
                confirmListener.confirm(contentView);
            dismiss();
        }
    };

    private View.OnClickListener removeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            viewSetup.remove();
            if (removeListener != null)
                removeListener.remove();
            dismiss();
        }
    };

    private View.OnClickListener cancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            viewSetup.remove();
            if (cancelListener != null)
                cancelListener.cancel();
            dismiss();
        }
    };

    public interface DismissListener {
        void dismiss();
    }

    public interface CancelListener {
        void cancel();
    }

    public interface ConfirmListener {
        void confirm(View v);
    }

    public interface RemoveListener {
        void remove();
    }

}

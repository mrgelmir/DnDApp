package be.sanderdecleer.dndapp.dialog_fragments;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.views.ItemViewController;


/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link InfoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoDialogFragment extends DialogFragment {

    public static final int VIEW_TYPE_INFO = 1;
    public static final int VIEW_TYPE_EDIT = 2;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESOURCE_ID = "resource_id";
    private static final String ARG_VIEW_TYPE = "view_type";

    private int resourceId;
    private int viewType;
    private ItemViewController viewSetup = null;

    public InfoDialogFragment() {
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
     * @return A new instance of fragment InfoDialogFragment.
     */
    public static InfoDialogFragment newInstance(@LayoutRes int resourceId, int viewType) {
        InfoDialogFragment fragment = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESOURCE_ID, resourceId);
        args.putInt(ARG_VIEW_TYPE, viewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resourceId = getArguments().getInt(ARG_RESOURCE_ID);
            viewType = getArguments().getInt(ARG_VIEW_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_info_dialog, container, false);

//        setShowsDialog(false);


        // Inflate child view
        View contentView = parentView.findViewById(R.id.info_content);
        inflater.inflate(resourceId, (ViewGroup) contentView);

        if (viewSetup != null) {
            // Handle the title
            TextView title = (TextView) parentView.findViewById(R.id.info_title);
            if (viewSetup.hasTitle()) {
                title.setText(viewSetup.getTitle());
            } else {
                title.setVisibility(View.GONE);
            }

            // Let the setup setup its own content
            // TODO enable/disable buttons depending on type

            Button dismissBtn;
            Button confirmBtn;
            Button cancelBtn;

            switch (viewType) {
                case VIEW_TYPE_INFO:
                    // Show dismiss button
                    // Set cancel to INVISIBLE instead of gone for layout purposes
                    confirmBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.INVISIBLE);
                    viewSetup.setupInfoView(contentView);
                case VIEW_TYPE_EDIT:
                    // Show cancel and confirm buttons
                    dismissBtn.setVisibility(View.GONE);
                    viewSetup.setupEditView(contentView);
                    break;
            }
        }

        return parentView;
    }

}

package be.sanderdecleer.dndapp.dialog_fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.sanderdecleer.dndapp.R;
import be.sanderdecleer.dndapp.view.BaseItemView;


/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link InfoDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoDialogFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESOURCE_ID = "resource_id";

    private int resourceId;
    private ViewSetup viewSetup = null;

    public InfoDialogFragment() {
        // Required empty public constructor
    }

    public void setViewSetup(ViewSetup viewSetup) {
        this.viewSetup = viewSetup;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resourceId Resource Id.
     * @return A new instance of fragment InfoDialogFragment.
     */
    public static InfoDialogFragment newInstance(int resourceId) {
        InfoDialogFragment fragment = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESOURCE_ID, resourceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resourceId = getArguments().getInt(ARG_RESOURCE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_info_dialog, container, false);


        // Inflate child view
        View contentView = parentView.findViewById(R.id.info_content);
        inflater.inflate(resourceId, (ViewGroup) contentView);

        // TODO set or hide title

        if (viewSetup != null) {
            viewSetup.setupInfoView(contentView);
        }

        return parentView;
    }

    public interface ViewSetup {
        void setupInfoView(View view);
    }
}

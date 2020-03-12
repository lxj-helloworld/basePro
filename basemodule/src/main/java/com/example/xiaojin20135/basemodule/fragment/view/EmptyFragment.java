package com.example.xiaojin20135.basemodule.fragment.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.view.widget.empty.EmptyView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyFragment extends Fragment {

    EmptyView mEmptyView;

    public EmptyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        mEmptyView = view.findViewById(R.id.emptyView);
        return view;
    }

}

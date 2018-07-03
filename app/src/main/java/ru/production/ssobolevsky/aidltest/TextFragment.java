package ru.production.ssobolevsky.aidltest;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {

    private TextView mTextView;
    private IActivityCallback mIActivityCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIActivityCallback = (IActivityCallback) getActivity();
    }

    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTextView = view.findViewById(R.id.tv_text);
        if (mIActivityCallback.getData() != null) {
            mTextView.setText(mIActivityCallback.getData());
        }
    }

    public static final Fragment newInstance() {
        Fragment fragment = new TextFragment();
        return fragment;
    }
}

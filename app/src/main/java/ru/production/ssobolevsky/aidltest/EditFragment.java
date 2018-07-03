package ru.production.ssobolevsky.aidltest;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private EditText mEditText;
    private Button mButton;
    private IActivityCallback mIActivityCallback;
    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIActivityCallback = (IActivityCallback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mEditText = view.findViewById(R.id.et_text);
        mButton = view.findViewById(R.id.btn_send);
        mButton.setOnClickListener(new MyButtonClickListener());
    }

    public static final Fragment newInstance() {
        Fragment fragment = new EditFragment();
        return new EditFragment();
    }

    private class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fl_content, TextFragment.newInstance())
                    .commit();
            mIActivityCallback.setData(mEditText.getText().toString());
        }
    }
}

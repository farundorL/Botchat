package com.farundorl.android.botchat.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.farundorl.android.botchat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountFragment extends Fragment {

    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.name_yomi)
    EditText nameYomi;

    public static final String TAG = AccountFragment.class.getSimpleName();

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

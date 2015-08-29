package com.farundorl.android.botchat.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.farundorl.android.botchat.Adapter.TimeLineAdapter;
import com.farundorl.android.botchat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimeLineFragment extends Fragment {

    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.input)
    EditText input;

    private TimeLineAdapter mAdapter;

    public static TimeLineFragment newInstance() {
        return new TimeLineFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        ButterKnife.bind(this, view);

        initRecycler();
        initSubmit();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initRecycler() {
        mAdapter = new TimeLineAdapter();
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initSubmit() {
        input.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEND) {
                mAdapter.add(input.getText().toString());
                input.setText("");
                return true;
            }
            return false;
        });
    }
}

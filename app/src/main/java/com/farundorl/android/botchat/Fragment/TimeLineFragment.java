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
import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;
import jp.ne.docomo.smt.dev.common.http.AuthApiKey;
import jp.ne.docomo.smt.dev.dialogue.Dialogue;
import jp.ne.docomo.smt.dev.dialogue.data.DialogueResultData;
import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TimeLineFragment extends Fragment {

    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.input)
    EditText input;

    private CompositeSubscription subscriptions;
    private TimeLineAdapter mAdapter;
    private Dialogue mDialogue;
    private DialogueResultData mResult;

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
        initDialogue();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        subscriptions.unsubscribe();
    }

    private void initRecycler() {
        mAdapter = new TimeLineAdapter();
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initSubmit() {
        input.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String message = input.getText().toString();
                sendDialogue(message);
                mAdapter.add(message);
                input.setText("");
                return true;
            }
            return false;
        });
    }

    private void initDialogue() {
        AuthApiKey.initializeAuth(getString(R.string.api_key));
        mDialogue = new Dialogue();
        subscriptions = new CompositeSubscription();
    }

    private void sendDialogue(String message) {
        DialogueRequestParam param = new DialogueRequestParam();
        param.setUtt(message);
        param.setContext(mResult == null ? "hogefugapiyo" : mResult.getContext());

        subscriptions.add(
            Observable.just(param)
                    .observeOn(Schedulers.newThread())
                    .flatMap(this::requestDialogue)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        mResult = result;
                        mAdapter.add(mResult.getUtt());
                    }, e -> {
                        mAdapter.add(e.getMessage());
                    })
        );

    }

    private Observable<DialogueResultData> requestDialogue(DialogueRequestParam param) {
        try {
            return Observable.just(mDialogue.request(param));
        } catch (SdkException e) {
            return Observable.error(new Throwable("SDKエラー", e.getCause()));
        } catch (ServerException e) {
            return Observable.error(new Throwable("サーバーエラー", e.getCause()));
        } catch (Exception e) {
            return Observable.error(new Throwable("なんでやろエラー", e.getCause()));
        }
    }
}

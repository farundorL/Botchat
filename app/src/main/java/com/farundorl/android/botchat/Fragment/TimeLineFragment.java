package com.farundorl.android.botchat.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.farundorl.android.botchat.Adapter.TimeLineAdapter;
import com.farundorl.android.botchat.Helper.SharedPreferencesHelper;
import com.farundorl.android.botchat.Model.Message;
import com.farundorl.android.botchat.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;
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

    @State
    TimeLineAdapter mAdapter;

    private CompositeSubscription subscriptions;
    private Dialogue mDialogue;
    private DialogueResultData mResult;
    private SharedPreferencesHelper mPrefs;

    public static final String TAG = TimeLineFragment.class.getSimpleName();

    public static TimeLineFragment newInstance() {
        return new TimeLineFragment();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
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

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void initRecycler() {
        mAdapter = (mAdapter == null) ? new TimeLineAdapter() : mAdapter;
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initSubmit() {
        input.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            String message = input.getText().toString();

            if (!TextUtils.isEmpty(message) && actionId == EditorInfo.IME_ACTION_SEND) {
                sendDialogue(message);
                updateTimeLine(message, Message.MessageFrom.ME);
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
        mPrefs = new SharedPreferencesHelper(getActivity());
    }

    private void sendDialogue(String message) {
        DialogueRequestParam param = mPrefs.getDialogueParam();
        param.setUtt(message);
        param.setContext(mResult == null ? "" : mResult.getContext());

        subscriptions.add(
                Observable.just(param)
                        .observeOn(Schedulers.newThread())
                        .flatMap(this::requestDialogue)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            mResult = result;
                            updateTimeLine(mResult.getUtt(), Message.MessageFrom.BOT);
                        }, e -> {
                            Snackbar.make(recycler, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        })
        );

    }

    private Observable<DialogueResultData> requestDialogue(DialogueRequestParam param) {
        try {
            return Observable.just(mDialogue.request(param));
        } catch (SdkException e) {
            return Observable.error(new Throwable(getString(R.string.error_sdk), e.getCause()));
        } catch (ServerException e) {
            return Observable.error(new Throwable(getString(R.string.error_server), e.getCause()));
        } catch (Exception e) {
            return Observable.error(new Throwable(getString(R.string.error), e.getCause()));
        }
    }

    private void updateTimeLine(String message, Message.MessageFrom from) {
        mAdapter.add(new Message(message, from));
        recycler.scrollToPosition(mAdapter.getItemCount() - 1);
    }

}

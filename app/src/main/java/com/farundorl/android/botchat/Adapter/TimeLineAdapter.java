package com.farundorl.android.botchat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.farundorl.android.botchat.Model.Message;
import com.farundorl.android.botchat.R;

import java.util.ArrayList;
import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter {

    private List<Message> timeline;

    public TimeLineAdapter() {
        timeline = new ArrayList<>();
    }

    public void add(Message message) {
        timeline.add(message);
        notifyItemInserted(timeline.size() - 1);
    }

    public void addAll(List<Message> items) {
        timeline.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        timeline.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MessageViewHolder(inflater.inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder) holder).bind(timeline.get(position));
    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

        public void bind(Message message) {
            boolean from = message.getFrom() == Message.MessageFrom.ME;
            textView.setText(message.getMessage());
            textView.setBackgroundResource(from ? R.drawable.message_green : R.drawable.message_gray);
            ((FrameLayout.LayoutParams) textView.getLayoutParams()).gravity = (from ? Gravity.RIGHT : Gravity.LEFT);
        }

    }

}

package com.farundorl.android.botchat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farundorl.android.botchat.R;

import java.util.ArrayList;
import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter {

    private List<String> timeline;

    public TimeLineAdapter() {
        timeline = new ArrayList<>();
    }

    public void add(String message) {
        timeline.add(message);
        notifyItemInserted(timeline.size() - 1);
    }

    public void addAll(List<String> messages) {
        timeline.addAll(messages);
        notifyDataSetChanged();
    }

    public void clear() {
        timeline.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(View.inflate(parent.getContext(), R.layout.item_message, null));
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

        public void bind(String message) {
            textView.setText(message);
        }

    }

}

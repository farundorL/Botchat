package com.farundorl.android.botchat.Adapter;

import android.os.Parcel;
import android.os.Parcelable;
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

public class TimeLineAdapter extends RecyclerView.Adapter implements Parcelable {

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

    public List<Message> get() {
        return timeline;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(timeline);
    }

    protected TimeLineAdapter(Parcel in) {
        this.timeline = in.createTypedArrayList(Message.CREATOR);
    }

    public static final Parcelable.Creator<TimeLineAdapter> CREATOR = new Parcelable.Creator<TimeLineAdapter>() {
        public TimeLineAdapter createFromParcel(Parcel source) {
            return new TimeLineAdapter(source);
        }

        public TimeLineAdapter[] newArray(int size) {
            return new TimeLineAdapter[size];
        }
    };
}

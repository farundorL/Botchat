package com.farundorl.android.botchat.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    public enum MessageFrom {
        ME,
        BOT
    }

    private String message;
    private MessageFrom from;

    public Message(String message, MessageFrom from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageFrom getFrom() {
        return from;
    }

    public void setFrom(MessageFrom from) {
        this.from = from;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.from == null ? -1 : this.from.ordinal());
    }

    protected Message(Parcel in) {
        this.message = in.readString();
        int tmpFrom = in.readInt();
        this.from = tmpFrom == -1 ? null : MessageFrom.values()[tmpFrom];
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}

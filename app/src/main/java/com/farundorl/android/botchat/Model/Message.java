package com.farundorl.android.botchat.Model;

public class Message {

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
}

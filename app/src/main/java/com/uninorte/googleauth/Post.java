package com.uninorte.googleauth;

import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by dbain on 25/10/2016.
 */

@IgnoreExtraProperties
public class Post {
    public String nick;
    public String message;

    public Post() {
    }

    public Post(String nick, String message) {
        this.nick = nick;
        this.message = message;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.tinmoc.reflekt;

import java.io.Console;

/**
 * Created by M3800 on 2/24/2018.
 */

public class Message {
    private String content,username;

    public Message()
    {

    }
    public Message(String content, String username)
    {
        this.content = content;
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

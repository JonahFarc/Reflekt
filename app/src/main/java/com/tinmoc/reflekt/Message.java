package com.tinmoc.reflekt;

/**
 * Created by M3800 on 2/24/2018.
 */

public class Message {
    private String content;

    public Message()
    {

    }
    public Message(String content)
    {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

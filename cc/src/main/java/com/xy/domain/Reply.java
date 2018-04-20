package com.xy.domain;

import org.springframework.http.HttpStatus;

public class Reply {

    private Object content;

    public Reply(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

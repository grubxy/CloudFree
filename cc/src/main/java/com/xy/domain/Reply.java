package com.xy.domain;

import org.springframework.http.HttpStatus;

public class Reply {

    private int status = HttpStatus.OK.value();

    private Object content;

    public Reply(Object content) {
        this.content = content;
    }

    public Reply(int status, String content) {
        this.status = status;
        this.content = content;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

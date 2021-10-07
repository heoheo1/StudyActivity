package com.hj.studyactivity;

import android.net.Uri;

public class Item {
    String title;
    String contents;
    String uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Item(String title, String contents, String uri) {
        this.title = title;
        this.contents = contents;
        this.uri =uri;
    }
}

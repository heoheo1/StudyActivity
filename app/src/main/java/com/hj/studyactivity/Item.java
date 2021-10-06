package com.hj.studyactivity;

import android.net.Uri;

public class Item {
    String title;
    String contents;
    Uri uri;

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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Item(String title, String contents, Uri uri) {
        this.title = title;
        this.contents = contents;
        this.uri =uri;
    }
}

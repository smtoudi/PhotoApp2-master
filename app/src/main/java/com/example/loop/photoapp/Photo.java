package com.example.loop.photoapp;

import android.net.Uri;

/**
 * Created by loop on 25/02/17.
 */

public class Photo {

    private String description = null;
    private Uri uri;

    public Photo(String description, Uri uri) {
        this.description = description;
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public Uri getUri() {
        return uri;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return description != null ? description.equals(photo.description) : photo.description == null;

    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "description='" + description + '\'' +
                ", uri=" + uri +
                '}';
    }
}

package com.example.standard.popularmoviesstageone;

/**
 * Created by vince on 06.06.2017.
 */

public class IntentKeys {
    public static final String TITLE_KEY = "title";
    public static final String IMAGE_KEY = "image";
    public static final String STORY_KEY = "story";
    public static final String RATING_KEY = "rating";
    public static final String DATE_KEY = "date";

    public boolean detailLayout;

    public boolean isDetailLayout() {
        return detailLayout;
    }

    public void setDetailLayout(boolean detailLayout) {
        this.detailLayout = detailLayout;
    }
}

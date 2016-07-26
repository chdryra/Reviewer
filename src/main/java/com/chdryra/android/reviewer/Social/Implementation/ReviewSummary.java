/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSummary {
    private String mSubject;
    private float mRating;
    private ArrayList<String> mHeadlines;
    private ArrayList<String> mTags;
    private ArrayList<String> mLocations;

    public ReviewSummary(String subject, float rating, ArrayList<String>
            headlines, ArrayList<String> tags, ArrayList<String> locations) {
        mSubject = subject;
        mRating = rating;
        mHeadlines = headlines;
        mTags = tags;
        mLocations = locations;
    }

    public String getSubject() {
        return mSubject;
    }

    public float getRating() {
        return mRating;
    }

    public ArrayList<String> getHeadlines() {
        return mHeadlines;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public ArrayList<String> getLocations() {
        return mLocations;
    }
}

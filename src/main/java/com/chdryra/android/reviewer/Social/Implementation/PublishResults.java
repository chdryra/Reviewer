/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishResults {
    private String mPublisherName;
    private int mFollowers;
    private String mErrorIfFail;
    private boolean mResult;

    public PublishResults(String publisherName, int followers) {
        mPublisherName = publisherName;
        mFollowers = followers;
        mResult = true;
        mErrorIfFail = "";
    }

    public PublishResults(String publisherName, String errorIfFail) {
        mPublisherName = publisherName;
        mErrorIfFail = errorIfFail;
        mResult = false;
    }

    public String getPublisherName() {
        return mPublisherName;
    }

    public int getFollowers() {
        return mFollowers;
    }

    public String getErrorIfFail() {
        return mErrorIfFail;
    }

    public boolean isSuccessful() {
        return mResult;
    }
}

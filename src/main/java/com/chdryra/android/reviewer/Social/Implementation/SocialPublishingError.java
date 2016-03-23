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
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingError {
    private String mMessage;
    private boolean mIsError = false;

    public static SocialPublishingError none() {
        return new SocialPublishingError();
    }

    public static SocialPublishingError error(String message) {
        return new SocialPublishingError(message);
    }

    private SocialPublishingError() {
    }

    private SocialPublishingError(String message) {
        mMessage = message;
        mIsError = true;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isError() {
        return mIsError;
    }
}

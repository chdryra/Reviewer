/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RepositoryError {
    private String mMessage;
    private boolean mIsError = false;

    public static RepositoryError none() {
        return new RepositoryError();
    }

    public static RepositoryError error(String message) {
        return new RepositoryError(message);
    }

    private RepositoryError() {
    }

    private RepositoryError(String message) {
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

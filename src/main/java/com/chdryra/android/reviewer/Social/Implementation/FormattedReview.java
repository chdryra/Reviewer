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
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FormattedReview {
    private final String mTitle;
    private final String mBody;

    public FormattedReview(String title, String body) {
        mTitle = title;
        mBody = body;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }
}

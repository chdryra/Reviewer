/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.startouch.Application.Interfaces.SocialSuite;
import com.chdryra.android.startouch.Social.Implementation.PublisherAndroid;
import com.chdryra.android.startouch.Social.Implementation.ReviewSummariser;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class SocialSuiteAndroid implements SocialSuite {
    private final SocialPlatformList mList;
    private final ReviewSummariser mSummariser;
    private final ReviewFormatter mFormatter;

    private Activity mActivity;

    public SocialSuiteAndroid(SocialPlatformList list,
                              ReviewSummariser summariser,
                              ReviewFormatter formatter) {
        mList = list;
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public SocialPlatformList getSocialPlatforms() {
        return mList;
    }

    @Override
    public SocialPublisher newPublisher() {
        return new PublisherAndroid(mActivity, mSummariser, mFormatter);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }
}

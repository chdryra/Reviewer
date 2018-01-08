/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Application.Interfaces.SocialSuite;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Implementation.NullSocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.Social.Implementation.PublisherAndroid;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class SocialSuiteAndroid implements SocialSuite {
    private SocialPlatformList mList;
    private UserSession mSession;
    private Activity mActivity;

    public SocialSuiteAndroid(SocialPlatformList list) {
        mList = list;
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mList;
    }

    @Override
    public ProfileSocial getSocialProfile() {
        return mSession != null ? mSession.getAccount().getSocialProfile() : new NullSocialProfile();
    }

    @Override
    public SocialPublisher newPublisher() {
        return new PublisherAndroid(mActivity, new ReviewSummariser(), new ReviewFormatterTwitter());
    }

    public void setSession(UserSession session) {
        mSession = session;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }
}

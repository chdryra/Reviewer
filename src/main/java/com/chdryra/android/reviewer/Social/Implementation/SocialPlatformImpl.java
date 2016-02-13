/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public class SocialPlatformImpl implements SocialPlatform {
    private Context mContext;
    private final SocialPublisher mPublisher;

    public SocialPlatformImpl(Context context, SocialPublisher publisher) {
        mContext = context;
        mPublisher = publisher;
    }

    @Override
    public String getName() {
        return mPublisher.getName();
    }

    @Override
    public int getFollowers() {
        return mPublisher.getFollowers(mContext);
    }

    @Override
    public SocialPublisher getPublisher() {
        return mPublisher;
    }
}

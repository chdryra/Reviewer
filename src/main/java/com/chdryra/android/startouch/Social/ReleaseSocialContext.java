/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social;

import android.content.Context;

import com.chdryra.android.startouch.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.startouch.Social.Factories.FactorySocialPlatformList;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseSocialContext implements SocialContext {

    private final SocialPlatformList mPlatforms;

    public ReleaseSocialContext(Context context) {
        mPlatforms = new FactorySocialPlatformList(context).getPlatforms();
    }

    @Override
    public SocialPlatformList getSocialPlatforms() {
        return mPlatforms;
    }
}

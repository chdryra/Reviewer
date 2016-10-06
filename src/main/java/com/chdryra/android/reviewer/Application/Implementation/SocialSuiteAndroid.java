/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.SocialSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Implementation.NullSocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class SocialSuiteAndroid implements SocialSuite {
    private SocialPlatformList mList;
    private UserSession mSession;
    private TagsManager mManager;

    public SocialSuiteAndroid(SocialPlatformList list, TagsManager manager) {
        mList = list;
        mManager = manager;
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mList;
    }

    @Override
    public SocialProfile getSocialProfile() {
        return mSession != null ? mSession.getSocialProfile() : new NullSocialProfile();
    }

    @Override
    public TagsManager getTagsManager() {
        return mManager;
    }

    public void setSession(UserSession session) {
        mSession = session;
    }
}

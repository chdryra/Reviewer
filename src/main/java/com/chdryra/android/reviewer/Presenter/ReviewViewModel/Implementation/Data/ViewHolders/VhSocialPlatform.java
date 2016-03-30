/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.FollowersListener;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .GVSocialPlatformList.GvSocialPlatform}. Shows
 * platform name
 * above, number followers below.
 */
public class VhSocialPlatform extends VhDualText implements FollowersListener{
    private String mName;

    @Override
    public void updateView(ViewHolderData data) {
        GvSocialPlatform platform = (GvSocialPlatform) data;
        mName = platform.getName();
        onNumberFollowers(0);

        if(platform.isAuthorised()) {
            super.updateView(mName, platform.getPlaceHolder());
            platform.getFollowers(this);
        }
    }

    @Override
    public void onNumberFollowers(int followers) {
        super.updateView(mName, String.valueOf(followers));
    }
}
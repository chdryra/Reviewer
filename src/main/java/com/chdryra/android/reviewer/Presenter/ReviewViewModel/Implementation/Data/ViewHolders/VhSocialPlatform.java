/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .GVSocialPlatformList.GvSocialPlatform}. Shows
 * platform name
 * above, number followers below.
 */
public class VhSocialPlatform extends VhDualText {
    private String mName;

    @Override
    public void updateView(ViewHolderData data) {
        GvSocialPlatform platform = (GvSocialPlatform) data;
        mName = platform.getName();

        if(platform.isAuthorised()) {
            super.updateView(mName, "connected");
        } else {
            super.updateView(mName, "not connected");
        }
    }
}
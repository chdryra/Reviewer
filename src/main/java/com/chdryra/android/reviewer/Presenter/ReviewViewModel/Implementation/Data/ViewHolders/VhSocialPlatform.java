/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .GVSocialPlatformList.GvSocialPlatform}. Shows
 * platform name
 * above, number followers below.
 */
public class VhSocialPlatform extends VhDualText {
    //Overridden
    @Override
    public void updateView(ViewHolderData data) {
        GvSocialPlatform platform = (GvSocialPlatform)
                data;
        if (platform != null) {
            super.updateView(platform.getName(), String.valueOf(platform.getFollowers()));
        }
    }
}
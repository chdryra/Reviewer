/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .GVSocialPlatformList.GvSocialPlatform}. Shows
 * platform name
 * above, number followers below.
 */
public class VhSocialPlatform extends VhDualText implements GvSocialPlatform.PlatformStateListener {
    private static final String SHARE = Strings.Publish.SHARE;
    private static final String CONNECTED = Strings.Publish.CONNECTED;
    private static final String NOT_CONNECTED = Strings.Publish.NOT_CONNECTED;

    @Override
    public void updateView(ViewHolderData data) {
        ((GvSocialPlatform) data).setListener(this);
    }

    @Override
    public void onPlatformStateChange(GvSocialPlatform platform) {
        String name = platform.getName();
        if(platform.isAuthorised()) {
            if(platform.isChosen()) {
                getView().setActivated(true);
                super.updateView(platform.getName(), SHARE);
            } else {
                getView().setActivated(false);
                super.updateView(platform.getName(), CONNECTED);
            }

        } else {
            super.updateView(name, NOT_CONNECTED);
        }
    }
}
/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ButtonActionNone;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 08/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class ShareScreenShare extends ButtonActionNone<GvSocialPlatform> {
    public ShareScreenShare(String title) {
        super(title);
    }

    @Override
    public void onClick(View v) {
        getCurrentScreen().showToast("Private ratings under construction...");
    }
}

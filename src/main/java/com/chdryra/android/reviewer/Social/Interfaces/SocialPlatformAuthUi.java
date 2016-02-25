/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import android.content.Intent;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPlatformAuthUi extends ActivityResultListener {
    void launchUi();

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent data);
}

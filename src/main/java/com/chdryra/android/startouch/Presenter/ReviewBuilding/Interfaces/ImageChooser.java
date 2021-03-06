/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces;

import android.content.Intent;

import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 12/11/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface ImageChooser {
    interface ImageChooserListener {
        void onChosenImage(GvImage image);
    }

    Intent getChooserIntents();

    boolean chosenImageExists(ActivityResultCode resultCode, Intent data);

    void getChosenImage(final ImageChooserListener listener);
}

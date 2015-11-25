/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 November, 2014
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;

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

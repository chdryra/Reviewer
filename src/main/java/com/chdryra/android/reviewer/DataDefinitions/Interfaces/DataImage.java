/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 December, 2014
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataImage extends HasReviewId, Validatable{
    @Nullable
    Bitmap getBitmap();

    @Nullable
    DataDate getDate();

    @Nullable
    String getCaption();

    boolean isCover();

    @Override
    ReviewId getReviewId();

    @Override
    boolean hasData(DataValidator dataValidator);
}

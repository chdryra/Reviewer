/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataImage extends HasReviewId, Validatable{
    Bitmap getBitmap();

    DataDate getDate();

    String getCaption();

    boolean isCover();

    @Override
    ReviewId getReviewId();

    @Override
    boolean hasData(DataValidator dataValidator);
}

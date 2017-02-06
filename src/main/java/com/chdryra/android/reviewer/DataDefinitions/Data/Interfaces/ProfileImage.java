/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface ProfileImage extends HasAuthorId, Validatable{
    String TYPE_NAME = "profileImage";

    Bitmap getBitmap();

    @Override
    boolean hasData(DataValidator dataValidator);
}

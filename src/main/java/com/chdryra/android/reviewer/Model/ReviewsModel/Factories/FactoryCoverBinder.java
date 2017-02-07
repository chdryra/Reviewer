/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.CoverBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 07/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryCoverBinder {
    public CoverBinder newCoverBinder(DataReference<ProfileImage> profileImage,
                                      Bitmap placeholder,
                                      CoverBinder.CoverListener listener) {
        return new CoverBinder(profileImage, placeholder, listener);
    }
}

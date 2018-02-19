/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Interfaces;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataSize extends HasReviewId, Size {
    String TYPE_NAME = "size";

    @Override
    int getSize();

    @Override
    ReviewId getReviewId();
}

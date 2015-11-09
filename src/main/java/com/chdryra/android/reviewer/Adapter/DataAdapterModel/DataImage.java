/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 December, 2014
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataImage extends DataReview, Validatable{
    //abstract
    Bitmap getBitmap();

    Date getDate();

    String getCaption();

    boolean isCover();

    @Override
    boolean hasData(DataValidator dataValidator);
}

/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface CoversManager {
    GvImageList getCovers(GvReviewId id);
}

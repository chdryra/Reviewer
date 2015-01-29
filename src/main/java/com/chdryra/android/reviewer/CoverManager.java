/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 29 January, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 29/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface CoverManager {
    public void updateCover(FragmentViewReview fragment);

    public void proposeCover(GvImageList.GvImage image);
}

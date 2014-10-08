/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Version of ReviewNode that allows children to be added/removed and parents to be assigned.
 */
public interface ReviewNodeExpandable extends ReviewNode {
    void setParent(ReviewNodeExpandable parentNode);

    void addChild(Review child);

    void addChild(ReviewNodeExpandable childNode);

    void removeChild(ReviewNodeExpandable childNode);

    void clearChildren();
}

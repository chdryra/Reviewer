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
public interface ReviewNodeExpandable extends ReviewNode {
    public void setParent(ReviewNodeExpandable parentTree);

    public void addChild(Review child);

    public void addChild(ReviewNodeExpandable childNode);

    public void removeChild(ReviewNodeExpandable childNode);

    public void clearChildren();
}

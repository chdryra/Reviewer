/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 June, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.View.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewId {
    public static ReviewId nextId() {
        return ReviewId.generateId(RandomAuthor.nextAuthor());
    }

    public static String nextIdString() {
        return nextId().toString();
    }

    public static GvReviewId nextGvReviewId() {
        return GvReviewId.getId(nextIdString());
    }
}

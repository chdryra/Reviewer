/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.RDComment;
import com.chdryra.android.reviewer.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RDataMocker {
    private static final RandomStringGenerator STRING_GENERATOR = new RandomStringGenerator();
    private static final Review                REVIEW           = ReviewMocker.newReview();

    private RDataMocker() {
    }

    public static RDComment newComment() {
        String comment = STRING_GENERATOR.nextParagraph();
        return new RDComment(comment, REVIEW);
    }

//
}

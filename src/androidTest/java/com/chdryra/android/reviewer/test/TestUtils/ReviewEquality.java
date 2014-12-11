/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.MdToGvConverter;
import com.chdryra.android.reviewer.Review;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEquality {
    public static void checkData(Review lhs, Review rhs) {
        Assert.assertEquals(lhs.getSubject().get(), rhs.getSubject().get());
        Assert.assertEquals(lhs.getRating().get(), rhs.getRating().get());
        MdGvEquality.check(lhs.getComments(), MdToGvConverter.convert(rhs.getComments()));
        MdGvEquality.check(lhs.getFacts(), MdToGvConverter.convert(rhs.getFacts()));
        MdGvEquality.check(lhs.getImages(), MdToGvConverter.convert(rhs.getImages()));
        MdGvEquality.check(lhs.getLocations(), MdToGvConverter.convert(rhs.getLocations()));
        MdGvEquality.check(lhs.getUrls(), MdToGvConverter.convert(rhs.getUrls()));
    }

    public static void checkDataIncPublish(Review lhs, Review rhs) {
        Assert.assertEquals(lhs.isPublished(), rhs.isPublished());
        Assert.assertEquals(lhs.getPublishDate(), rhs.getPublishDate());
        Assert.assertEquals(lhs.getAuthor(), rhs.getAuthor());
        checkData(lhs, rhs);
    }
}

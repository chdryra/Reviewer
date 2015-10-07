/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.ReviewData.MdData;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataUtils {
    //Static methods
    public static void testEqualsHash(MdData lhs, MdData rhs, boolean result) {
        if (result) {
            Assert.assertTrue(lhs.equals(rhs));
            Assert.assertTrue(lhs.hashCode() == rhs.hashCode());
        } else {
            Assert.assertFalse(lhs.equals(rhs));
            Assert.assertFalse(lhs.hashCode() == rhs.hashCode());
        }
    }
}

/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.testutils.RandomStringGenerator;

import junit.framework.Assert;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 11/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildrenMocker {
    private static final int NUMDATA = 50;

    public static void checkEquality(RCollectionReview<ReviewNode> children, GvChildrenList
            list) {
        Assert.assertEquals(children.size(), list.size());
        for (int i = 0; i < children.size(); ++i) {
            Assert.assertEquals(children.getItem(i).getSubject().get(),
                    list.getItem(i).getSubject());
            Assert.assertEquals(children.getItem(i).getRating().get(), list.getItem(i).getRating());
        }
    }

    public static void checkEquality(GvChildrenList children, GvChildrenList list) {
        Assert.assertEquals(children.size(), list.size());
        for (int i = 0; i < children.size(); ++i) {
            Assert.assertEquals(children.getItem(i).getSubject(), list.getItem(i).getSubject());
            Assert.assertEquals(children.getItem(i).getRating(), list.getItem(i).getRating());
        }
    }

    public static RCollectionReview<ReviewNode> getMockNodeChildren() {
        RCollectionReview<ReviewNode> children = new RCollectionReview<>();
        for (int i = 0; i < NUMDATA; ++i) {
            ReviewNode child = ReviewMocker.newReviewNode();
            children.add(child);
        }

        return children;
    }

    public static GvChildrenList getMockGvChildren() {
        Random rand = new Random();
        GvChildrenList children = new GvChildrenList();
        for (int i = 0; i < NUMDATA; ++i) {
            children.add(new GvChildrenList.GvChildReview(RandomStringGenerator.nextWord()
                    , rand.nextFloat() * 5));
        }

        return children;
    }
}

package com.chdryra.android.reviewer.test.TestUtils;

import junit.framework.Assert;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorTester<T> {
    private Comparator<T> mComparator;

    public ComparatorTester(Comparator<T> comparator) {
        mComparator = comparator;
    }

    public void testEquals(T lhs, T rhs) {
        Assert.assertEquals(0, mComparator.compare(lhs, rhs));
        Assert.assertEquals(0, mComparator.compare(rhs, lhs));
    }

    public void testFirstSecond(T first, T second) {
        Assert.assertTrue(mComparator.compare(first, second) < 0);
        Assert.assertEquals(mComparator.compare(first, second), -mComparator.compare(second,
                first));
    }
}

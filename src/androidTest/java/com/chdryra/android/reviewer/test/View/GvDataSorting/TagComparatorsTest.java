package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.GvDataSorting.TagComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagComparatorsTest extends ComparatorCollectionTest<GvTag> {

    //Constructors
    public TagComparatorsTest() {
        super(TagComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvTag> comparator = mComparators.getDefault();
        GvTag A = new GvTag("A");
        GvTag A2 = new GvTag("A");
        GvTag a = new GvTag("a");
        GvTag B = new GvTag("B");
        GvTag C = new GvTag("C");

        ComparatorTester<GvTag> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}


package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataSorting.TagComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagComparatorsTest extends ComparatorCollectionTest<GvTagList.GvTag> {

    //Constructors
    public TagComparatorsTest() {
        super(TagComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvTagList.GvTag> comparator = mComparators.getDefault();
        GvTagList.GvTag A = new GvTagList.GvTag("A");
        GvTagList.GvTag A2 = new GvTagList.GvTag("A");
        GvTagList.GvTag a = new GvTagList.GvTag("a");
        GvTagList.GvTag B = new GvTagList.GvTag("B");
        GvTagList.GvTag C = new GvTagList.GvTag("C");

        ComparatorTester<GvTagList.GvTag> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}


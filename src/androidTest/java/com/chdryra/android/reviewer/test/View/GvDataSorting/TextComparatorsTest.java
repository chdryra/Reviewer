package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvText;
import com.chdryra.android.reviewer.View.GvDataSorting.TextComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TextComparatorsTest extends ComparatorCollectionTest<GvText> {

    public TextComparatorsTest() {
        super(TextComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvText> comparator = mComparators.getDefault();
        GvText A = new GvText("A");
        GvText A2 = new GvText("A");
        GvText a = new GvText("a");
        GvText B = new GvText("B");
        GvText C = new GvText("C");

        ComparatorTester<GvText> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}


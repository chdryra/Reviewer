package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.CommentComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentComparatorsTest extends ComparatorCollectionTest<GvComment> {
    //Constructors
    public CommentComparatorsTest() {
        super(CommentComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvComment> comparator = mComparators.getDefault();

        GvComment comment1 = new GvComment("A", false);
        GvComment comment12 = new GvComment("A", false);
        GvComment comment2 = new GvComment("A", true);
        GvComment comment3 = new GvComment("a", true);
        GvComment comment4 = new GvComment("B", true);
        GvComment comment5 = new GvComment("B", false);

        ComparatorTester<GvComment> tester = new ComparatorTester<>(comparator);
        tester.testEquals(comment1, comment1);
        tester.testEquals(comment1, comment12);
        tester.testEquals(comment2, comment3);
        tester.testFirstSecond(comment2, comment1);
        tester.testFirstSecond(comment4, comment1);
        tester.testFirstSecond(comment2, comment4);
        tester.testFirstSecond(comment1, comment5);
    }
}

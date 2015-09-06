package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataSorting.CommentComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentComparatorsTest extends ComparatorCollectionTest<GvCommentList.GvComment> {
    public CommentComparatorsTest() {
        super(CommentComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvCommentList.GvComment> comparator = mComparators.getDefault();

        GvCommentList.GvComment comment1 = new GvCommentList.GvComment("A", false);
        GvCommentList.GvComment comment12 = new GvCommentList.GvComment("A", false);
        GvCommentList.GvComment comment2 = new GvCommentList.GvComment("A", true);
        GvCommentList.GvComment comment3 = new GvCommentList.GvComment("a", true);
        GvCommentList.GvComment comment4 = new GvCommentList.GvComment("B", true);
        GvCommentList.GvComment comment5 = new GvCommentList.GvComment("B", false);

        ComparatorTester<GvCommentList.GvComment> tester = new ComparatorTester<>(comparator);
        tester.testEquals(comment1, comment1);
        tester.testEquals(comment1, comment12);
        tester.testEquals(comment2, comment3);
        tester.testFirstSecond(comment2, comment1);
        tester.testFirstSecond(comment4, comment1);
        tester.testFirstSecond(comment2, comment4);
        tester.testFirstSecond(comment1, comment5);
    }
}

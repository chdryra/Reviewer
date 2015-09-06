package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataSorting.AuthorComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorComparatorsTest extends ComparatorCollectionTest<GvAuthorList.GvAuthor> {
    public AuthorComparatorsTest() {
        super(AuthorComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvAuthorList.GvAuthor> comparator = mComparators.getDefault();
        GvAuthorList.GvAuthor A = new GvAuthorList.GvAuthor("A", UserId.generateId().toString());
        GvAuthorList.GvAuthor A2 = new GvAuthorList.GvAuthor("A", UserId.generateId().toString());
        GvAuthorList.GvAuthor a = new GvAuthorList.GvAuthor("a", UserId.generateId().toString());
        GvAuthorList.GvAuthor B = new GvAuthorList.GvAuthor("B", UserId.generateId().toString());
        GvAuthorList.GvAuthor C = new GvAuthorList.GvAuthor("C", UserId.generateId().toString());

        ComparatorTester<GvAuthorList.GvAuthor> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}

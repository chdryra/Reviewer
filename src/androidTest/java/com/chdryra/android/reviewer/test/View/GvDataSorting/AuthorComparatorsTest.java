package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Models.UserModel.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvAuthor;
import com.chdryra.android.reviewer.View.GvDataSorting.AuthorComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorComparatorsTest extends ComparatorCollectionTest<GvAuthor> {
    //Constructors
    public AuthorComparatorsTest() {
        super(AuthorComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvAuthor> comparator = mComparators.getDefault();
        GvAuthor A = new GvAuthor("A", UserId.generateId().toString());
        GvAuthor A2 = new GvAuthor("A", UserId.generateId().toString());
        GvAuthor a = new GvAuthor("a", UserId.generateId().toString());
        GvAuthor B = new GvAuthor("B", UserId.generateId().toString());
        GvAuthor C = new GvAuthor("C", UserId.generateId().toString());

        ComparatorTester<GvAuthor> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}

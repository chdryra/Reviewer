package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Algorithms.DataSorting.AuthorComparators;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
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
        GvAuthor A = new GvAuthor("A", AuthorId.generateId().toString());
        GvAuthor A2 = new GvAuthor("A", AuthorId.generateId().toString());
        GvAuthor a = new GvAuthor("a", AuthorId.generateId().toString());
        GvAuthor B = new GvAuthor("B", AuthorId.generateId().toString());
        GvAuthor C = new GvAuthor("C", AuthorId.generateId().toString());

        ComparatorTester<GvAuthor> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}

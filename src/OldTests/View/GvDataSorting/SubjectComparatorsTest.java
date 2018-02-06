package com.chdryra.android.startouch.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Algorithms.DataSorting.SubjectComparators;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.startouch.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectComparatorsTest extends ComparatorCollectionTest<GvSubject> {

    //Constructors
    public SubjectComparatorsTest() {
        super(SubjectComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvSubject> comparator = mComparators.getDefault();
        GvSubject A = new GvSubject("A");
        GvSubject A2 = new GvSubject("A");
        GvSubject a = new GvSubject("a");
        GvSubject B = new GvSubject("B");
        GvSubject C = new GvSubject("C");

        ComparatorTester<GvSubject> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}

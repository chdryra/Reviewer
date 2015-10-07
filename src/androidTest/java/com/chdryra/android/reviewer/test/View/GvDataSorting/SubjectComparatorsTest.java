package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataSorting.SubjectComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectComparatorsTest extends ComparatorCollectionTest<GvSubjectList.GvSubject> {

    //Constructors
    public SubjectComparatorsTest() {
        super(SubjectComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvSubjectList.GvSubject> comparator = mComparators.getDefault();
        GvSubjectList.GvSubject A = new GvSubjectList.GvSubject("A");
        GvSubjectList.GvSubject A2 = new GvSubjectList.GvSubject("A");
        GvSubjectList.GvSubject a = new GvSubjectList.GvSubject("a");
        GvSubjectList.GvSubject B = new GvSubjectList.GvSubject("B");
        GvSubjectList.GvSubject C = new GvSubjectList.GvSubject("C");

        ComparatorTester<GvSubjectList.GvSubject> tester = new ComparatorTester<>(comparator);
        tester.testEquals(A, A);
        tester.testEquals(A, A2);
        tester.testEquals(A, a);
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }
}

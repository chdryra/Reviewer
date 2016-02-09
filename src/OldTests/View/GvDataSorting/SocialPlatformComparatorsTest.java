package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Algorithms.DataSorting.SocialPlatformComparators;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformComparatorsTest extends ComparatorCollectionTest<GvSocialPlatform> {

    //Constructors
    public SocialPlatformComparatorsTest() {
        super(SocialPlatformComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvSocialPlatform> comparator = mComparators.getDefault();

        String A = "A";
        String a = "a";
        String Z = "Z";
        int followers10 = 10;
        int followers100 = 100;

        GvSocialPlatform p1 = new GvSocialPlatform(A,
                followers10);
        GvSocialPlatform p12 = new GvSocialPlatform(A,
                followers10);
        GvSocialPlatform p2 = new GvSocialPlatform(A,
                followers100);
        GvSocialPlatform p3 = new GvSocialPlatform(Z,
                followers100);

        ComparatorTester<GvSocialPlatform> tester = new ComparatorTester<>
                (comparator);
        tester.testEquals(p1, p1);
        tester.testEquals(p1, p12);
        tester.testFirstSecond(p2, p1);
        tester.testFirstSecond(p3, p1);
        tester.testFirstSecond(p2, p3);
    }
}

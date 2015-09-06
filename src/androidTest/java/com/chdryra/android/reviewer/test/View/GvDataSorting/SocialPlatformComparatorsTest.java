package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataSorting.SocialPlatformComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformComparatorsTest extends ComparatorCollectionTest<GvSocialPlatformList
        .GvSocialPlatform> {

    public SocialPlatformComparatorsTest() {
        super(SocialPlatformComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvSocialPlatformList.GvSocialPlatform> comparator = mComparators.getDefault();

        String A = "A";
        String a = "a";
        String Z = "Z";
        int followers10 = 10;
        int followers100 = 100;

        GvSocialPlatformList.GvSocialPlatform p1 = new GvSocialPlatformList.GvSocialPlatform(A,
                followers10);
        GvSocialPlatformList.GvSocialPlatform p12 = new GvSocialPlatformList.GvSocialPlatform(A,
                followers10);
        GvSocialPlatformList.GvSocialPlatform p2 = new GvSocialPlatformList.GvSocialPlatform(A,
                followers100);
        GvSocialPlatformList.GvSocialPlatform p3 = new GvSocialPlatformList.GvSocialPlatform(Z,
                followers100);

        ComparatorTester<GvSocialPlatformList.GvSocialPlatform> tester = new ComparatorTester<>
                (comparator);
        tester.testEquals(p1, p1);
        tester.testEquals(p1, p12);
        tester.testFirstSecond(p2, p1);
        tester.testFirstSecond(p3, p1);
        tester.testFirstSecond(p2, p3);
    }
}

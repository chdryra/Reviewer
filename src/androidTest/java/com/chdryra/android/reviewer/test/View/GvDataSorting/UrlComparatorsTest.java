package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.GvDataSorting.UrlComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlComparatorsTest extends ComparatorCollectionTest<GvUrlList.GvUrl> {

    //Constructors
    public UrlComparatorsTest() {
        super(UrlComparators.getComparators());
    }

    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvUrlList.GvUrl> comparator = mComparators.getDefault();

        GvUrlList.GvUrl AA = newUrl("A", "A");
        GvUrlList.GvUrl AA2 = newUrl("A", "A");
        GvUrlList.GvUrl Aa = newUrl("A", "a");
        GvUrlList.GvUrl aA = newUrl("a", "A");
        GvUrlList.GvUrl aa = newUrl("a", "a");
        GvUrlList.GvUrl AZ = newUrl("A", "Z");
        GvUrlList.GvUrl BC = newUrl("B", "C");
        GvUrlList.GvUrl CA = newUrl("C", "A");

        ComparatorTester<GvUrlList.GvUrl> tester = new ComparatorTester<>(comparator);
        tester.testEquals(AA, AA);
        tester.testEquals(AA, AA2);
        tester.testEquals(AA, Aa);
        tester.testEquals(AA, aA);
        tester.testEquals(AA, aa);
        tester.testFirstSecond(AA, AZ);
        tester.testFirstSecond(AZ, BC);
        tester.testFirstSecond(BC, CA);
        tester.testFirstSecond(AA, CA);
    }

    private GvUrlList.GvUrl newUrl(String label, String value) {
        String urlString = "http://www." + value + ".co.uk";

        try {
            URL url = new URL(urlString);
            return new GvUrlList.GvUrl(label, url);
        } catch (MalformedURLException e) {
            fail();
            return new GvUrlList.GvUrl();
        }
    }
}

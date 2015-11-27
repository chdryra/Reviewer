package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.DataSorting.UrlComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlComparatorsTest extends ComparatorCollectionTest<GvUrl> {

    //Constructors
    public UrlComparatorsTest() {
        super(UrlComparators.getComparators());
    }

    @SmallTest
    public void testGetDefaultComparator() {
        Comparator<GvUrl> comparator = mComparators.getDefault();

        GvUrl AA = newUrl("A", "A");
        GvUrl AA2 = newUrl("A", "A");
        GvUrl Aa = newUrl("A", "a");
        GvUrl aA = newUrl("a", "A");
        GvUrl aa = newUrl("a", "a");
        GvUrl AZ = newUrl("A", "Z");
        GvUrl BC = newUrl("B", "C");
        GvUrl CA = newUrl("C", "A");

        ComparatorTester<GvUrl> tester = new ComparatorTester<>(comparator);
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

    private GvUrl newUrl(String label, String value) {
        String urlString = "http://www." + value + ".co.uk";

        try {
            URL url = new URL(urlString);
            return new GvUrl(label, url);
        } catch (MalformedURLException e) {
            fail();
            return new GvUrl();
        }
    }
}

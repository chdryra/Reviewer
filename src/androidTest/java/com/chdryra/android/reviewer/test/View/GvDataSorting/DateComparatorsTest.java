package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.DataSorting.DateComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;

import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateComparatorsTest extends ComparatorCollectionTest<GvDate> {
    //Constructors
    public DateComparatorsTest() {
        super(DateComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvDate> comparator = mComparators.getDefault();

        GvDate date1 = new GvDate(new GregorianCalendar(2015, 2, 25, 19, 30).getTime());
        GvDate date12 = new GvDate(new GregorianCalendar(2015, 2, 25, 19, 30).getTime());
        GvDate date2 = new GvDate(new GregorianCalendar(2015, 2, 25, 19, 20).getTime());
        GvDate date3 = new GvDate(new GregorianCalendar(2015, 2, 25, 18, 30).getTime());
        GvDate date4 = new GvDate(new GregorianCalendar(2015, 2, 24, 19, 30).getTime());
        GvDate date5 = new GvDate(new GregorianCalendar(2015, 1, 25, 19, 30).getTime());
        GvDate date6 = new GvDate(new GregorianCalendar(2014, 2, 25, 19, 30).getTime());

        ComparatorTester<GvDate> tester = new ComparatorTester<>(comparator);
        tester.testEquals(date1, date1);
        tester.testEquals(date1, date12);
        tester.testFirstSecond(date1, date2);
        tester.testFirstSecond(date2, date3);
        tester.testFirstSecond(date3, date4);
        tester.testFirstSecond(date4, date5);
        tester.testFirstSecond(date5, date6);
        tester.testFirstSecond(date1, date6);
    }
}

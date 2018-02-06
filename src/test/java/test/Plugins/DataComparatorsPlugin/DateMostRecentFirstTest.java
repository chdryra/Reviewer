/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DateComparator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import org.junit.Test;

import java.util.Date;
import java.util.Random;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DateMostRecentFirstTest extends ComparatorTest<DateTime>{
    private static final Random RAND = new Random();

    public DateMostRecentFirstTest() {
        super(new DateComparator());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        long first = new Date().getTime();
        long second = first + 100l;
        long third = second + 100l;
        DateTime date1 = new DatumDate(RandomReviewId.nextReviewId(), third);
        DateTime date2 = new DatumDate(RandomReviewId.nextReviewId(), second);
        DateTime date3 = new DatumDate(RandomReviewId.nextReviewId(), first);

        ComparatorTester<DateTime> tester = newComparatorTester();
        tester.testFirstSecond(date1, date2);
        tester.testFirstSecond(date2, date3);
        tester.testFirstSecond(date1, date3);
    }

    @Test
    public void comparatorEquality() {
        long time = new Date().getTime();
        DateTime date1 = new DatumDate(RandomReviewId.nextReviewId(), time);
        DateTime date2 = new DatumDate(RandomReviewId.nextReviewId(), time);

        ComparatorTester<DateTime> tester = newComparatorTester();
        tester.testEquals(date1, date1);
        tester.testEquals(date1, date2);
    }
}

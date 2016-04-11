/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DateMostRecentFirst;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;

import org.junit.Test;

import java.util.Date;
import java.util.Random;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DateMostRecentFirstTest extends ComparatorTest<DataDate>{
    private static final Random RAND = new Random();

    public DateMostRecentFirstTest() {
        super(new DateMostRecentFirst());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        long first = new Date().getTime();
        long second = first + 100l;
        long third = second + 100l;
        DataDate date1 = new DatumDateReview(RandomReviewId.nextReviewId(), third);
        DataDate date2 = new DatumDateReview(RandomReviewId.nextReviewId(), second);
        DataDate date3 = new DatumDateReview(RandomReviewId.nextReviewId(), first);

        ComparatorTester<DataDate> tester = newComparatorTester();
        tester.testFirstSecond(date1, date2);
        tester.testFirstSecond(date2, date3);
        tester.testFirstSecond(date1, date3);
    }

    @Test
    public void comparatorEquality() {
        long time = new Date().getTime();
        DataDate date1 = new DatumDateReview(RandomReviewId.nextReviewId(), time);
        DataDate date2 = new DatumDateReview(RandomReviewId.nextReviewId(), time);

        ComparatorTester<DataDate> tester = newComparatorTester();
        tester.testEquals(date1, date1);
        tester.testEquals(date1, date2);
    }
}

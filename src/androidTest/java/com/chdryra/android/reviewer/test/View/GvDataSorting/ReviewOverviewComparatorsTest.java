package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.DataSorting.ReviewOverviewComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewOverviewComparatorsTest extends ComparatorCollectionTest<GvReviewOverview> {

    //Constructors
    public ReviewOverviewComparatorsTest() {
        super(ReviewOverviewComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparator() {
        Comparator<GvReviewOverview> comparator = mComparators.getDefault();

        Date date1 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();
        Date date12 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();
        Date date2 = new GregorianCalendar(2015, 2, 25, 19, 20).getTime();
        Date date3 = new GregorianCalendar(2015, 2, 25, 18, 30).getTime();
        Date date4 = new GregorianCalendar(2015, 2, 24, 19, 30).getTime();
        Date date5 = new GregorianCalendar(2015, 1, 25, 19, 30).getTime();
        Date date6 = new GregorianCalendar(2014, 2, 25, 19, 30).getTime();

        GvReviewOverview review1 = getReview(date1);
        GvReviewOverview review12 = getReview(date12);
        GvReviewOverview review2 = getReview(date2);
        GvReviewOverview review3 = getReview(date3);
        GvReviewOverview review4 = getReview(date4);
        GvReviewOverview review5 = getReview(date5);
        GvReviewOverview review6 = getReview(date6);

        ComparatorTester<GvReviewOverview> tester = new ComparatorTester<>
                (comparator);
        tester.testEquals(review1, review1);
        tester.testEquals(review1, review12);
        tester.testFirstSecond(review1, review2);
        tester.testFirstSecond(review2, review3);
        tester.testFirstSecond(review3, review4);
        tester.testFirstSecond(review4, review5);
        tester.testFirstSecond(review5, review6);
        tester.testFirstSecond(review1, review6);
    }

    private GvReviewOverview getReview(Date publishDate) {
        return new GvReviewOverview(RandomReviewId.nextIdString(),
                RandomAuthor.nextAuthor(), publishDate, RandomString.nextWord(),
                RandomRating.nextRating(), BitmapMocker.nextBitmap(), RandomString.nextSentence(),
                new ArrayList<String>(), new ArrayList<String>());
    }
}

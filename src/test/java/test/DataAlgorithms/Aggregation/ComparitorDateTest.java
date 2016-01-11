package test.DataAlgorithms.Aggregation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorDate;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceDate;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorDateTest {
    private ComparitorDate mComparitor;
    private static final DifferenceDate DAY = new DifferenceDate(DifferenceDate.DateBucket.DAY);
    private static final DifferenceDate MONTH = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
    private static final DifferenceDate YEAR= new DifferenceDate(DifferenceDate.DateBucket.YEAR);
    private static final DifferenceDate MORE_THAN_YEAR
            = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);

    @Before
    public void setUp() {
        mComparitor = new ComparitorDate();
    }

    @Test
    public void sameDayDifferentTimeReturnsDifferenceDateOfDayOrLess() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2015, 10, 25, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(true));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(true));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(true));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void differentDayLessThan24HoursLaterReturnsDifferenceDateGreaterThanDay() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2015, 10, 26, 18, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(true));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(true));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void differentDayLessThanMonthReturnsDifferenceDateGreaterThanDayLessThanMonth() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2015, 10, 29, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(true));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(true));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void nextMonthLessThanMonthLaterReturnsDifferenceDateGreaterThanMonthLessThanYear() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2015, 11, 2, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(false));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(true));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void nextMonthGreaterThanMonthLaterReturnsDifferenceDateGreaterThanMonthLessThanYear() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2015, 11, 27, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(false));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(true));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void differentMonthReturnsDifferenceDateGreaterThanMonthLessThanYear() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 5, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2015, 10, 27, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(false));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(true));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void nextYearLessThanYearLaterReturnsDifferenceDateGreaterThanYearLessThanMoreThanYear() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2016, 1, 1, 19, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(false));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(false));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void nextYearMoreThanYearLaterReturnsDifferenceDateGreaterThanYearLessThanMoreThanYear() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 10, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2016, 11, 26, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(false));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(false));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }

    @Test
    public void differentYearReturnsDifferenceDateGreaterThanYearLessThanMoreThanYear() {
        GregorianCalendar lhsCal = new GregorianCalendar(2015, 1, 25, 19, 15);
        GregorianCalendar rhsCal = new GregorianCalendar(2116, 1, 25, 20, 15);
        DataDate lhs = new DatumDateReview(RandomReviewId.nextReviewId(), lhsCal.getTimeInMillis());
        DataDate rhs = new DatumDateReview(RandomReviewId.nextReviewId(), rhsCal.getTimeInMillis());

        DifferenceDate calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(DAY), is(false));
        assertThat(calculated.lessThanOrEqualTo(MONTH), is(false));
        assertThat(calculated.lessThanOrEqualTo(YEAR), is(false));
        assertThat(calculated.lessThanOrEqualTo(MORE_THAN_YEAR), is(true));
    }
}

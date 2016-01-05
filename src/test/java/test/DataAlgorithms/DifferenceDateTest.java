package test.DataAlgorithms;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceDate;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceDateTest {
    @Test
    public void daySameAsDay() {
        DifferenceDate day1 = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceDate day2 = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        assertThat(day1.lessThanOrEqualTo(day2), is(true));
        assertThat(day2.lessThanOrEqualTo(day1), is(true));
    }

    @Test
    public void monthSameAsMonth() {
        DifferenceDate month1 = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        DifferenceDate month2 = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        assertThat(month1.lessThanOrEqualTo(month2), is(true));
        assertThat(month2.lessThanOrEqualTo(month1), is(true));
    }

    @Test
    public void yearSameAsYear() {
        DifferenceDate Year1 = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        DifferenceDate Year2 = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        assertThat(Year1.lessThanOrEqualTo(Year2), is(true));
        assertThat(Year2.lessThanOrEqualTo(Year1), is(true));
    }

    @Test
    public void moreThanYearSameAsMoreThanYear() {
        DifferenceDate MoreThanYear1 = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);
        DifferenceDate MoreThanYear2 = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);
        assertThat(MoreThanYear1.lessThanOrEqualTo(MoreThanYear2), is(true));
        assertThat(MoreThanYear2.lessThanOrEqualTo(MoreThanYear1), is(true));
    }

    @Test
    public void dayLessThanMonthAndMonthGreaterThanDay() {
        DifferenceDate day = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceDate month = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        assertThat(day.lessThanOrEqualTo(month), is(true));
        assertThat(month.lessThanOrEqualTo(day), is(false));
    }

    @Test
    public void dayLessThanYearAndYearGreaterThanDay() {
        DifferenceDate day = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceDate year = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        assertThat(day.lessThanOrEqualTo(year), is(true));
        assertThat(year.lessThanOrEqualTo(day), is(false));
    }

    @Test
    public void dayLessThanMoreThanYearAndMoreThanYearGreaterThanDay() {
        DifferenceDate day = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceDate moreThanYear = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);
        assertThat(day.lessThanOrEqualTo(moreThanYear), is(true));
        assertThat(moreThanYear.lessThanOrEqualTo(day), is(false));
    }

    @Test
    public void monthLessThanYearAndYearGreaterThanMonth() {
        DifferenceDate month = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        DifferenceDate year = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        assertThat(month.lessThanOrEqualTo(year), is(true));
        assertThat(year.lessThanOrEqualTo(month), is(false));
    }

    @Test
    public void monthLessThanMoreThanYearAndMoreThanYearGreaterThanMonth() {
        DifferenceDate month = new DifferenceDate(DifferenceDate.DateBucket.MONTH);
        DifferenceDate moreThanYear = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);
        assertThat(month.lessThanOrEqualTo(moreThanYear), is(true));
        assertThat(moreThanYear.lessThanOrEqualTo(month), is(false));
    }

    @Test
    public void yearLessThanMoreThanYearAndMoreThanYearGreaterThanYear() {
        DifferenceDate year = new DifferenceDate(DifferenceDate.DateBucket.YEAR);
        DifferenceDate moreThanYear = new DifferenceDate(DifferenceDate.DateBucket.MORE_THAN_YEAR);
        assertThat(year.lessThanOrEqualTo(moreThanYear), is(true));
        assertThat(moreThanYear.lessThanOrEqualTo(year), is(false));
    }
}

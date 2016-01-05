package test.DataAlgorithms;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ComparitorString;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorLevenshteinDistance;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferencePercentage;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
/** Internally uses LevenshteinDistance. These values below are from JavaDoc of the function.
 * StringUtils.getLevenshteinDistance("","")               = 0
 * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
 * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
 * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
 */
public class ComparitorStringTest {
    private ComparitorString mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparitorLevenshteinDistance();
    }

    @Test
    public void zeroLengthStringsReturnDifferencePercentageOfZero() {
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare("", "");
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void singleLetterDifferenceReturnsExpectedDifference() {
        String lhs = "hello";
        String rhs = "hallo";
        DifferencePercentage expected = new DifferencePercentage(1./(double)lhs.length());
        DifferencePercentage calculated1 = mComparitor.compare(lhs, rhs);
        DifferencePercentage calculated2 = mComparitor.compare(rhs, lhs);
        assertThat(calculated1.lessThanOrEqualTo(expected), is(true));
        assertThat(calculated2.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void elephantHippoLetterDifferenceReturnsExpectedDifference() {
        String lhs = "elephant";
        String rhs = "hippo";
        DifferencePercentage expected = new DifferencePercentage(7./(double)lhs.length());
        DifferencePercentage calculated1 = mComparitor.compare(lhs, rhs);
        DifferencePercentage calculated2 = mComparitor.compare(rhs, lhs);
        assertThat(calculated1.lessThanOrEqualTo(expected), is(true));
        assertThat(calculated2.lessThanOrEqualTo(expected), is(true));
    }
}

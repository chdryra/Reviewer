package test.Plugins.DataAggregationPlugin;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorFactLabel;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorLevenshteinDistance;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorFactLabelTest {
    private ComparitorFactLabel mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparitorFactLabel(new ComparitorLevenshteinDistance());
    }

    @Test
    public void zeroDifferenceForSameFact() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        DataFact lhs = new DatumFact(RandomReviewId.nextReviewId(), label, value);
        DataFact rhs = new DatumFact(RandomReviewId.nextReviewId(), label, value);

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void zeroDifferenceRegardlessOfCaseForSameLabelDifferentValues() {
        String label = RandomString.nextWord();
        String valueLhs = RandomString.nextWord();
        String valueRhs = RandomString.nextWord();
        DataFact lhs =
                new DatumFact(RandomReviewId.nextReviewId(), label.toUpperCase(), valueLhs);
        DataFact rhs =
                new DatumFact(RandomReviewId.nextReviewId(), label.toLowerCase(), valueRhs);

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfDifferentLabelsButSameValues() {
        String labelLhs = RandomString.nextWord();
        String labelRhs = RandomString.nextWord();
        String value = RandomString.nextWord();
        DataFact lhs =
                new DatumFact(RandomReviewId.nextReviewId(), labelLhs, value);
        DataFact rhs =
                new DatumFact(RandomReviewId.nextReviewId(), labelRhs, value);

        DifferencePercentage zeroDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(zeroDifference), is(false));
    }

    @Test
    public void someDifferenceIfDifferentFacts() {
        String labelLhs = RandomString.nextWord();
        String labelRhs = RandomString.nextWord();
        String valueLhs = RandomString.nextWord();
        String valueRhs = RandomString.nextWord();
        DataFact lhs =
                new DatumFact(RandomReviewId.nextReviewId(), labelLhs, valueLhs);
        DataFact rhs =
                new DatumFact(RandomReviewId.nextReviewId(), labelRhs, valueRhs);

        DifferencePercentage zeroDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(zeroDifference), is(false));
    }
}

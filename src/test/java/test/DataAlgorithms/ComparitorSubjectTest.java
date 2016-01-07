package test.DataAlgorithms;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorSubject;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .ComparitorLevenshteinDistance;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .DifferencePercentage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
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
public class ComparitorSubjectTest {
    private ComparitorSubject mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparitorSubject(new ComparitorLevenshteinDistance());
    }

    @Test
    public void zeroDifferenceForSameSubject() {
        String subject = RandomString.nextSentence();
        DataSubject lhs = new DatumSubject(RandomReviewId.nextReviewId(), subject);
        DataSubject rhs = new DatumSubject(RandomReviewId.nextReviewId(), subject);
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void zeroDifferenceRegardlessOfCaseForSameSubject() {
        String subject = RandomString.nextSentence();
        DataSubject lhs = new DatumSubject(RandomReviewId.nextReviewId(), subject.toUpperCase());
        DataSubject rhs = new DatumSubject(RandomReviewId.nextReviewId(), subject.toLowerCase());
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfDifferentSubjects() {
        String subjectLhs = RandomString.nextSentence();
        String subjectRhs = RandomString.nextSentence();
        DataSubject lhs = new DatumSubject(RandomReviewId.nextReviewId(), subjectLhs);
        DataSubject rhs = new DatumSubject(RandomReviewId.nextReviewId(), subjectRhs);
        DifferencePercentage noDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(noDifference), is(false));
    }
}
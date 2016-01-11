package test.DataAlgorithms.Aggregation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .ComparitorCriterion;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorCriterionTest {
    private ComparitorCriterion mComparitor;
    private String mSubjectLhs;
    private String mSubjectRhs;
    private float mRatingLhs;
    private float mRatingRhs;

    @Before
    public void setUp() {
        mComparitor = new ComparitorCriterion();
        mSubjectLhs = RandomString.nextWord();
        mSubjectRhs = RandomString.nextWord();
        mRatingLhs = RandomRating.nextRating();
        mRatingRhs = RandomRating.nextRating();
    }

    @Test
    public void sameSubjectSameRatingReturnsDifferenceBooleanOfFalse() {
        DataCriterion lhs = new Criterion(mSubjectLhs, mRatingLhs);
        DataCriterion rhs = new Criterion(mSubjectLhs, mRatingLhs);

        checkDifference(lhs, rhs, false);
    }

    @Test
    public void sameSubjectDifferentRatingReturnsDifferenceBooleanOfTrue() {
        DataCriterion lhs = new Criterion(mSubjectLhs, mRatingLhs);
        DataCriterion rhs = new Criterion(mSubjectLhs, mRatingRhs);

        checkDifference(lhs, rhs, true);
    }

    @Test
    public void differentSubjectSameRatingReturnsDifferenceBooleanOfTrue() {
        DataCriterion lhs = new Criterion(mSubjectLhs, mRatingLhs);
        DataCriterion rhs = new Criterion(mSubjectRhs, mRatingLhs);

        checkDifference(lhs, rhs, true);
    }

    @Test
    public void differentSubjectDifferentRatingReturnsDifferenceBooleanOfTrue() {
        DataCriterion lhs = new Criterion(mSubjectLhs, mRatingLhs);
        DataCriterion rhs = new Criterion(mSubjectRhs, mRatingRhs);

        checkDifference(lhs, rhs, true);
    }

    private void checkDifference(DataCriterion lhs, DataCriterion rhs, boolean hasDifference) {
        DifferenceBoolean expected = new DifferenceBoolean(hasDifference);
        DifferenceBoolean calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    private class Criterion implements DataCriterion {
        private ReviewId mId;
        private String mSubject;
        private float mRating;

        public Criterion(String subject, float rating) {
            mId = RandomReviewId.nextReviewId();
            mSubject = subject;
            mRating = rating;
        }

        @Override
        public String getSubject() {
            return mSubject;
        }

        @Override
        public float getRating() {
            return mRating;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public boolean hasData(DataValidator validator) {
            return true;
        }
    }
}

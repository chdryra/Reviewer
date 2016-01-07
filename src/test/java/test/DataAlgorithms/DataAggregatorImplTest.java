package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .DataAggregatorImpl;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceLevel;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorImplTest {
    private static final int MIN_INT = 1;
    private static final int MAX_INT = 10;
    private static final int NUM_DATA = 100;
    private static final Random RAND = new Random();
    private IntegerComparitor mComparitor;
    private CanonicalInt mCanonical;

    @Before
    public void setUp() {
        mComparitor = new IntegerComparitor();
        mCanonical = new CanonicalInt();
    }

    @Test
    public void allDataWithinMaxIntDistance() {
        IdableList<ReviewInteger> data = getData();
        IntegerDifference diff = new IntegerDifference(MAX_INT);
        DataAggregatorImpl<ReviewInteger, IntegerDifference> aggregator = newAggregator(diff);
        AggregatedList<ReviewInteger> results = aggregator.aggregate(data);
        assertThat(results.size(), is(1));
    }

    @NonNull
    private DataAggregatorImpl<ReviewInteger, IntegerDifference> newAggregator(IntegerDifference
                                                                                           diff) {
        return new DataAggregatorImpl<>(mComparitor, diff, mCanonical);
    }

    private IdableList<ReviewInteger> getData() {
        IdableList<ReviewInteger> data = new IdableDataList<>(RandomReviewId.nextReviewId());
        for(int i = 0; i < NUM_DATA; ++i) {
            data.add(new ReviewInteger(RandomReviewId.nextReviewId(), newInt()));
        }

        return data;
    }

    private int newInt() {
        return MIN_INT + RAND.nextInt(MAX_INT);
    }

    private int getAverageInt(IdableList<? extends ReviewInteger> data) {
        float average = 0f;
        for (ReviewInteger datum : data) {
            average += datum.getInt() / (double) data.size();
        }
        return Math.round(average);
    }

    private class ReviewInteger implements HasReviewId {
        private int mInt;
        private ReviewId mId;

        public ReviewInteger(ReviewId id, int anInt) {
            mInt = anInt;
            mId = id;
        }

        public int getInt() {
            return mInt;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }
    }

    private class CanonicalInt implements CanonicalDatumMaker<ReviewInteger> {
        @Override
        public ReviewInteger getCanonical(IdableList<? extends ReviewInteger> data) {
            return new ReviewInteger(data.getReviewId(), getAverageInt(data));
        }
    }

    private class IntegerComparitor implements DifferenceComparitor<ReviewInteger,
            IntegerDifference> {
        @Override
        public IntegerDifference compare(ReviewInteger lhs, ReviewInteger rhs) {
            return new IntegerDifference(Math.abs(lhs.getInt() - rhs.getInt()));
        }
    }

    private class IntegerDifference implements DifferenceLevel<IntegerDifference> {
        private int mInt;

        public IntegerDifference(int anInt) {
            mInt = anInt;
        }

        @Override
        public boolean lessThanOrEqualTo(@NotNull IntegerDifference differenceThreshold) {
            return mInt <= differenceThreshold.mInt;
        }
    }
}

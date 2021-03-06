/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Aggregation.DifferenceComparator;
import com.chdryra.android.corelibrary.Aggregation.DifferenceLevel;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.AggregatedData;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.DataAggregatorImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorImplTest {
    private static final int MIN_INT = 1;
    private static final int MAX_INT = 10;
    private static final int NUM_DATA = 100;
    private IntegerComparator mComparitor;
    private CanonicalInt mCanonical;

    @Before
    public void setUp() {
        mComparitor = new IntegerComparator();
        mCanonical = new CanonicalInt();
    }

    @Test
    public void allDataWithinMaxIntDistanceAggregatesToOneGroupOfNumDataSize() {
        IdableList<ReviewInteger> data = getData();
        IntegerDifference diff = new IntegerDifference(MAX_INT);
        DataAggregatorImpl<ReviewInteger, IntegerDifference> aggregator = newAggregator(diff);

        AggregatedList<ReviewInteger> results = aggregator.aggregate(data);

        checkAggregates(data, results, 1, diff);
    }

    @Test
    public void allDataWithinHalfMaxMinusOneIntDistanceAggregatesToTwoGroupsOfEqualSize() {
        IdableList<ReviewInteger> data = getData();
        IntegerDifference diff = new IntegerDifference(MAX_INT / 2 - 1); //because lessThanOrEqualTo
        DataAggregatorImpl<ReviewInteger, IntegerDifference> aggregator = newAggregator(diff);

        AggregatedList<ReviewInteger> results = aggregator.aggregate(data);

        checkAggregates(data, results, 2, diff);
    }

    @Test
    public void allDataWithinZeroDistanceAggregatesToMaxIntGroupsOfEqualSize() {
        IdableList<ReviewInteger> data = getData();
        IntegerDifference diff = new IntegerDifference(0);
        DataAggregatorImpl<ReviewInteger, IntegerDifference> aggregator = newAggregator(diff);

        AggregatedList<ReviewInteger> results = aggregator.aggregate(data);

        checkAggregates(data, results, MAX_INT, diff);
    }

    @Test
    public void allDataWithinOneDistanceAggregatesToHalfMaxIntGroupsOfEqualSize() {
        IdableList<ReviewInteger> data = getData();
        IntegerDifference diff = new IntegerDifference(1);
        DataAggregatorImpl<ReviewInteger, IntegerDifference> aggregator = newAggregator(diff);

        AggregatedList<ReviewInteger> results = aggregator.aggregate(data);

        checkAggregates(data, results, MAX_INT / 2, diff);
    }

    @Test
    public void allDataWithinCertainDistanceAggregatesToNGroupsThatAddUpToNumData() {
        IdableList<ReviewInteger> data = getData();
        int size = MIN_INT + new Random().nextInt(MAX_INT - 1);
        IntegerDifference diff = new IntegerDifference(size);
        DataAggregatorImpl<ReviewInteger, IntegerDifference> aggregator = newAggregator(diff);

        AggregatedList<ReviewInteger> results = aggregator.aggregate(data);

        int numAggregates = (int) Math.ceil((double) MAX_INT / ((double) size + 1));
        assertThat(results.size(), is(numAggregates));
        int totalSize = 0;
        for (int i = 0; i < results.size(); ++i) {
            AggregatedData<ReviewInteger> aggregate = results.get(i);
            totalSize += aggregate.getAggregatedItems().size();
            checkAggregateWithinExpectedSimilarity(aggregate, diff);
        }
        assertThat(totalSize, is(NUM_DATA));
    }

    private IdableList<ReviewInteger> getData() {
        IdableList<ReviewInteger> data = new IdableDataList<>(RandomReviewId.nextReviewId());
        for (int i = 0; i < NUM_DATA; ++i) {
            data.add(new ReviewInteger(RandomReviewId.nextReviewId(), MIN_INT + i % MAX_INT));
        }

        return data;
    }

    private void checkAggregates(IdableList<ReviewInteger> data,
                                 AggregatedList<ReviewInteger> results,
                                 int numAggregatesExpected,
                                 IntegerDifference expectedDifference) {
        assertThat(results.size(), is(numAggregatesExpected));
        int totalSize = 0;
        for (int i = 0; i < results.size(); ++i) {
            AggregatedData<ReviewInteger> aggregate = results.get(i);
            totalSize = checkAggregateSize(aggregate, numAggregatesExpected, totalSize);
            assertThat(aggregate.getReviewId(), is(data.getReviewId()));
            checkAggregateWithinExpectedSimilarity(aggregate, expectedDifference);
        }
        assertThat(totalSize, is(NUM_DATA));
    }

    private int checkAggregateSize(AggregatedData<ReviewInteger> aggregate, int
            numAggregatesExpected, int totalSize) {
        int groupSize = aggregate.getAggregatedItems().size();
        assertThat(groupSize, is(NUM_DATA / numAggregatesExpected));
        totalSize += groupSize;
        return totalSize;
    }

    private void checkAggregateWithinExpectedSimilarity(AggregatedData<ReviewInteger> aggregate,
                                                        IntegerDifference expectedDifference) {
        ReviewInteger canonical = aggregate.getCanonical();
        IdableList<ReviewInteger> aggregates = aggregate.getAggregatedItems();
        assertThat(canonical.getReviewId(), is(aggregate.getReviewId()));
        assertThat(canonical.getInt(), is(getAverageInt(aggregates)));
        for (int j = 0; j < aggregates.size(); ++j) {
            IntegerDifference diff = mComparitor.compare(canonical, aggregates.get(j));
            assertThat(diff.lessThanOrEqualTo(expectedDifference), is(true));
        }
    }

    @NonNull
    private DataAggregatorImpl<ReviewInteger, IntegerDifference> newAggregator(IntegerDifference
                                                                                       diff) {
        return new DataAggregatorImpl<>(mComparitor, diff, mCanonical);
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

    private class IntegerComparator implements DifferenceComparator<ReviewInteger,
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

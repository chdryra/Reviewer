/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.corelibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorCriterionSubject;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLevenshteinDistance;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorCriterionSubjectTest {
    private ComparatorCriterionSubject mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparatorCriterionSubject(new ComparatorLevenshteinDistance());
    }

    @Test
    public void zeroDifferenceForSameCriterion() {
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion lhs = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);
        DataCriterion rhs = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void zeroDifferenceRegardlessOfCaseForSameSubjectDifferentRatings() {
        String subject = RandomString.nextWord();
        float ratingLhs = RandomRating.nextRating();
        float ratingRhs = RandomRating.nextRating();
        DataCriterion lhs =
                new DatumCriterion(RandomReviewId.nextReviewId(), subject.toUpperCase(), ratingLhs);
        DataCriterion rhs =
                new DatumCriterion(RandomReviewId.nextReviewId(), subject.toLowerCase(), ratingRhs);

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfDifferentCriteria() {
        String subjectLhs = RandomString.nextWord();
        String subjectRhs = RandomString.nextWord();
        float ratingLhs = RandomRating.nextRating();
        float ratingRhs = RandomRating.nextRating();
        DataCriterion lhs =
                new DatumCriterion(RandomReviewId.nextReviewId(), subjectLhs, ratingLhs);
        DataCriterion rhs =
                new DatumCriterion(RandomReviewId.nextReviewId(), subjectRhs, ratingRhs);

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(false));
    }
}

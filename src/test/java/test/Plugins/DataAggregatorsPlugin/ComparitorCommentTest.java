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
        .DataAggregationDefault.Implementation.ComparatorComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLevenshteinDistance;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
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
public class ComparitorCommentTest {
    private ComparatorComment mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparatorComment(new ComparatorLevenshteinDistance());
    }

    @Test
    public void zeroDifferenceForSameComment() {
        String comment = RandomString.nextSentence();
        DataComment lhs = new DatumComment(RandomReviewId.nextReviewId(), comment, false);
        DataComment rhs = new DatumComment(RandomReviewId.nextReviewId(), comment, true);
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void zeroDifferenceRegardlessOfCaseForSameComment() {
        String comment = RandomString.nextSentence();
        DataComment lhs = new DatumComment(RandomReviewId.nextReviewId(), comment.toUpperCase(),
                false);
        DataComment rhs = new DatumComment(RandomReviewId.nextReviewId(), comment.toLowerCase(),
                true);
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfDifferentComments() {
        String commentLhs = RandomString.nextSentence();
        String commentRhs = RandomString.nextSentence();
        DataComment lhs = new DatumComment(RandomReviewId.nextReviewId(), commentLhs, false);
        DataComment rhs = new DatumComment(RandomReviewId.nextReviewId(), commentRhs, true);
        DifferencePercentage noDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(noDifference), is(false));
    }
}

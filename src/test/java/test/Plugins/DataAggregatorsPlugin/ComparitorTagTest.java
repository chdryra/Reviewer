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
        .DataAggregationDefault.Implementation.ComparatorLevenshteinDistance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
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
public class ComparitorTagTest {
    private ComparatorTag mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparatorTag(new ComparatorLevenshteinDistance());
    }

    @Test
    public void zeroDifferenceForSameTag() {
        String tag = RandomString.nextSentence();
        DataTag lhs = new DatumTag(RandomReviewId.nextReviewId(), tag);
        DataTag rhs = new DatumTag(RandomReviewId.nextReviewId(), tag);
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void zeroDifferenceRegardlessOfCaseForSameTag() {
        String tag = RandomString.nextSentence();
        DataTag lhs = new DatumTag(RandomReviewId.nextReviewId(), tag.toUpperCase());
        DataTag rhs = new DatumTag(RandomReviewId.nextReviewId(), tag.toLowerCase());
        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfDifferentTags() {
        String tagLhs = RandomString.nextSentence();
        String tagRhs = RandomString.nextSentence();
        DataTag lhs = new DatumTag(RandomReviewId.nextReviewId(), tagLhs);
        DataTag rhs = new DatumTag(RandomReviewId.nextReviewId(), tagRhs);
        DifferencePercentage noDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(noDifference), is(false));
    }
}

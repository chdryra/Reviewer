/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.corelibrary.Aggregation.ComparatorString;
import com.chdryra.android.corelibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLevenshteinDistance;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Internally uses LevenshteinDistance. These values below are from JavaDoc of the function.
 * StringUtils.getLevenshteinDistance("","")               = 0
 * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
 * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
 * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
 */
public class ComparitorLevenshteinDistanceTest {
    private ComparatorString mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparatorLevenshteinDistance();
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
        DifferencePercentage expected = new DifferencePercentage(1. / (double) lhs.length());
        DifferencePercentage calculated1 = mComparitor.compare(lhs, rhs);
        DifferencePercentage calculated2 = mComparitor.compare(rhs, lhs);
        assertThat(calculated1.lessThanOrEqualTo(expected), is(true));
        assertThat(calculated2.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void elephantHippoLetterDifferenceReturnsExpectedDifference() {
        String lhs = "elephant";
        String rhs = "hippo";
        DifferencePercentage expected = new DifferencePercentage(7. / (double) lhs.length());
        DifferencePercentage calculated1 = mComparitor.compare(lhs, rhs);
        DifferencePercentage calculated2 = mComparitor.compare(rhs, lhs);
        assertThat(calculated1.lessThanOrEqualTo(expected), is(true));
        assertThat(calculated2.lessThanOrEqualTo(expected), is(true));
    }
}

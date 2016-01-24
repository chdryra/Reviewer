/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.DataAlgorithms.DataAggregation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation
        .DifferencePercentage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferencePercentageTest {
    private static final Random RAND = new Random();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructionWithPercentageLessThanZeroThrowsIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        new DifferencePercentage(RAND.nextDouble() - 1.1);
    }

    @Test
    public void constructionWithPercentageMoreThanOneThrowsIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        new DifferencePercentage(RAND.nextDouble() + 1.1);
    }

    @Test
    public void constructionWithPercentageZeroOk() {
        assertThat(new DifferencePercentage(0), not(nullValue()));
    }

    @Test
    public void constructionWithPercentageOneOk() {
        assertThat(new DifferencePercentage(1), not(nullValue()));
    }

    @Test
    public void lessThanOrEqualShowsAppropriateBehaviourForDifference() {
        double percentage = RAND.nextDouble();
        DifferencePercentage lower = new DifferencePercentage(percentage/2.);
        DifferencePercentage higher = new DifferencePercentage(percentage);
        assertThat(lower.lessThanOrEqualTo(higher), is(true));
        assertThat(higher.lessThanOrEqualTo(lower), is(false));
    }

    @Test
    public void lessThanOrEqualShowsAppropriateBehaviourForSame() {
        double percentage = RAND.nextDouble();
        DifferencePercentage pct1 = new DifferencePercentage(percentage);
        DifferencePercentage pct2 = new DifferencePercentage(percentage);
        assertThat(pct1.lessThanOrEqualTo(pct2), is(true));
        assertThat(pct2.lessThanOrEqualTo(pct1), is(true));
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.DataAggregation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceBoolean;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceBooleanTest {
    @Test
    public void falseAgainstFalseReturnsTrue() {
        DifferenceBoolean f1 = new DifferenceBoolean(false);
        DifferenceBoolean f2 = new DifferenceBoolean(false);
        assertThat(f1.lessThanOrEqualTo(f2), is(true));
        assertThat(f2.lessThanOrEqualTo(f1), is(true));
    }

    @Test
    public void trueAgainstTrueReturnsTrue() {
        DifferenceBoolean t1 = new DifferenceBoolean(true);
        DifferenceBoolean t2 = new DifferenceBoolean(true);
        assertThat(t1.lessThanOrEqualTo(t2), is(true));
        assertThat(t2.lessThanOrEqualTo(t1), is(true));
    }

    @Test
    public void differentBooleansReturnFalse() {
        DifferenceBoolean f = new DifferenceBoolean(false);
        DifferenceBoolean t = new DifferenceBoolean(true);
        assertThat(f.lessThanOrEqualTo(t), is(false));
        assertThat(t.lessThanOrEqualTo(f), is(false));
    }
}

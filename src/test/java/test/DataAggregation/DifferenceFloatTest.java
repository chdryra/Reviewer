/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.DataAggregation;

import com.chdryra.android.corelibrary.Aggregation.DifferenceFloat;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceFloatTest {
    private static final Random RAND = new Random();

    @Test
    public void lessThanOrEqualShowsAppropriateBehaviourForDifference() {
        float value = RAND.nextFloat();
        DifferenceFloat lower = new DifferenceFloat(value/2f);
        DifferenceFloat higher = new DifferenceFloat(value);
        assertThat(lower.lessThanOrEqualTo(higher), is(true));
        assertThat(higher.lessThanOrEqualTo(lower), is(false));
    }

    @Test
    public void lessThanOrEqualShowsAppropriateBehaviourForSame() {
        float value = RAND.nextFloat();
        DifferenceFloat val1 = new DifferenceFloat(value);
        DifferenceFloat val2 = new DifferenceFloat(value);
        assertThat(val1.lessThanOrEqualTo(val2), is(true));
        assertThat(val2.lessThanOrEqualTo(val1), is(true));
    }
}

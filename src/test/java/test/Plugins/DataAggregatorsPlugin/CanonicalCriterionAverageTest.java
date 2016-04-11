/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.reviewer.DataDefinitions.Factories.FactoryNullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalCriterionAverage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalStringMaker;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverageTest extends CanonicalStringMakerTest<DataCriterion>{
    public CanonicalCriterionAverageTest() {
        this(new CanonicalCriterionAverage());
    }

    protected CanonicalCriterionAverageTest(CanonicalStringMaker<DataCriterion> canonical) {
        super(canonical);
    }

    @Override
    protected void checkValidForMultipleAggregated(DataCriterion canonical) {
        String subject = canonical.getSubject();
        assertThat(subject, is(getModeString() + " + " + String.valueOf(getNumDifferent())));
        assertThat(canonical.getRating(), is(getExpectedRating()));
    }

    @Override
    protected void checkValidForSingleAggregated(DataCriterion canonical) {
        assertThat(canonical.getSubject(), is(getModeString()));
        assertThat(canonical.getRating(), is(getExpectedRating()));
    }

    @Override
    protected void checkInvalid(DataCriterion canonical) {
        assertThat(canonical, is(FactoryNullData.nullCriterion(canonical.getReviewId())));
    }

    @Override
    protected DataCriterion newDatum(String string) {
        return new DatumCriterion(RandomReviewId.nextReviewId(), string, RandomRating.nextRating());
    }

    protected float getExpectedRating() {
        IdableList<DataCriterion> data = getData();
        float average = 0f;
        for (DataCriterion child : data) {
            average += child.getRating() / (float) data.size();
        }
        return average;
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalCriterionMode;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionModeTest extends CanonicalCriterionAverageTest{
    private float mModeRating;

    public CanonicalCriterionModeTest() {
        super(new CanonicalCriterionMode());
        mModeRating = RandomRating.nextRating();
    }

    @Override
    protected DataCriterion newDatum(String string) {
        //To ensure it's the most common rating...
        String modeString = getModeString();
        if(string.equals(modeString)) {
            return new DatumCriterion(RandomReviewId.nextReviewId(), modeString, mModeRating);
        } else {
            return super.newDatum(string);
        }
    }

    @Override
    protected float getExpectedRating() {
        return mModeRating;
    }
}

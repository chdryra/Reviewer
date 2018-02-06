/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalSubjectMode;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectModeTest extends CanonicalStringMakerTest<DataSubject>{
    public CanonicalSubjectModeTest() {
        super(new CanonicalSubjectMode());
    }

    @Override
    protected void checkValidForMultipleAggregated(DataSubject canonical) {
        String string = canonical.getSubject();
        assertThat(string, is(getModeString() + " + " + String.valueOf(getNumDifferent())));
    }

    @Override
    protected void checkValidForSingleAggregated(DataSubject canonical) {
        assertThat(canonical.getSubject(), is(getModeString()));
    }

    @Override
    protected void checkInvalid(DataSubject canonical) {
        assertThat(canonical, is(FactoryNullData.nullSubject(canonical.getReviewId())));
    }

    @Override
    protected DataSubject newDatum(String string) {
        return new DatumSubject(RandomReviewId.nextReviewId(), string);
    }

    @Override
    protected String getModeString() {
        return super.getModeString().toLowerCase();
    }
}

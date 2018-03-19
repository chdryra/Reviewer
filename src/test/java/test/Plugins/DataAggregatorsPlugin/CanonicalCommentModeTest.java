/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalCommentMode;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentModeTest extends CanonicalStringMakerTest<DataComment> {
    public CanonicalCommentModeTest() {
        super(new CanonicalCommentMode());
    }

    @Override
    protected void checkValidForMultipleAggregated(DataComment canonical) {
        String string = canonical.getComment();
        assertThat(string, is(String.valueOf(getNumDifferent() + 1) + " comments"));
        assertThat(canonical.isHeadline(), is(false));
    }

    @Override
    protected void checkValidForSingleAggregated(DataComment canonical) {
        assertThat(canonical.getComment(), is(getModeString()));
        assertThat(canonical.isHeadline(), is(false));
    }

    @Override
    protected void checkInvalid(DataComment canonical) {
        assertThat(canonical, is(FactoryNullData.nullComment(canonical.getReviewId())));
    }

    @Override
    protected DataComment newDatum(String string) {
        return new DatumComment(RandomReviewId.nextReviewId(), string, getRand().nextBoolean());
    }
}

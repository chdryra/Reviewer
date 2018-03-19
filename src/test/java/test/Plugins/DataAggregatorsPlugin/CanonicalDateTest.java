/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDateTest {
    private static final int NUM = 10;
    private CanonicalDate mCanonical;

    @Before
    public void setUp() {
        mCanonical = new CanonicalDate();
    }

    @Test
    public void noDataReturnsDateWithZeroTime() {
        IdableList<DataDate> dates = new IdableDataList<>(RandomReviewId.nextReviewId());
        DataDate canonical = mCanonical.getCanonical(dates);
        assertThat(canonical, is(FactoryNullData.nulDate(dates.getReviewId())));
    }

    @Test
    public void getCanonicalReturnsMostRecentDate() {
        IdableList<DataDate> dates = new IdableDataList<>(RandomReviewId.nextReviewId());
        long expectedTime = 0l;
        for (int i = 0; i < NUM; ++i) {
            DataDate date = RandomDataDate.nextDate();
            if (date.getTime() > expectedTime) expectedTime = date.getTime();
            dates.add(date);
        }

        DataDate canonical = mCanonical.getCanonical(dates);
        assertThat(canonical.getReviewId(), is(dates.getReviewId()));
        assertThat(canonical.getTime(), is(expectedTime));
    }
}

package test.DataAlgorithms.DataAggregation;

import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.DataAggregationPluginDefault.Implementation.CanonicalDate;
import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

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
        IdableList<DataDateReview> dates = new IdableDataList<>(RandomReviewId.nextReviewId());
        DataDateReview canonical = mCanonical.getCanonical(dates);
        assertThat(canonical, is(NullData.nulDate(dates.getReviewId())));
    }

    @Test
    public void getCanonicalReturnsMostRecentDate() {
        IdableList<DataDateReview> dates = new IdableDataList<>(RandomReviewId.nextReviewId());
        long expectedTime = 0l;
        for (int i = 0; i < NUM; ++i) {
            DataDateReview date = RandomDataDate.nextDateReview();
            if (date.getTime() > expectedTime) expectedTime = date.getTime();
            dates.add(date);
        }

        DataDateReview canonical = mCanonical.getCanonical(dates);
        assertThat(canonical.getReviewId(), is(dates.getReviewId()));
        assertThat(canonical.getTime(), is(expectedTime));
    }
}

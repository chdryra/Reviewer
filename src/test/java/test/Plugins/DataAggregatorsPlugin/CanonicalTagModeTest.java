package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalTagMode;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagModeTest extends CanonicalStringMakerTest<DataTag>{
    public CanonicalTagModeTest() {
        super(new CanonicalTagMode());
    }

    @Override
    protected void checkValidForMultipleAggregated(DataTag canonical) {
        String string = canonical.getTag();
        assertThat(string, is(getModeString() + " + " + String.valueOf(getNumDifferent())));
    }

    @Override
    protected void checkValidForSingleAggregated(DataTag canonical) {
        assertThat(canonical.getTag(), is(getModeString()));
    }

    @Override
    protected void checkInvalid(DataTag canonical) {
        assertThat(canonical, is(NullData.nullTag(canonical.getReviewId())));
    }

    @Override
    protected DataTag newDatum(String string) {
        return new DatumTag(RandomReviewId.nextReviewId(), string);
    }
}

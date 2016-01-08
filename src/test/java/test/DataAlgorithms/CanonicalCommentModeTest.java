package test.DataAlgorithms;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalCommentMode;

import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentModeTest extends CanonicalStringMakerTest<DataComment>{
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
        assertThat(canonical, is(NullData.nullComment(canonical.getReviewId())));
    }

    @Override
    protected DataComment newDatum(String string) {
        return new DatumComment(RandomReviewId.nextReviewId(), string, getRand().nextBoolean());
    }
}

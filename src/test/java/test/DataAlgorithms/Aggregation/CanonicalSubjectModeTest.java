package test.DataAlgorithms.Aggregation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalSubjectMode;

import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;

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
        assertThat(canonical, is(NullData.nullSubject(canonical.getReviewId())));
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

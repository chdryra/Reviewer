package test.DataAlgorithms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalFact;
import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonnicalFactTest {
    private static final int NUM_LABEL_MODE = 5;
    private static final int NUM_VALUE_MODE = 7;
    private static final int NUM_NON_MODE_DATA = NUM_LABEL_MODE + NUM_VALUE_MODE;
    private static final int NUM_DATA = NUM_LABEL_MODE + NUM_VALUE_MODE + NUM_NON_MODE_DATA;

    private CanonicalFact mCanonical;
    private String mModeLabel;
    private String mModeValue;
    private IdableList<DataFact> mData;

    @Before
    public void setUp() {
        mCanonical = new CanonicalFact();
        mModeLabel = RandomString.nextWord();
        mModeValue = RandomString.nextWord();
    }

    @Test
    public void noDataReturnsInvalidDatum() {
        IdableList<DataFact> data = new IdableDataList<>(RandomReviewId.nextReviewId());
        DataFact canonical = mCanonical.getCanonical(data);
        assertThat(canonical, is(NullData.nullFact(canonical.getReviewId())));
    }

    @Test
    public void getCanonicalReturnsSingleFactLableAndValueIfOnlyOneType() {
        newData(false);
        DataFact canonical = mCanonical.getCanonical(mData);
        assertThat(canonical.getReviewId().toString(), is(mData.getReviewId().toString()));
        assertThat(canonical.getLabel(), is(mModeLabel));
        assertThat(canonical.getValue(), is(mModeValue));
    }

    @Test
    public void getCanonicalReturnsModeLabelPluExtrasAndNumValuesAsFactIfMoreThanOneType() {
        newData(true);
        DataFact canonical = mCanonical.getCanonical(mData);
        assertThat(canonical.getReviewId().toString(), is(mData.getReviewId().toString()));
        assertThat(canonical.getLabel(), is(mModeLabel + " + " + String.valueOf(NUM_VALUE_MODE)));
        assertThat(canonical.getValue(), is(String.valueOf(NUM_LABEL_MODE + 1) + " values"));
    }

    private void newData(boolean variable) {
        mData = newDataList();
        if(variable) {
            addData(mData, mModeLabel, null, NUM_LABEL_MODE);
            addData(mData, null, mModeValue, NUM_VALUE_MODE);
        } else {
            addData(mData, mModeLabel, mModeValue, NUM_LABEL_MODE + NUM_VALUE_MODE);
        }
    }

    @NonNull
    private IdableList<DataFact> newDataList() {
        return new IdableDataList<>(RandomReviewId.nextReviewId());
    }

    private void addData(IdableList<DataFact> data, @Nullable String label, @Nullable String value, int numData) {
        boolean randomLabel = label == null;
        boolean randomValue = value == null;
        for (int j = 0; j < numData; ++j) {
            if(randomLabel) label = RandomString.nextWord();
            if(randomValue) value = RandomString.nextWord();
            data.add(newDatum(label, value));
        }
    }

    private DataFact newDatum(String label, String value) {
        return new DatumFact(RandomReviewId.nextReviewId(), label, value);
    }
}

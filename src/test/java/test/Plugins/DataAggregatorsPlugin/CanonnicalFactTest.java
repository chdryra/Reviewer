package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalFact;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonnicalFactTest {
    private static final int MIN_NUM_DATA = 3;
    private static final int MAX_EXTRA_DATA = 7;
    private static final Random RAND = new Random();

    private CanonicalFact mCanonical;
    private String mModeLabel;
    private String mModeValue;
    private IdableList<DataFact> mData;
    private int mNumValueMode;
    private int mNumLabelMode;

    @Before
    public void setUp() {
        mCanonical = new CanonicalFact();
        mModeLabel = RandomString.nextWord();
        mModeValue = RandomString.nextWord();
        mNumLabelMode = MIN_NUM_DATA + RAND.nextInt(MAX_EXTRA_DATA);
        mNumValueMode = MIN_NUM_DATA + RAND.nextInt(MAX_EXTRA_DATA);
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
        assertThat(canonical.getLabel(), is(mModeLabel + " + " + String.valueOf(mNumValueMode)));
        assertThat(canonical.getValue(), is(String.valueOf(mNumLabelMode + 1) + " values"));
    }

    private void newData(boolean variable) {
        mData = newDataList();
        if(variable) {
            addData(mData, mModeLabel, null, mNumLabelMode);
            addData(mData, null, mModeValue, mNumValueMode);
        } else {
            addData(mData, mModeLabel, mModeValue, mNumLabelMode + mNumValueMode);
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

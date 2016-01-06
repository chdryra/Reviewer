package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .CanonicalStringMaker;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CanonicalStringMakerTest<T extends HasReviewId> {
    private static final int MIN_DATA = 1;
    private static final int MAX_EXTRA = 5;
    private static final int MIN_DIFFERENT = 2;
    private static final int MAX_ADDITIONAL = 3;
    private static final Random RAND = new Random();

    private String mModeString;
    private int mNumDifferent;

    private CanonicalStringMaker<T> mCanonical;
    private IdableList<T> mData;

    protected abstract T newDatum(String string);

    protected abstract void checkValidForSingleAggregated(T canonical);

    protected abstract void checkValidForMultipleAggregated(T canonical);

    protected abstract void checkInvalid(T canonical);

    public CanonicalStringMakerTest(CanonicalStringMaker<T> canonical) {
        mCanonical = canonical;
    }

    @Before
    public void setUp() {
        mModeString = RandomString.nextWord();
        mNumDifferent = MIN_DIFFERENT + RAND.nextInt(MAX_ADDITIONAL);
    }

    @Test
    public void noDataReturnsInvalidDatum() {
        IdableList<T> data = newDataList();
        T canonical = mCanonical.getCanonical(data);
        assertThat(canonical.getReviewId().toString(), is(data.getReviewId().toString()));
        checkInvalid(canonical);
    }

    @Test
    public void getCanonicalReturnsModeItemCorrectlyIfOnlyOneType() {
        newData(false);
        T canonical = mCanonical.getCanonical(mData);
        assertThat(canonical.getReviewId().toString(), is(mData.getReviewId().toString()));
        checkValidForSingleAggregated(canonical);
    }

    @Test
    public void getCanonicalReturnsModeItemCorrectlyIfMoreThanOneType() {
        newData(true);
        T canonical = mCanonical.getCanonical(mData);
        assertThat(canonical.getReviewId().toString(), is(mData.getReviewId().toString()));
        checkValidForMultipleAggregated(canonical);
    }

    protected IdableList<T> getData() {
        return mData;
    }

    protected int getNumDifferent() {
        return mNumDifferent;
    }

    protected Random getRand() {
        return RAND;
    }

    protected String getModeString() {
        return mModeString;
    }

    private void newData(boolean addNonMode) {
        mData = newDataList();
        if(addNonMode) addNonModeItems(mData);
        addModeItems(mData);
    }

    @NonNull
    private IdableList<T> newDataList() {
        return new IdableDataList<>(RandomReviewId.nextReviewId());
    }

    private void addModeItems(IdableList<T> data) {
        addData(data, mModeString, getNumModeData());
    }

    protected int getNumModeData() {
        return MIN_DATA + MAX_EXTRA + 1;
    }

    private void addNonModeItems(IdableList<T> data) {
        for (int i = 0; i < mNumDifferent; ++i) {
            addData(data, RandomString.nextWord(), MIN_DATA + RAND.nextInt(MAX_EXTRA));
        }
    }

    private void addData(IdableList<T> data, String string, int numData) {
        for (int j = 0; j < numData; ++j) {
            data.add(newDatum(string));
        }
    }
}

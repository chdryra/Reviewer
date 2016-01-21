package test.Plugins.ReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb
        .Implementation.RowLocationImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb
        .Interfaces.RowLocation;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocationImplTest extends RowTableBasicTest<RowLocationImpl> {

    public static final int INDEX = 314;

    public RowLocationImplTest() {
        super(RowLocation.LOCATION_ID.getName(), 5);
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowLocation reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowLocation.LOCATION_ID, reference.getRowId());
        values.put(RowLocation.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowLocation.LATITUDE, reference.getLatLng().latitude);
        values.put(RowLocation.LONGITUDE, reference.getLatLng().longitude);
        values.put(RowLocation.NAME, reference.getName());

        RowLocationImpl row = new RowLocationImpl(values);

        assertThat(row.hasData(new DataValidator()), is(true));

        checkAgainstReference(row, reference);
    }

    @Test
    public void constructionWithDataLocationAndGetters() {
        DataLocation location = new DatumLocation(RandomReviewId.nextReviewId(),
                RandomLatLng.nextLatLng(), RandomString.nextWord());

        RowLocationImpl row = new RowLocationImpl(location, INDEX);

        assertThat(row.hasData(new DataValidator()), is(true));

        checkAgainstReference(row, location);
    }

    @Test
    public void constructionWithInvalidLocationIdMakesRowLocationInvalid() {
        RowLocation reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowLocation.LOCATION_ID, "");
        values.put(RowLocation.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowLocation.LATITUDE, reference.getLatLng().latitude);
        values.put(RowLocation.LONGITUDE, reference.getLatLng().longitude);
        values.put(RowLocation.NAME, reference.getName());

        RowLocationImpl row = new RowLocationImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowLocationInvalid() {
        RowLocation reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowLocation.LOCATION_ID, reference.getRowId());
        values.put(RowLocation.REVIEW_ID, "");
        values.put(RowLocation.LATITUDE, reference.getLatLng().latitude);
        values.put(RowLocation.LONGITUDE, reference.getLatLng().longitude);
        values.put(RowLocation.NAME, reference.getName());

        RowLocationImpl row = new RowLocationImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidNameMakesRowLocationInvalid() {
        DataLocation location = new DatumLocation(RandomReviewId.nextReviewId(),
                RandomLatLng.nextLatLng(), "");

        RowLocationImpl row = new RowLocationImpl(location, INDEX);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowLocationImpl row = newRow();

        ArrayList<RowEntry<?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(5));

        checkEntry(entries.get(0), RowLocation.LOCATION_ID, getRowId(row));
        checkEntry(entries.get(1), RowLocation.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(2), RowLocation.LATITUDE, row.getLatLng().latitude);
        checkEntry(entries.get(3), RowLocation.LONGITUDE, row.getLatLng().longitude);
        checkEntry(entries.get(4), RowLocation.NAME, row.getName());
    }

    @NonNull
    @Override
    protected RowLocationImpl newRow() {
        return new RowLocationImpl(new DatumLocation(RandomReviewId.nextReviewId(),
                RandomLatLng.nextLatLng(), RandomString.nextWord()), INDEX);
    }

    @Override
    protected String getRowId(RowLocationImpl row) {
        return rowId(row);
    }

    private void checkAgainstReference(RowLocationImpl row, DataLocation reference) {
        assertThat(row.getRowId(), is(rowId(reference)));
        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getLatLng(), is(reference.getLatLng()));
        assertThat(row.getName(), is(reference.getName()));
    }

    private String rowId(DataLocation location) {
        return location.getReviewId().toString() + ":l" + String.valueOf(INDEX);
    }
}

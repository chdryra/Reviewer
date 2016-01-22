package test.Plugins.ReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.ByteArray;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.RowImageImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImageImplTest extends RowTableBasicTest<RowImageImpl>{
    private static final Random RAND = new Random();
    public static final int INDEX = 314;

    public RowImageImplTest() {
        super(RowImage.IMAGE_ID.getName(), 6);
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowImageImpl reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowImage.IMAGE_ID, reference.getRowId());
        values.put(RowImage.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowImage.BITMAP, reference.getBitmap());
        values.put(RowImage.IS_COVER, reference.isCover());
        values.put(RowImage.CAPTION, reference.getCaption());
        values.put(RowImage.IMAGE_DATE, reference.getDate().getTime());

        checkAgainstReference(new RowImageImpl(values), reference);
    }

    private void checkAgainstReference(RowImageImpl row, DataImage reference) {
        assertThat(row.getRowId(), is(rowId(reference, INDEX)));
        assertThat(row.getReviewId(), is(reference.getReviewId()));
        assertThat(row.getBitmap(), is(reference.getBitmap()));
        assertThat(row.isCover(), is(reference.isCover()));
        assertThat(row.getCaption(), is(reference.getCaption()));
        assertThat(row.getDate().getTime(), is(reference.getDate().getTime()));
    }

    @Test
    public void constructionWithDataImageAndGetters() {
        DataImage reference = new DatumImage(RandomReviewId.nextReviewId(), null,
                RandomDataDate.nextDateReview(),
                RandomString.nextSentence(), RAND.nextBoolean());

        checkAgainstReference(new RowImageImpl(reference, INDEX), reference);
    }

    @Test
    public void constructionWithInvalidImageIdMakesRowImageInvalid() {
        RowImage reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowImage.IMAGE_ID, "");
        values.put(RowImage.REVIEW_ID, reference.getReviewId().toString());
        values.put(RowImage.BITMAP, reference.getBitmap());
        values.put(RowImage.IS_COVER, reference.isCover());
        values.put(RowImage.CAPTION, reference.getCaption());
        values.put(RowImage.IMAGE_DATE, reference.getDate().getTime());

        RowImageImpl row = new RowImageImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithInvalidReviewIdMakesRowImageInvalid() {
        RowImage reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowImage.IMAGE_ID, reference.getRowId());
        values.put(RowImage.REVIEW_ID, "");
        values.put(RowImage.BITMAP, reference.getBitmap());
        values.put(RowImage.IS_COVER, reference.isCover());
        values.put(RowImage.CAPTION, reference.getCaption());
        values.put(RowImage.IMAGE_DATE, reference.getDate().getTime());

        RowImageImpl row = new RowImageImpl(values);

        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowImageImpl row = newRow();

        ArrayList<RowEntry<?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(6));

        checkEntry(entries.get(0), RowImage.IMAGE_ID, getRowId(row));
        checkEntry(entries.get(1), RowImage.REVIEW_ID, row.getReviewId().toString());
        checkEntry(entries.get(2), RowImage.BITMAP, new ByteArray(row.getBitmapByteArray()));
        checkEntry(entries.get(3), RowImage.IS_COVER, row.isCover());
        checkEntry(entries.get(4), RowImage.CAPTION, row.getCaption());
        checkEntry(entries.get(5), RowImage.IMAGE_DATE, row.getDate().getTime());
    }

    @NonNull
    @Override
    protected RowImageImpl newRow() {
        //Because Android hasn't stubbed Bitmaps can't actually use a randomly generated Bitmap...
        RowValuesForTest values = new RowValuesForTest();
        String reviewId = RandomReviewId.nextIdString();
        values.put(RowImage.REVIEW_ID, reviewId);
        values.put(RowImage.IMAGE_ID, reviewId + ":i" + String.valueOf(INDEX));
        byte[] data = new byte[40];
        RAND.nextBytes(data);
        values.put(RowImage.BITMAP, new ByteArray(data));
        values.put(RowImage.IS_COVER, RAND.nextBoolean());
        values.put(RowImage.CAPTION, RandomString.nextSentence());
        values.put(RowImage.IMAGE_DATE, RandomDataDate.nextDate().getTime());

        return new RowImageImpl(values);
    }

    @Override
    protected String getRowId(RowImageImpl row) {
        return rowId(row, 314);
    }

    private String rowId(DataImage image, int index) {
        return image.getReviewId().toString() + ":i" + String.valueOf(index);
    }
}

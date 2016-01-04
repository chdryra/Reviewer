package test.Model.ReviewsModel.MdConverters;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterImages;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class MdConverterImageTest extends MdConverterBasicTest<DataImage, MdImage> {
    public MdConverterImageTest() {
        super(new MdConverterImages());
    }

    @Override
    protected DataImage newDatum() {
        return new Image();
    }

    @Override
    protected void checkDatumEquivalence(DataImage datum, MdImage mdDatum) {
        assertThat(mdDatum.getReviewId().toString(), is(datum.getReviewId().toString()));
        assertThat(mdDatum.getBitmap(), is(datum.getBitmap()));
        assertThat(mdDatum.getDate().getTime(), is(datum.getDate().getTime()));
        assertThat(mdDatum.getCaption(), is(datum.getCaption()));
        assertThat(mdDatum.isCover(), is(datum.isCover()));
    }

    private static class Image implements DataImage {
        private static final Random RAND = new Random();

        @Mock
        private Bitmap mBitmap;
        private DataDate mDate;
        private String mCaption;
        private boolean mIsCover;
        private ReviewId mReviewId;

        public Image() {
            mReviewId = RandomReviewId.nextReviewId();
            mDate = new DatumDateReview(mReviewId, RandomDate.nextDate().getTime());
            mCaption = RandomString.nextSentence();
            mIsCover = RAND.nextBoolean();
        }

        @Override
        public Bitmap getBitmap() {
            return mBitmap;
        }

        @Override
        public DataDate getDate() {
            return mDate;
        }

        @Override
        public String getCaption() {
            return mCaption;
        }

        @Override
        public boolean isCover() {
            return mIsCover;
        }

        @Override
        public ReviewId getReviewId() {
            return mReviewId;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }
}

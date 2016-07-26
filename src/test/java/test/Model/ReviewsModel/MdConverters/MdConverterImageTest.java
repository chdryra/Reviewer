/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.MdConverters;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.MdConverterImages;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReviewId;

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
        DataEquivalence.checkEquivalence(datum, mdDatum);
    }

    @Override
    protected void checkDatumEquivalence(DataImage datum, MdImage mdDatum, ReviewId mdDatumId) {
        DataEquivalence.checkEquivalence(datum, mdDatum, mdDatumId);
    }

    private static class Image implements DataImage {
        private static final Random RAND = new Random();

        @Mock
        private Bitmap mBitmap;
        private DateTime mDate;
        private String mCaption;
        private boolean mIsCover;
        private ReviewId mReviewId;

        public Image() {
            mReviewId = RandomReviewId.nextReviewId();
            mDate = new DatumDate(mReviewId, RandomDate.nextDate().getTime());
            mCaption = RandomString.nextSentence();
            mIsCover = RAND.nextBoolean();
        }

        @Override
        public Bitmap getBitmap() {
            return mBitmap;
        }

        @Override
        public DateTime getDate() {
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

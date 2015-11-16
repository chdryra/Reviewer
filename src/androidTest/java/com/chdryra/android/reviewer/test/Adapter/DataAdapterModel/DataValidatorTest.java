/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.DataAdapterModel;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdUrlList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.net.URL;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataValidatorTest extends TestCase {
    private MdDataMocker mMdMocker;
    private MdReviewId mR;

    @SmallTest
    public void testValidateString() {
        assertFalse(DataValidator.validateString(null));
        assertFalse(DataValidator.validateString(""));
        assertTrue(DataValidator.validateString(RandomString.nextWord()));
    }

    @SmallTest
    public void testNotNull() {
        assertFalse(DataValidator.NotNull(null));
        assertTrue(DataValidator.NotNull(this));
    }

    @SmallTest
    public void testValidateComment() {
        String comment = RandomString.nextSentence();
        GvCommentList.GvComment invalid = new GvCommentList.GvComment("");
        GvCommentList.GvComment valid = new GvCommentList.GvComment(comment);

        assertFalse(DataValidator.validate(new GvCommentList.GvComment()));
        assertFalse(DataValidator.validate(invalid));
        assertFalse(DataValidator.validate(new GvCommentList.GvComment(invalid)));
        assertTrue(DataValidator.validate(valid));
        assertTrue(DataValidator.validate(new GvCommentList.GvComment(valid)));
        assertTrue(DataValidator.validate(GvDataMocker.newComment(null)));

        MdReviewId r = ReviewMocker.newReview().getMdReviewId();
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(r, null, false)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(r, null, true)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(r, "", false)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(r, "", true)));
        assertTrue(DataValidator.validate(new MdCommentList.MdComment(r, comment, false)));
        assertTrue(DataValidator.validate(new MdCommentList.MdComment(r, comment, true)));
        assertTrue(DataValidator.validate(mMdMocker.newComment()));
    }

    @SmallTest
    public void testValidateFact() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        assertFalse(DataValidator.validate(new GvFactList.GvFact()));
        assertFalse(DataValidator.validate(new GvFactList.GvFact("", null)));
        assertFalse(DataValidator.validate(new GvFactList.GvFact(null, "")));
        assertFalse(DataValidator.validate(new GvFactList.GvFact("", "")));
        assertFalse(DataValidator.validate(new GvFactList.GvFact("", value)));
        assertFalse(DataValidator.validate(new GvFactList.GvFact(label, "")));
        assertTrue(DataValidator.validate(new GvFactList.GvFact(label, value)));
        assertTrue(DataValidator.validate(GvDataMocker.newFact(null)));

        assertFalse(DataValidator.validate(new MdFactList.MdFact(mR, null, null)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(mR, "", null)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(mR, null, "")));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(mR, "", "")));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(mR, "", value)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(mR, label, "")));
        assertTrue(DataValidator.validate(new MdFactList.MdFact(mR, label, value)));
        assertTrue(DataValidator.validate(mMdMocker.newFact()));
    }

    @SmallTest
    public void testValidateImage() {
        Bitmap b = BitmapMocker.nextBitmap(false);
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();
        LatLng latLng = RandomLatLng.nextLatLng();

        assertFalse(DataValidator.validate(new GvImageList.GvImage()));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, null, null, true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, null, null, false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, null, latLng, null,
                true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, null, latLng, null,
                false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, null, caption,
                true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, null, caption,
                false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, latLng, null,
                true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, latLng, null,
                false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, latLng, caption,
                true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, date, latLng, caption,
                false)));

        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, null, null, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, null, null, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, null, null, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, null, null, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, latLng, null, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, latLng, null, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, null, caption, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, null, caption, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, null, caption, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, null, caption,
                false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, latLng, null, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, latLng, null,
                false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, latLng, caption, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, date, latLng, caption,
                false)));
        assertTrue(DataValidator.validate(GvDataMocker.newImage(null)));

        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, null, null, true
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, null, null, false
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, date, null, true
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, date, null, false
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, null, caption, true
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, null, caption, false
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, date, caption, true
        )));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(mR, null, date, caption, false
        )));

        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, null, null, true)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, null, null, false)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, date, null, true)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, date, null, false)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, null, caption, true)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, null, caption, false)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, date, caption, true
        )));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(mR, b, date, caption, false
        )));
        assertTrue(DataValidator.validate(mMdMocker.newImage()));
    }

    @SmallTest
    public void testValidateLocation() {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();

        assertFalse(DataValidator.validate(new GvLocationList.GvLocation()));
        assertFalse(DataValidator.validate(new GvLocationList.GvLocation(latLng, null)));
        assertFalse(DataValidator.validate(new GvLocationList.GvLocation(null, "")));
        assertFalse(DataValidator.validate(new GvLocationList.GvLocation(latLng, "")));
        assertTrue(DataValidator.validate(new GvLocationList.GvLocation(latLng, name)));
        assertTrue(DataValidator.validate(GvDataMocker.newLocation(null)));

        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(mR, null, null)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(mR, latLng, null)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(mR, null, "")));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(mR, latLng, "")));
        assertTrue(DataValidator.validate(new MdLocationList.MdLocation(mR, latLng, name)));
        assertTrue(DataValidator.validate(mMdMocker.newLocation()));
    }

    @SmallTest
    public void testValidateUrl() {

        GvUrlList.GvUrl gvUrl = null;
        while (gvUrl == null) gvUrl = GvDataMocker.newUrl(null);

        URL url = gvUrl.getUrl();
        assertFalse(DataValidator.validate(new GvUrlList.GvUrl()));
        assertTrue(DataValidator.validate(new GvUrlList.GvUrl(RandomString.nextWord(), url)));
        assertTrue(DataValidator.validate(GvDataMocker.newUrl(null)));

        MdReviewId r = ReviewMocker.newReview().getMdReviewId();
        assertTrue(DataValidator.validate(new MdUrlList.MdUrl(r, RandomString.nextWord(), url)));
        assertTrue(DataValidator.validate(mMdMocker.newUrl()));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMdMocker = new MdDataMocker(ReviewMocker.newReview().getMdReviewId());
        mR = ReviewMocker.newReview().getMdReviewId();
    }
}

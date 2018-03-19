/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.startouch.test.Adapter.DataAdapterModel;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.MdDataMocker;
import com.chdryra.android.startouch.test.TestUtils.ReviewMocker;
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
        GvComment invalid = new GvComment("");
        GvComment valid = new GvComment(comment);

        assertFalse(DataValidator.validate(new GvComment()));
        assertFalse(DataValidator.validate(invalid));
        assertFalse(DataValidator.validate(new GvComment(invalid)));
        assertTrue(DataValidator.validate(valid));
        assertTrue(DataValidator.validate(new GvComment(valid)));
        assertTrue(DataValidator.validate(GvDataMocker.newComment(null)));

        MdReviewId r = ReviewMocker.newReview().getMdReviewId();
        assertFalse(DataValidator.validate(new MdComment(r, null, false)));
        assertFalse(DataValidator.validate(new MdComment(r, null, true)));
        assertFalse(DataValidator.validate(new MdComment(r, "", false)));
        assertFalse(DataValidator.validate(new MdComment(r, "", true)));
        assertTrue(DataValidator.validate(new MdComment(r, comment, false)));
        assertTrue(DataValidator.validate(new MdComment(r, comment, true)));
        assertTrue(DataValidator.validate(mMdMocker.newComment()));
    }

    @SmallTest
    public void testValidateFact() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        assertFalse(DataValidator.validate(new GvFact()));
        assertFalse(DataValidator.validate(new GvFact("", null)));
        assertFalse(DataValidator.validate(new GvFact(null, "")));
        assertFalse(DataValidator.validate(new GvFact("", "")));
        assertFalse(DataValidator.validate(new GvFact("", value)));
        assertFalse(DataValidator.validate(new GvFact(label, "")));
        assertTrue(DataValidator.validate(new GvFact(label, value)));
        assertTrue(DataValidator.validate(GvDataMocker.newFact(null)));

        assertFalse(DataValidator.validate(new MdFact(mR, null, null)));
        assertFalse(DataValidator.validate(new MdFact(mR, "", null)));
        assertFalse(DataValidator.validate(new MdFact(mR, null, "")));
        assertFalse(DataValidator.validate(new MdFact(mR, "", "")));
        assertFalse(DataValidator.validate(new MdFact(mR, "", value)));
        assertFalse(DataValidator.validate(new MdFact(mR, label, "")));
        assertTrue(DataValidator.validate(new MdFact(mR, label, value)));
        assertTrue(DataValidator.validate(mMdMocker.newFact()));
    }

    @SmallTest
    public void testValidateImage() {
        Bitmap b = BitmapMocker.nextBitmap(false);
        Date date = RandomDate.nextDate();
        String caption = RandomString.nextSentence();
        LatLng latLng = RandomLatLng.nextLatLng();

        assertFalse(DataValidator.validate(new GvImage()));
        assertFalse(DataValidator.validate(new GvImage(null, date, null, null, true)));
        assertFalse(DataValidator.validate(new GvImage(null, date, null, null, false)));
        assertFalse(DataValidator.validate(new GvImage(null, null, latLng, null,
                true)));
        assertFalse(DataValidator.validate(new GvImage(null, null, latLng, null,
                false)));
        assertFalse(DataValidator.validate(new GvImage(null, date, null, caption,
                true)));
        assertFalse(DataValidator.validate(new GvImage(null, date, null, caption,
                false)));
        assertFalse(DataValidator.validate(new GvImage(null, date, latLng, null,
                true)));
        assertFalse(DataValidator.validate(new GvImage(null, date, latLng, null,
                false)));
        assertFalse(DataValidator.validate(new GvImage(null, date, latLng, caption,
                true)));
        assertFalse(DataValidator.validate(new GvImage(null, date, latLng, caption,
                false)));

        assertTrue(DataValidator.validate(new GvImage(b, null, null, null, true)));
        assertTrue(DataValidator.validate(new GvImage(b, null, null, null, false)));
        assertTrue(DataValidator.validate(new GvImage(b, date, null, null, true)));
        assertTrue(DataValidator.validate(new GvImage(b, date, null, null, false)));
        assertTrue(DataValidator.validate(new GvImage(b, null, latLng, null, true)));
        assertTrue(DataValidator.validate(new GvImage(b, null, latLng, null, false)));
        assertTrue(DataValidator.validate(new GvImage(b, null, null, caption, true)));
        assertTrue(DataValidator.validate(new GvImage(b, null, null, caption, false)));
        assertTrue(DataValidator.validate(new GvImage(b, date, null, caption, true)));
        assertTrue(DataValidator.validate(new GvImage(b, date, null, caption,
                false)));
        assertTrue(DataValidator.validate(new GvImage(b, date, latLng, null, true)));
        assertTrue(DataValidator.validate(new GvImage(b, date, latLng, null,
                false)));
        assertTrue(DataValidator.validate(new GvImage(b, date, latLng, caption, true)));
        assertTrue(DataValidator.validate(new GvImage(b, date, latLng, caption,
                false)));
        assertTrue(DataValidator.validate(GvDataMocker.newImage(null)));

        assertFalse(DataValidator.validate(new MdImage(mR, null, null, null, true
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, null, null, false
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, date, null, true
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, date, null, false
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, null, caption, true
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, null, caption, false
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, date, caption, true
        )));
        assertFalse(DataValidator.validate(new MdImage(mR, null, date, caption, false
        )));

        assertTrue(DataValidator.validate(new MdImage(mR, b, null, null, true)));
        assertTrue(DataValidator.validate(new MdImage(mR, b, null, null, false)));
        assertTrue(DataValidator.validate(new MdImage(mR, b, date, null, true)));
        assertTrue(DataValidator.validate(new MdImage(mR, b, date, null, false)));
        assertTrue(DataValidator.validate(new MdImage(mR, b, null, caption, true)));
        assertTrue(DataValidator.validate(new MdImage(mR, b, null, caption, false)));
        assertTrue(DataValidator.validate(new MdImage(mR, b, date, caption, true
        )));
        assertTrue(DataValidator.validate(new MdImage(mR, b, date, caption, false
        )));
        assertTrue(DataValidator.validate(mMdMocker.newImage()));
    }

    @SmallTest
    public void testValidateLocation() {
        LatLng latLng = RandomLatLng.nextLatLng();
        String name = RandomString.nextWord();

        assertFalse(DataValidator.validate(new GvLocation()));
        assertFalse(DataValidator.validate(new GvLocation(latLng, null)));
        assertFalse(DataValidator.validate(new GvLocation(null, "")));
        assertFalse(DataValidator.validate(new GvLocation(latLng, "")));
        assertTrue(DataValidator.validate(new GvLocation(latLng, name)));
        assertTrue(DataValidator.validate(GvDataMocker.newLocation(null)));

        assertFalse(DataValidator.validate(new MdLocation(mR, null, null)));
        assertFalse(DataValidator.validate(new MdLocation(mR, latLng, null)));
        assertFalse(DataValidator.validate(new MdLocation(mR, null, "")));
        assertFalse(DataValidator.validate(new MdLocation(mR, latLng, "")));
        assertTrue(DataValidator.validate(new MdLocation(mR, latLng, name)));
        assertTrue(DataValidator.validate(mMdMocker.newLocation()));
    }

    @SmallTest
    public void testValidateUrl() {

        GvUrl gvUrl = null;
        while (gvUrl == null) gvUrl = GvDataMocker.newUrl(null);

        URL url = gvUrl.getUrl();
        assertFalse(DataValidator.validate(new GvUrl()));
        assertTrue(DataValidator.validate(new GvUrl(RandomString.nextWord(), url)));
        assertTrue(DataValidator.validate(GvDataMocker.newUrl(null)));

        MdReviewId r = ReviewMocker.newReview().getMdReviewId();
        assertTrue(DataValidator.validate(new MdUrl(r, RandomString.nextWord(), url)));
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

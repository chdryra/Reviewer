/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.test.Controller.DataAdapterModel;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
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
    private ReviewId mR;

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

        ReviewId r = ReviewMocker.newReview().getId();
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(null, false, r)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(null, true, r)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment("", false, r)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment("", true, r)));
        assertTrue(DataValidator.validate(new MdCommentList.MdComment(comment, false, r)));
        assertTrue(DataValidator.validate(new MdCommentList.MdComment(comment, true, r)));
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

        assertFalse(DataValidator.validate(new MdFactList.MdFact(null, null, mR)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact("", null, mR)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(null, "", mR)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact("", "", mR)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact("", value, mR)));
        assertFalse(DataValidator.validate(new MdFactList.MdFact(label, "", mR)));
        assertTrue(DataValidator.validate(new MdFactList.MdFact(label, value, mR)));
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

        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, null, true,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, null, false,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, date, null, true,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, date, null, false,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, caption, true,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, caption, false,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, date, caption, true,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, date, caption, false,
                mR)));

        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, null, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, null, false, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, date, null, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, date, null, false, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, caption, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, caption, false, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, date, caption, true,
                mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, date, caption, false,
                mR)));
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

        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(null, null, mR)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(latLng, null, mR)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(null, "", mR)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(latLng, "", mR)));
        assertTrue(DataValidator.validate(new MdLocationList.MdLocation(latLng, name, mR)));
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

        ReviewId r = ReviewMocker.newReview().getId();
        assertTrue(DataValidator.validate(new MdUrlList.MdUrl(RandomString.nextWord(), url, r)));
        assertTrue(DataValidator.validate(mMdMocker.newUrl()));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMdMocker = new MdDataMocker(ReviewMocker.newReview().getId());
        mR = ReviewMocker.newReview().getId();
    }
}

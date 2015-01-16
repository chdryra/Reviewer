/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.DataValidator;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdFactList;
import com.chdryra.android.reviewer.MdImageList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.MdUrlList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.LatLngMocker;
import com.chdryra.android.testutils.RandomStringGenerator;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataValidatorTest extends TestCase {
    private MdDataMocker mMdMocker;
    private Review       mR;

    @SmallTest
    public void testValidateString() {
        assertFalse(DataValidator.validateString(null));
        assertFalse(DataValidator.validateString(""));
        assertTrue(DataValidator.validateString(RandomStringGenerator.nextWord()));
    }

    @SmallTest
    public void testNotNull() {
        assertFalse(DataValidator.NotNull(null));
        assertTrue(DataValidator.NotNull(this));
    }

    @SmallTest
    public void testValidateComment() {
        String comment = RandomStringGenerator.nextSentence();

        assertFalse(DataValidator.validate(new GvCommentList.GvComment()));
        assertFalse(DataValidator.validate(new GvCommentList.GvComment("")));
        assertTrue(DataValidator.validate(new GvCommentList.GvComment(comment)));
        assertTrue(DataValidator.validate(GvDataMocker.newComment()));

        Review r = ReviewMocker.newReview();
        assertFalse(DataValidator.validate(new MdCommentList.MdComment(null, r)));
        assertFalse(DataValidator.validate(new MdCommentList.MdComment("", r)));
        assertTrue(DataValidator.validate(new MdCommentList.MdComment(comment, r)));
        assertTrue(DataValidator.validate(mMdMocker.newComment()));
    }

    @SmallTest
    public void testValidateFact() {
        String label = RandomStringGenerator.nextWord();
        String value = RandomStringGenerator.nextWord();

        assertFalse(DataValidator.validate(new GvFactList.GvFact()));
        assertFalse(DataValidator.validate(new GvFactList.GvFact("", null)));
        assertFalse(DataValidator.validate(new GvFactList.GvFact(null, "")));
        assertFalse(DataValidator.validate(new GvFactList.GvFact("", "")));
        assertFalse(DataValidator.validate(new GvFactList.GvFact("", value)));
        assertFalse(DataValidator.validate(new GvFactList.GvFact(label, "")));
        assertTrue(DataValidator.validate(new GvFactList.GvFact(label, value)));
        assertTrue(DataValidator.validate(GvDataMocker.newFact()));

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
        String caption = RandomStringGenerator.nextSentence();
        LatLng latLng = LatLngMocker.newLatLng();

        assertFalse(DataValidator.validate(new GvImageList.GvImage()));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, null, null, true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, null, null, false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, latLng, null, true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, latLng, null, false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, null, caption, true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, null, caption, false)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, latLng, caption, true)));
        assertFalse(DataValidator.validate(new GvImageList.GvImage(null, latLng, caption, false)));

        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, null, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, null, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, latLng, null, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, latLng, null, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, caption, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, null, caption, false)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, latLng, caption, true)));
        assertTrue(DataValidator.validate(new GvImageList.GvImage(b, latLng, caption, false)));
        assertTrue(DataValidator.validate(GvDataMocker.newImage()));

        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, null, true, mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, null, false, mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, latLng, null, true, mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, latLng, null, false, mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, caption, true, mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, null, caption, false,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, latLng, caption, true,
                mR)));
        assertFalse(DataValidator.validate(new MdImageList.MdImage(null, latLng, caption, false,
                mR)));

        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, null, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, null, false, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, latLng, null, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, latLng, null, false, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, caption, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, null, caption, false, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, latLng, caption, true, mR)));
        assertTrue(DataValidator.validate(new MdImageList.MdImage(b, latLng, caption, false, mR)));
        assertTrue(DataValidator.validate(mMdMocker.newImage()));
    }

    @SmallTest
    public void testValidateLocation() {
        LatLng latLng = LatLngMocker.newLatLng();
        String name = RandomStringGenerator.nextWord();

        assertFalse(DataValidator.validate(new GvLocationList.GvLocation()));
        assertFalse(DataValidator.validate(new GvLocationList.GvLocation(latLng, null)));
        assertFalse(DataValidator.validate(new GvLocationList.GvLocation(null, "")));
        assertFalse(DataValidator.validate(new GvLocationList.GvLocation(latLng, "")));
        assertTrue(DataValidator.validate(new GvLocationList.GvLocation(latLng, name)));
        assertTrue(DataValidator.validate(GvDataMocker.newLocation()));

        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(null, null, mR)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(latLng, null, mR)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(null, "", mR)));
        assertFalse(DataValidator.validate(new MdLocationList.MdLocation(latLng, "", mR)));
        assertTrue(DataValidator.validate(new MdLocationList.MdLocation(latLng, name, mR)));
        assertTrue(DataValidator.validate(mMdMocker.newLocation()));
    }

    @SmallTest
    public void testValidateUrl() {
        URL url = GvDataMocker.newUrl().getUrl();

        assertFalse(DataValidator.validate(new GvUrlList.GvUrl()));
        assertTrue(DataValidator.validate(new GvUrlList.GvUrl(url)));
        assertTrue(DataValidator.validate(GvDataMocker.newUrl()));

        Review r = ReviewMocker.newReview();
        assertFalse(DataValidator.validate(new MdUrlList.MdUrl(null, r)));
        assertTrue(DataValidator.validate(new MdUrlList.MdUrl(url, r)));
        assertTrue(DataValidator.validate(mMdMocker.newUrl()));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMdMocker = new MdDataMocker<>(ReviewMocker.newReview());
        mR = ReviewMocker.newReview();
    }
}

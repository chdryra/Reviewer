/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.DataAdapterModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdUrlList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdGvConverterTest extends TestCase {
    private static final int NUM = 1;
    public MdDataMocker mMocker;

    @SmallTest
    public void testConvertCommentList() {
        MdCommentList mdData = mMocker.newCommentList(NUM);
        GvCommentList gvData = MdGvConverter.convert(mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());

        for (int i = 0; i < NUM; ++i) {
            MdCommentList.MdComment mdDatum = mdData.getItem(i);
            GvCommentList.GvComment gvDatum = gvData.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));

            assertEquals(mdDatum.getReviewId().toString(), gvDatum.getReviewId().getId());
            assertEquals(mdDatum.getComment(), gvDatum.getComment());
        }
    }

    @SmallTest
    public void testConvertFactList() {
        MdFactList mdData = mMocker.newFactList(NUM);
        GvFactList gvData = MdGvConverter.convert(mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());

        for (int i = 0; i < NUM; ++i) {
            MdFactList.MdFact mdDatum = mdData.getItem(i);
            GvFactList.GvFact gvDatum = gvData.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));

            assertEquals(mdDatum.getReviewId().toString(), gvDatum.getReviewId().getId());
            assertEquals(mdDatum.getLabel(), gvDatum.getLabel());
            assertEquals(mdDatum.getValue(), gvDatum.getValue());
        }
    }

    @SmallTest
    public void testConvertImageList() {
        MdImageList mdData = mMocker.newImageList(NUM);

        GvImageList gvData = MdGvConverter.convert(mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());

        for (int i = 0; i < NUM; ++i) {
            MdImageList.MdImage mdDatum = mdData.getItem(i);
            GvImageList.GvImage gvDatum = gvData.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));

            assertEquals(mdDatum.getReviewId().toString(), gvDatum.getReviewId().getId());
            assertEquals(mdDatum.getBitmap(), gvDatum.getBitmap());
            assertEquals(mdDatum.getCaption(), gvDatum.getCaption());
            assertEquals(mdDatum.isCover(), gvDatum.isCover());
        }
    }

    @SmallTest
    public void testConvertLocationList() {
        MdLocationList mdData = mMocker.newLocationList(NUM);
        GvLocationList gvData = MdGvConverter.convert(mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());

        for (int i = 0; i < NUM; ++i) {
            MdLocationList.MdLocation mdDatum = mdData.getItem(i);
            GvLocationList.GvLocation gvDatum = gvData.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));

            assertEquals(mdDatum.getReviewId().toString(), gvDatum.getReviewId().getId());
            assertEquals(mdDatum.getLatLng(), gvDatum.getLatLng());
            assertEquals(mdDatum.getName(), gvDatum.getName());
        }
    }

    @SmallTest
    public void testConvertUrlList() {
        MdUrlList mdData = mMocker.newUrlList(NUM);
        GvUrlList gvData = MdGvConverter.convert(mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());

        for (int i = 0; i < NUM; ++i) {
            MdUrlList.MdUrl mdDatum = mdData.getItem(i);
            GvUrlList.GvUrl gvDatum = gvData.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));

            assertEquals(mdDatum.getReviewId().toString(), gvDatum.getReviewId().getId());
            assertEquals(mdData.getItem(i).getUrl(), gvData.getItem(i).getUrl());
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMocker = new MdDataMocker(ReviewMocker.newReview().getId());
    }
}

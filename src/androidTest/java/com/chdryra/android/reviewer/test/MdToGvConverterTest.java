/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.DataValidator;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdFactList;
import com.chdryra.android.reviewer.MdImageList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.MdToGvConverter;
import com.chdryra.android.reviewer.MdUrlList;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdToGvConverterTest extends TestCase {
    private static final int NUM = 100;
    public MdDataMocker mMocker;

    @SmallTest
    public void testConvertCommentList() {
        MdCommentList mdData = mMocker.newCommentList(NUM);

        GvCommentList gvData = MdToGvConverter.convert(mdData);
        GvCommentList gvData2 = (GvCommentList) MdToGvConverter.convert(GvDataList.GvType
                .COMMENTS, mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());
        assertEquals(mdData.size(), gvData2.size());

        for (int i = 0; i < NUM; ++i) {
            MdCommentList.MdComment mdDatum = mdData.getItem(i);
            GvCommentList.GvComment gvDatum = gvData.getItem(i);
            GvCommentList.GvComment gvDatum2 = gvData2.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));
            assertTrue(DataValidator.validate(gvDatum2));

            assertEquals(mdData.getItem(i).getComment(), gvData.getItem(i).getComment());
            assertEquals(mdData.getItem(i).getComment(), gvData2.getItem(i).getComment());
        }
    }

    @SmallTest
    public void testConvertFactList() {
        MdFactList mdData = mMocker.newFactList(NUM);
        GvFactList gvData = MdToGvConverter.convert(mdData);
        GvFactList gvData2 = (GvFactList) MdToGvConverter.convert(GvDataList.GvType.FACTS, mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());
        assertEquals(mdData.size(), gvData2.size());

        for (int i = 0; i < NUM; ++i) {
            MdFactList.MdFact mdDatum = mdData.getItem(i);
            GvFactList.GvFact gvDatum = gvData.getItem(i);
            GvFactList.GvFact gvDatum2 = gvData2.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));
            assertTrue(DataValidator.validate(gvDatum2));

            assertEquals(mdData.getItem(i).getLabel(), gvData.getItem(i).getLabel());
            assertEquals(mdData.getItem(i).getLabel(), gvData2.getItem(i).getLabel());
            assertEquals(mdData.getItem(i).getValue(), gvData.getItem(i).getValue());
            assertEquals(mdData.getItem(i).getValue(), gvData2.getItem(i).getValue());
        }
    }

    @SmallTest
    public void testConvertImageList() {
        MdImageList mdData = mMocker.newImageList(NUM);

        GvImageList gvData = MdToGvConverter.convert(mdData);
        GvImageList gvData2 = (GvImageList) MdToGvConverter.convert(GvDataList.GvType.IMAGES,
                mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());
        assertEquals(mdData.size(), gvData2.size());

        for (int i = 0; i < NUM; ++i) {
            MdImageList.MdImage mdDatum = mdData.getItem(i);
            GvImageList.GvImage gvDatum = gvData.getItem(i);
            GvImageList.GvImage gvDatum2 = gvData2.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));
            assertTrue(DataValidator.validate(gvDatum2));

            assertEquals(mdData.getItem(i).getBitmap(), gvData.getItem(i).getBitmap());
            assertEquals(mdData.getItem(i).getBitmap(), gvData2.getItem(i).getBitmap());
            assertEquals(mdData.getItem(i).getCaption(), gvData.getItem(i).getCaption());
            assertEquals(mdData.getItem(i).getCaption(), gvData2.getItem(i).getCaption());
            assertEquals(mdData.getItem(i).getLatLng(), gvData.getItem(i).getLatLng());
            assertEquals(mdData.getItem(i).isCover(), gvData.getItem(i).isCover());
            assertEquals(mdData.getItem(i).isCover(), gvData2.getItem(i).isCover());
        }
    }

    @SmallTest
    public void testConvertLocationList() {
        MdLocationList mdData = mMocker.newLocationList(NUM);
        GvLocationList gvData = MdToGvConverter.convert(mdData);
        GvLocationList gvData2 = (GvLocationList) MdToGvConverter.convert(GvDataList.GvType
                .LOCATIONS, mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());
        assertEquals(mdData.size(), gvData2.size());

        for (int i = 0; i < NUM; ++i) {
            MdLocationList.MdLocation mdDatum = mdData.getItem(i);
            GvLocationList.GvLocation gvDatum = gvData.getItem(i);
            GvLocationList.GvLocation gvDatum2 = gvData2.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));
            assertTrue(DataValidator.validate(gvDatum2));

            assertEquals(mdData.getItem(i).getLatLng(), gvData.getItem(i).getLatLng());
            assertEquals(mdData.getItem(i).getLatLng(), gvData2.getItem(i).getLatLng());
            assertEquals(mdData.getItem(i).getName(), gvData.getItem(i).getName());
            assertEquals(mdData.getItem(i).getName(), gvData2.getItem(i).getName());
        }
    }

    @SmallTest
    public void testConvertUrlList() {
        MdUrlList mdData = mMocker.newUrlList(NUM);
        GvUrlList gvData = MdToGvConverter.convert(mdData);
        GvUrlList gvData2 = (GvUrlList) MdToGvConverter.convert(GvDataList.GvType.URLS, mdData);

        assertEquals(NUM, mdData.size());
        assertEquals(mdData.size(), gvData.size());
        assertEquals(mdData.size(), gvData2.size());

        for (int i = 0; i < NUM; ++i) {
            MdUrlList.MdUrl mdDatum = mdData.getItem(i);
            GvUrlList.GvUrl gvDatum = gvData.getItem(i);
            GvUrlList.GvUrl gvDatum2 = gvData.getItem(i);

            assertTrue(DataValidator.validate(mdDatum));
            assertTrue(DataValidator.validate(gvDatum));
            assertTrue(DataValidator.validate(gvDatum2));

            assertEquals(mdData.getItem(i).getUrl(), gvData.getItem(i).getUrl());
            assertEquals(mdData.getItem(i).getUrl(), gvData2.getItem(i).getUrl());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMocker = new MdDataMocker<>(ReviewMocker.newReview());
    }
}

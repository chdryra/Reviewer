/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvUrlListTest extends TestCase {
    private static final int NUM = 50;
    private GvUrlList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvUrlList.GvUrl.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newUrl(null));
        ParcelableTester.testParcelable(GvDataMocker.newUrl(RandomReviewId.nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newUrlList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newUrlList(10, true));
    }

    @SmallTest
    public void testGvUrl() {
        GvUrlList.GvUrl testUrl1 = GvDataMocker.newUrl(null);
        GvUrlList.GvUrl testUrl2 = GvDataMocker.newUrl(null);

        String url1label = testUrl1.getLabel();
        String url2label = testUrl2.getLabel();
        URL url1 = testUrl1.getUrl();
        URL url2 = testUrl2.getUrl();

        GvUrlList.GvUrl gvUrl = new GvUrlList.GvUrl(url1label, url1);
        GvUrlList.GvUrl gvUrlEquals = new GvUrlList.GvUrl(url1label, url1);
        GvUrlList.GvUrl gvUrlEquals2 = new GvUrlList.GvUrl(gvUrl);
        GvUrlList.GvUrl gvUrlNotEquals = new GvUrlList.GvUrl(url2label, url2);
        GvUrlList.GvUrl gvUrlNotEquals2 = new GvUrlList.GvUrl(RandomReviewId.nextGvReviewId(),
                url1label, url1);
        GvUrlList.GvUrl gvUrlNull = new GvUrlList.GvUrl();

        assertNotNull(gvUrl.getViewHolder());
        assertTrue(gvUrl.isValidForDisplay());

        assertEquals(url1, gvUrl.getUrl());

        assertTrue(gvUrl.equals(gvUrlEquals));
        assertTrue(gvUrl.equals(gvUrlEquals2));
        assertFalse(gvUrl.equals(gvUrlNotEquals));
        assertFalse(gvUrl.equals(gvUrlNotEquals2));

        assertFalse(gvUrlNull.isValidForDisplay());

        URL url = gvUrl.getUrl();
        String urlString = url.toExternalForm();
        assertTrue(urlString.contains("://"));
        String shortened = gvUrl.getValue();
        assertFalse(shortened.contains("://"));
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newUrlList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterionList.GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTagList.GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.GvUrl.TYPE, NUM)));

        GvUrlList list = new GvUrlList();
        GvUrlList list2 = new GvUrlList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addList(mList);
        list2.addList(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvUrlList();
    }

}

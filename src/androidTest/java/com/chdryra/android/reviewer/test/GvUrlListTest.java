/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvUrlListTest extends TestCase {
    private GvUrlList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvUrlList.TYPE, mList.getGvType());
    }

    @SmallTest
    public void testGvUrl() {
        URL url1 = GvDataMocker.newUrl().getUrl();
        URL url2 = GvDataMocker.newUrl().getUrl();

        GvUrlList.GvUrl gvUrl = new GvUrlList.GvUrl(url1);
        GvUrlList.GvUrl gvUrlEquals = new GvUrlList.GvUrl(url1);
        GvUrlList.GvUrl gvUrlNotEquals = new GvUrlList.GvUrl(url2);
        GvUrlList.GvUrl gvUrlNull = new GvUrlList.GvUrl();

        assertNotNull(gvUrl.newViewHolder());
        assertTrue(gvUrl.isValidForDisplay());

        assertEquals(url1, gvUrl.getUrl());

        assertTrue(gvUrl.equals(gvUrlEquals));
        assertFalse(gvUrl.equals(gvUrlNotEquals));

        assertFalse(gvUrlNull.isValidForDisplay());

        URL url = gvUrl.getUrl();
        String urlString = url.toExternalForm();
        assertTrue(urlString.contains("://"));
        String shortened = gvUrl.toShortenedString();
        assertFalse(shortened.contains("://"));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvUrlList();
    }

}

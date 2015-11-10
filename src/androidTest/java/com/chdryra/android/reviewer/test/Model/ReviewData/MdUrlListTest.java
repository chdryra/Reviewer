/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;
import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Model.ReviewData.MdUrlList;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdUrlListTest extends TestCase {
    private static final MdReviewId ID = RandomReviewId.nextId();
    private URL mUrl;

    @SmallTest
    public void testMdUrlHasData() {
        String label = RandomString.nextWord();

        MdUrlList.MdUrl url = new MdUrlList.MdUrl(ID, null, mUrl);
        assertFalse(url.hasData());
        url = new MdUrlList.MdUrl(ID, "", mUrl);
        assertFalse(url.hasData());
        url = new MdUrlList.MdUrl(ID, label, mUrl);
        assertTrue(url.hasData());
    }

    @SmallTest
    public void testMdUrlGetters() {
        String label = RandomString.nextWord();
        MdUrlList.MdUrl url = new MdUrlList.MdUrl(ID, label, mUrl);
        assertEquals(label, url.getLabel());
        assertEquals(mUrl, url.getUrl());
        assertEquals(mUrl.toExternalForm(), url.getValue());
        assertEquals(ID, url.getReviewId());
    }

    @SmallTest
    public void testIsUrl() {
        String label = RandomString.nextWord();
        MdUrlList.MdUrl url = new MdUrlList.MdUrl(ID, label, mUrl);
        assertTrue(url.isUrl());
    }

    @SmallTest
    public void testMdUrlEqualsHash() {
        String label1 = RandomString.nextWord();
        URL link1 = mUrl;
        String label2 = RandomString.nextWord();
        URL link2 = null;
        try {
            link2 = new URL(URLUtil.guessUrl("www.apple.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail();
        }

        MdReviewId id2 = RandomReviewId.nextId();

        MdUrlList.MdUrl url1 = new MdUrlList.MdUrl(ID, label1, link1);

        MdDataUtils.testEqualsHash(url1, new MdUrlList.MdUrl(ID, label1, link2), false);
        MdDataUtils.testEqualsHash(url1, new MdUrlList.MdUrl(ID, label2, link1), false);
        MdDataUtils.testEqualsHash(url1, new MdUrlList.MdUrl(id2, label1, link1), false);
        MdDataUtils.testEqualsHash(url1, new MdUrlList.MdUrl(ID, label1, link1), true);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        try {
            mUrl = new URL(URLUtil.guessUrl("www.google.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail();
        }
    }
}

/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 11 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.PublishDate;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 11/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishDateTest extends TestCase {

    @SmallTest
    public void testNow() {
        assertNotNull(PublishDate.now());
    }

    @SmallTest
    public void testThen() {
        PublishDate now = PublishDate.now();
        long time = now.getTime();
        long time_100 = time - 100;
        long time100 = time + 100;

        PublishDate then = PublishDate.then(time);
        assertNotNull(then);
        assertEquals(now, then);

        then = PublishDate.then(time_100);
        assertNotNull(then);
        assertEquals(now.getTime() - 100, then.getTime());

        try {
            PublishDate.then(time100);
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Publish date must not be in the future!", e.getMessage());
        }
    }

    @SmallTest
    public void testGetDate() {
        Date t0 = new Date();
        PublishDate now = PublishDate.now();
        Date t1 = new Date();
        assertTrue(t0.before(now.getDate()) || t0.equals(now.getDate()));
        assertTrue(t1.after(now.getDate()) || t1.equals(now.getDate()));
    }

    @SmallTest
    public void testGetTime() {
        long t0 = new Date().getTime();
        PublishDate now = PublishDate.now();
        long t1 = new Date().getTime();
        assertTrue(t0 <= now.getTime() && now.getTime() <= t1);
    }
}

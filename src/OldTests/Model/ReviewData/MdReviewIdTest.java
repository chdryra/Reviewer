/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.startouch.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.PublishDate;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewStamp;
import com.chdryra.android.startouch.test.TestUtils.RandomAuthor;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdReviewIdTest extends TestCase {

    @SmallTest
    public void testReviewId() {
        DatumAuthor author = RandomAuthor.nextAuthor();
        PublishDate date = PublishDate.now();
        ReviewStamp publisher = new ReviewStamp(author, date);
        MdReviewId id1 = MdReviewId.newId(publisher);
        assertNotNull(id1);
        assertTrue(id1.hasData());
        assertEquals(id1, id1.getReviewId());

        MdReviewId id2 = MdReviewId.newId(publisher);
        assertNotNull(id2);

        assertFalse(id1.equals(id2));

        MdReviewId id3 = MdReviewId.fromString(id2.toString());
        assertNotNull(id3);
        assertFalse(id1.equals(id3));
        assertTrue(id2.equals(id3));
    }
}

/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewIdTest extends TestCase {

    @SmallTest
    public void testReviewId() {
        ReviewId id1 = ReviewId.generateId(RandomAuthor.nextAuthor());
        assertNotNull(id1);
        assertTrue(id1.hasData());
        assertEquals(id1, id1.getReviewId());

        ReviewId id2 = ReviewId.generateId(RandomAuthor.nextAuthor());
        assertNotNull(id2);

        assertFalse(id1.equals(id2));

        ReviewId id3 = ReviewId.fromString(id2.toString());
        assertNotNull(id3);
        assertFalse(id1.equals(id3));
        assertTrue(id2.equals(id3));

        //Test concurrency i.e. if called potentially within 1ms, id is still unique.
        Author author1 = RandomAuthor.nextAuthor();
        Author author2 = RandomAuthor.nextAuthor();
        int num = 10;
        ReviewId[] ids_1 = new ReviewId[num];
        ReviewId[] ids_2 = new ReviewId[num];
        for (int i = 0; i < num; ++i) {
            ids_1[i] = ReviewId.generateId(author1);
            ids_2[i] = ReviewId.generateId(author2);
        }

        for (int i = 0; i < ids_1.length; ++i) {
            for (int j = i + 1; j < ids_1.length; ++j) {
                assertFalse(ids_1[i].equals(ids_1[j]));
            }
        }
        for (int i = 0; i < ids_2.length; ++i) {
            for (int j = i + 1; j < ids_2.length; ++j) {
                assertFalse(ids_2[i].equals(ids_2[j]));
            }
        }
    }
}

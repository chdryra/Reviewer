/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCommentListTest extends TestCase {
    private static final ReviewId ID = ReviewId.generateId();

    @SmallTest
    public void testMdCommentHasData() {
        MdCommentList.MdComment comment = new MdCommentList.MdComment(null, false, ID);
        assertFalse(comment.hasData());
        comment = new MdCommentList.MdComment("", false, ID);
        assertFalse(comment.hasData());
        comment = new MdCommentList.MdComment(RandomString.nextSentence(), false, ID);
        assertTrue(comment.hasData());
    }

    @SmallTest
    public void testMdCommentGetters() {
        String sentence = RandomString.nextSentence();
        MdCommentList.MdComment comment = new MdCommentList.MdComment(sentence, false, ID);
        assertEquals(sentence, comment.getComment());
        assertEquals(ID, comment.getReviewId());
    }

    @SmallTest
    public void testMdCommentIsHeadline() {
        String sentence = RandomString.nextSentence();
        MdCommentList.MdComment comment = new MdCommentList.MdComment(sentence, false, ID);
        assertFalse(comment.isHeadline());
        comment = new MdCommentList.MdComment(sentence, true, ID);
        assertTrue(comment.isHeadline());
    }

    @SmallTest
    public void testMdCommentEqualsHash() {
        String string1 = RandomString.nextSentence();
        String string2 = RandomString.nextSentence();
        ReviewId id2 = ReviewId.generateId();

        MdCommentList.MdComment comment1 = new MdCommentList.MdComment(string1, true, ID);

        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(string1, true, id2),
                false);
        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(string1, false, ID),
                false);
        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(string2, true, ID), false);
        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(string1, true, ID), true);
    }
}

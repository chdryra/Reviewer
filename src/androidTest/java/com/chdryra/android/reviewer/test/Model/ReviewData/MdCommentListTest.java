/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCommentListTest extends TestCase {
    private static final MdReviewId ID = RandomReviewId.nextId();

    @SmallTest
    public void testMdCommentHasData() {
        MdCommentList.MdComment comment = new MdCommentList.MdComment(ID, null, false);
        assertFalse(comment.hasData());
        comment = new MdCommentList.MdComment(ID, "", false);
        assertFalse(comment.hasData());
        comment = new MdCommentList.MdComment(ID, RandomString.nextSentence(), false);
        assertTrue(comment.hasData());
    }

    @SmallTest
    public void testMdCommentGetters() {
        String sentence = RandomString.nextSentence();
        MdCommentList.MdComment comment = new MdCommentList.MdComment(ID, sentence, false);
        assertEquals(sentence, comment.getComment());
        assertEquals(ID, comment.getReviewId());
    }

    @SmallTest
    public void testMdCommentIsHeadline() {
        String sentence = RandomString.nextSentence();
        MdCommentList.MdComment comment = new MdCommentList.MdComment(ID, sentence, false);
        assertFalse(comment.isHeadline());
        comment = new MdCommentList.MdComment(ID, sentence, true);
        assertTrue(comment.isHeadline());
    }

    @SmallTest
    public void testMdCommentEqualsHash() {
        String string1 = RandomString.nextSentence();
        String string2 = RandomString.nextSentence();
        MdReviewId id2 = RandomReviewId.nextId();

        MdCommentList.MdComment comment1 = new MdCommentList.MdComment(ID, string1, true);

        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(id2, string1, true),
                false);
        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(ID, string1, false),
                false);
        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(ID, string2, true), false);
        MdDataUtils.testEqualsHash(comment1, new MdCommentList.MdComment(ID, string1, true), true);
    }
}

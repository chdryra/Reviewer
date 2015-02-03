/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.RandomStringGenerator;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCommentListTest extends TestCase {
    private static final int NUM = 50;
    private GvCommentList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvCommentList.TYPE, mList.getGvType());
    }

    @SmallTest
    public void testGvComment() {
        String comment1 = GvDataMocker.newComment().getComment();
        String comment2 = GvDataMocker.newComment().getComment();

        GvCommentList.GvComment gvComment = new GvCommentList.GvComment(comment1);
        GvCommentList.GvComment gvCommentEquals = new GvCommentList.GvComment(comment1);
        GvCommentList.GvComment gvCommentNotEquals = new GvCommentList.GvComment(comment2);
        GvCommentList.GvComment gvCommentNull = new GvCommentList.GvComment();
        GvCommentList.GvComment gvCommentEmpty = new GvCommentList.GvComment("");

        assertNotNull(gvComment.newViewHolder());
        assertTrue(gvComment.isValidForDisplay());

        assertEquals(comment1, gvComment.getComment());

        assertTrue(gvComment.equals(gvCommentEquals));
        assertFalse(gvComment.equals(gvCommentNotEquals));

        assertFalse(gvCommentNull.isValidForDisplay());
        assertFalse(gvCommentEmpty.isValidForDisplay());
    }


    @SmallTest
    public void testSplitUnsplitHeadlineComments() {
        RandomStringGenerator generator = new RandomStringGenerator();
        String paragraph = generator.nextParagraph();
        String[] sentences = generator.getSentencesForParagraph();
        assertTrue(sentences.length > 0);

        GvCommentList.GvComment parent = (new GvCommentList.GvComment(paragraph));
        mList.add(parent);

        //Test getCommentHeadline()
        String first = sentences[0];
        String headline = parent.getCommentHeadline();
        if (first.endsWith(".")) headline += ".";
        assertEquals(first, headline);

        //Test split
        GvCommentList split = mList.getSplitComments();
        assertEquals(sentences.length, split.size());
        for (int i = 1; i < sentences.length; ++i) {
            String one = sentences[i];
            String two = split.getItem(i).getComment();
            if (one.endsWith(".")) two += ".";
            assertEquals(one, two);

            //Test unsplit
            assertEquals(parent, split.getItem(i).getUnSplitComment());
        }

        String paragraph2 = generator.nextParagraph();
        String[] sentences2 = generator.getSentencesForParagraph();
        assertTrue(sentences2.length > 0);

        GvCommentList.GvComment parent2 = new GvCommentList.GvComment(paragraph2);
        mList.add(parent2);

        //Test getCommentHeadline()
        first = sentences2[0];
        headline = parent2.getCommentHeadline();
        if (first.endsWith(".")) headline += ".";
        assertEquals(first, headline);

        //Test split is a concatenation of both paragraph sentences
        split = mList.getSplitComments();
        String[] both = ArrayUtils.addAll(sentences, sentences2);
        assertEquals(sentences.length + sentences2.length, both.length);
        assertEquals(both.length, split.size());
        for (int i = 0; i < both.length; ++i) {
            String one = both[i];
            String two = split.getItem(i).getComment();
            if (one.endsWith(".")) two += ".";
            assertEquals(one, two);

            //Test unsplit remains correct for either paragraph
            if (i < sentences.length) {
                assertEquals(parent, split.getItem(i).getUnSplitComment());
            } else {
                assertEquals(parent2, split.getItem(i).getUnSplitComment());
            }
        }
    }

    @SmallTest
    public void testEquals() {
        mList.add(GvDataMocker.newCommentList(NUM));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.CHILDREN, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.TAGS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.LOCATIONS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.COMMENTS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.FACTS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.IMAGES, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.URLS, NUM)));

        GvCommentList list = new GvCommentList();
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        list.add(mList);
        assertFalse(mList.equals(list));
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvCommentList();
    }
}

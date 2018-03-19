/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 January, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.ParcelableTester;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomString;

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
        assertEquals(GvComment.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newComment(null));
        ParcelableTester.testParcelable(GvDataMocker.newComment(RandomReviewId
                .nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newCommentList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newCommentList(10, true));
    }

    @SmallTest
    public void testGvComment() {
        String comment1 = GvDataMocker.newComment(null).getComment();
        String comment2 = GvDataMocker.newComment(null).getComment();

        GvComment gvComment = new GvComment(comment1);
        GvComment gvCommentEquals = new GvComment(comment1);
        GvComment gvCommentEquals2 = new GvComment(gvComment);
        GvComment gvCommentNotEquals = new GvComment(comment2);
        GvComment gvCommentNotEqual2 = new GvComment(RandomReviewId
                .nextGvReviewId(), comment1);
        GvComment gvCommentNull = new GvComment();
        GvComment gvCommentEmpty = new GvComment("");

        assertNotNull(gvComment.getViewHolder());
        assertTrue(gvComment.isValidForDisplay());

        assertEquals(comment1, gvComment.getComment());

        assertTrue(gvComment.equals(gvCommentEquals));
        assertTrue(gvComment.equals(gvCommentEquals2));
        assertFalse(gvComment.equals(gvCommentNotEquals));
        assertFalse(gvComment.equals(gvCommentNotEqual2));

        assertFalse(gvCommentNull.isValidForDisplay());
        assertFalse(gvCommentEmpty.isValidForDisplay());
    }


    @SmallTest
    public void testSplitUnsplitHeadlineComments() {
        RandomString generator = new RandomString();
        String paragraph = generator.nextParagraph();
        String[] sentences = generator.getSentencesForParagraph();
        assertTrue(sentences.length > 0);

        GvComment parent = (new GvComment(paragraph));
        mList.add(parent);

        //Test getCommentHeadline()
        String first = sentences[0];
        String headline = parent.getHeadline();
        if (first.endsWith("")) headline += "";
        assertEquals(first, headline);

        //Test split
        GvCommentList split = mList.getSplitComments();
        assertEquals(sentences.length, split.size());
        for (int i = 1; i < sentences.length; ++i) {
            String one = sentences[i];
            String two = split.getItem(i).getComment();
            if (one.endsWith("")) two += "";
            assertEquals(one, two);

            //Test unsplit
            assertEquals(parent, split.getItem(i).getUnsplitComment());
        }

        String paragraph2 = generator.nextParagraph();
        String[] sentences2 = generator.getSentencesForParagraph();
        assertTrue(sentences2.length > 0);

        GvComment parent2 = new GvComment(paragraph2);
        mList.add(parent2);

        //Test getCommentHeadline()
        first = sentences2[0];
        headline = parent2.getHeadline();
        if (first.endsWith("")) headline += "";
        assertEquals(first, headline);

        //Test split is a concatenation of both paragraph sentences
        split = mList.getSplitComments();
        String[] both = ArrayUtils.addAll(sentences, sentences2);
        assertEquals(sentences.length + sentences2.length, both.length);
        assertEquals(both.length, split.size());
        for (int i = 0; i < both.length; ++i) {
            String one = both[i];
            String two = split.getItem(i).getComment();
            if (one.endsWith("")) two += "";
            assertEquals(one, two);

            //Test unsplit remains correct for either paragraph
            if (i < sentences.length) {
                assertEquals(parent, split.getItem(i).getUnsplitComment());
            } else {
                assertEquals(parent2, split.getItem(i).getUnsplitComment());
            }
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newCommentList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvCommentList list = new GvCommentList();
        GvCommentList list2 = new GvCommentList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addAll(mList);
        list2.addAll(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    @SmallTest
    public void testSort() {
        mList.addAll(GvDataMocker.newCommentList(100, false));
        assertFalse(isSorted());
        mList.sort();
        assertTrue(isSorted());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvCommentList();
    }

    //private methods
    private boolean isSorted() {
        assertTrue(mList.size() > 0);
        boolean isSorted = true;
        for (int i = 0; i < mList.size() - 1; ++i) {
            GvComment before = mList.getItem(i);
            GvComment after = mList.getItem(i + 1);

            if (!before.isHeadline() && after.isHeadline()) {
                isSorted = false;
                break;
            }
        }

        return isSorted;
    }
}

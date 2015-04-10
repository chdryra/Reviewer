/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.ReviewerDbRow;
import com.chdryra.android.reviewer.Database.RowAuthor;
import com.chdryra.android.reviewer.Database.RowComment;
import com.chdryra.android.reviewer.Database.RowFact;
import com.chdryra.android.reviewer.Database.RowImage;
import com.chdryra.android.reviewer.Database.RowLocation;
import com.chdryra.android.reviewer.Database.RowReview;
import com.chdryra.android.reviewer.Database.RowReviewNode;
import com.chdryra.android.reviewer.Database.RowTag;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRowTest extends TestCase {
    private MdDataMocker mMocker;

    @SmallTest
    public void testEmptyRow() {
        assertNotNull(ReviewerDbRow.emptyRow(RowAuthor.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowComment.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowFact.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowImage.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowLocation.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowReview.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowReviewNode.class));
        assertNotNull(ReviewerDbRow.emptyRow(RowTag.class));
    }

    @SmallTest
    public void testNewRowReviewNode() {
        assertNotNull(ReviewerDbRow.newRow(ReviewMocker.newReviewNode()));
    }

    @SmallTest
    public void testNewRowReview() {
        assertNotNull(ReviewerDbRow.newRow(ReviewMocker.newReview()));
    }

    @SmallTest
    public void testNewRowAuthor() {
        assertNotNull(ReviewerDbRow.newRow(RandomAuthor.nextAuthor()));
    }

    @SmallTest
    public void testNewRowComment() {
        assertNotNull(ReviewerDbRow.newRow(mMocker.newComment(), 0));
    }

    @SmallTest
    public void testNewRowFact() {
        assertNotNull(ReviewerDbRow.newRow(mMocker.newFact(), 0));
        assertNotNull(ReviewerDbRow.newRow(mMocker.newUrl(), 0));
    }

    @SmallTest
    public void testNewRowLocation() {
        assertNotNull(ReviewerDbRow.newRow(mMocker.newLocation(), 0));
    }

    @SmallTest
    public void testNewRowImage() {
        assertNotNull(ReviewerDbRow.newRow(mMocker.newImage(), 0));
    }

    @Override
    protected void setUp() throws Exception {
        mMocker = new MdDataMocker(ReviewId.generateId());
    }
}

/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 April, 2015
 */

package com.chdryra.android.reviewer.test.Database;

import android.database.MatrixCursor;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Database.Factories.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;
import com.chdryra.android.reviewer.Database.Interfaces.RowLocation;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.test.TestUtils.MdDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbTableRowTest extends TestCase {
    private MdDataMocker mMocker;

    @SmallTest
    public void testEmptyRow() {
        assertNotNull(FactoryDbTableRow.emptyRow(RowAuthor.class));
        assertNotNull(FactoryDbTableRow.emptyRow(RowComment.class));
        assertNotNull(FactoryDbTableRow.emptyRow(RowFact.class));
        assertNotNull(FactoryDbTableRow.emptyRow(RowImage.class));
        assertNotNull(FactoryDbTableRow.emptyRow(RowLocation.class));
        assertNotNull(FactoryDbTableRow.emptyRow(RowReview.class));
        assertNotNull(FactoryDbTableRow.emptyRow(RowTag.class));
    }

    @SmallTest
    public void testStaticNewRow() {
        //Only checking one to see if logic flows through...
        Review review = ReviewMocker.newReview();
        MdReviewId parentId = RandomReviewId.nextId();
        String[] cols = new String[]{RowReview.REVIEW_ID, RowReview.PARENT_ID, RowReview.AUTHOR_ID,
                RowReview.PUBLISH_DATE, RowReview.SUBJECT, RowReview.RATING, RowReview.IS_AVERAGE};

        MatrixCursor cursor = new MatrixCursor(cols);
        String reviewId = review.getMdReviewId().toString();
        String authorId = review.getAuthor().getUserId().toString();
        cursor.addRow(new Object[]{reviewId, parentId, authorId, review.getPublishDate().getTime(),
                review.getSubject().getSubject(), review.getRating().getRating(), review
                .isRatingAverageOfCriteria() ? 1 : 0});
        cursor.moveToFirst();
        assertNotNull(FactoryDbTableRow.newRow(cursor, RowReview.class));
    }

    @SmallTest
    public void testNewRowReview() {
        assertNotNull(FactoryDbTableRow.newRow(ReviewMocker.newReview()));
    }

    @SmallTest
    public void testNewRowCriterion() {
        MdCriterion criterion =
                new MdCriterion(RandomReviewId.nextId(), ReviewMocker.newReview());
        assertNotNull(FactoryDbTableRow.newRow(criterion));
    }

    @SmallTest
    public void testNewRowAuthor() {
        assertNotNull(FactoryDbTableRow.newRow(RandomAuthor.nextAuthor()));
    }

    @SmallTest
    public void testNewRowComment() {
        assertNotNull(FactoryDbTableRow.newRow(mMocker.newComment(), 0));
    }

    @SmallTest
    public void testNewRowFact() {
        assertNotNull(FactoryDbTableRow.newRow(mMocker.newFact(), 0));
        assertNotNull(FactoryDbTableRow.newRow(mMocker.newUrl(), 0));
    }

    @SmallTest
    public void testNewRowLocation() {
        assertNotNull(FactoryDbTableRow.newRow(mMocker.newLocation(), 0));
    }

    @SmallTest
    public void testNewRowImage() {
        assertNotNull(FactoryDbTableRow.newRow(mMocker.newImage(), 0));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mMocker = new MdDataMocker();
    }
}

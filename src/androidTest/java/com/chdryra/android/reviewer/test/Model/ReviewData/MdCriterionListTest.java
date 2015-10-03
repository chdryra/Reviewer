package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCriterionListTest extends TestCase {
    private static final ReviewId ID = RandomReviewId.nextId();

    @SmallTest
    public void testMdCriterionHasData() {
        Review review = ReviewMocker.newReview();
        ReviewId parentId = RandomReviewId.nextId();
        MdCriterionList.MdCriterion criterion = new MdCriterionList.MdCriterion(review, parentId);
        assertTrue(criterion.hasData());
        criterion = new MdCriterionList.MdCriterion(null, parentId);
        assertFalse(criterion.hasData());
        criterion = new MdCriterionList.MdCriterion(review, null);
        assertFalse(criterion.hasData());
        criterion = new MdCriterionList.MdCriterion(null, null);
        assertFalse(criterion.hasData());
    }

    @SmallTest
    public void testMdCriterionGetters() {
        Review review = ReviewMocker.newReview();
        ReviewId parentId = RandomReviewId.nextId();
        MdCriterionList.MdCriterion criterion = new MdCriterionList.MdCriterion(review, parentId);

        assertEquals(review.getSubject().get(), criterion.getSubject());
        assertEquals(review.getRating().getValue(), criterion.getRating());
        assertEquals(review, criterion.getReview());
        assertEquals(parentId, criterion.getReviewId());
    }

    @SmallTest
    public void testMdCriterionEqualsHash() {
        Review review1 = ReviewMocker.newReview();
        Review review2 = ReviewMocker.newReview();
        ReviewId parentId1 = RandomReviewId.nextId();
        ReviewId parentId2 = RandomReviewId.nextId();

        MdCriterionList.MdCriterion criterion1 = new MdCriterionList.MdCriterion(review1,
                parentId1);
        MdCriterionList.MdCriterion criterion1Copy = new MdCriterionList.MdCriterion(review1,
                parentId1);
        MdCriterionList.MdCriterion criterion12 = new MdCriterionList.MdCriterion(review1,
                parentId2);
        MdCriterionList.MdCriterion criterion21 = new MdCriterionList.MdCriterion(review2,
                parentId1);
        MdCriterionList.MdCriterion criterion2 = new MdCriterionList.MdCriterion(review2,
                parentId2);

        MdDataUtils.testEqualsHash(criterion1, criterion1Copy, true);
        MdDataUtils.testEqualsHash(criterion1, criterion12, false);
        MdDataUtils.testEqualsHash(criterion1, criterion21, false);
        MdDataUtils.testEqualsHash(criterion1, criterion2, false);
        MdDataUtils.testEqualsHash(criterion2, criterion2, true);
    }

    @SmallTest
    public void testConstructor() {
        final int num = 3;
        IdableList<Review> criteria = new IdableList<>();
        for (int i = 0; i < num; ++i) {
            criteria.add(ReviewMocker.newReview());
        }
        ReviewId parentId = RandomReviewId.nextId();

        MdCriterionList list = new MdCriterionList(criteria, parentId);
        assertEquals(num, list.size());
        for (int i = 0; i < 3; ++i) {
            MdCriterionList.MdCriterion criterion = list.getItem(i);
            assertEquals(parentId, criterion.getReviewId());
            assertEquals(criteria.getItem(i), criterion.getReview());
        }
    }
}
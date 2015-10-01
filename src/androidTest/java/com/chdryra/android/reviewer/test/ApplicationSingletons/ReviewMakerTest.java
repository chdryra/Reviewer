package com.chdryra.android.reviewer.test.ApplicationSingletons;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewNodeProvider;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;
import com.chdryra.android.testutils.RandomString;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 23/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMakerTest extends InstrumentationTestCase {
    private static final int NUM = 3;
    ReviewNode mFeed;
    Context mContext;

    @Override
    protected void setUp() throws Exception {
        TestDatabase.recreateDatabase(getInstrumentation());
        mContext = getInstrumentation().getTargetContext();
        mFeed = ReviewNodeProvider.getReviewNode(mContext);
        assertTrue(mFeed.getChildren().size() > 0);
    }

    @Override
    protected void tearDown() throws Exception {
        TestDatabase.deleteDatabase(getInstrumentation());
    }

    @SmallTest
    public void testCreateMetaReviewReviewList() {
        IdableList<Review> reviews = new IdableList<>();
        float averageRating = 0f;
        for (int i = 0; i < NUM; ++i) {
            Review review = ReviewMocker.newReview();
            reviews.add(review);
            averageRating += review.getRating().get() / NUM;
        }
        String subject = RandomString.nextWord();

        ReviewNode meta = ReviewMaker.createMetaReview(mContext, reviews, subject);

        assertEquals(subject, meta.getSubject().get());
        assertEquals(averageRating, meta.getRating().get());
        IdableList<ReviewNode> children = meta.getChildren();
        assertEquals(NUM, children.size());
        for (int i = 0; i < NUM; ++i) {
            assertEquals(reviews.getItem(i), children.getItem(i).getReview());
        }
    }

    @SmallTest
    public void testCreateMetaReviewDataList() {
        assertTrue(NUM > 2);
        IdableList<ReviewNode> reviews = mFeed.getChildren();
        String subject = RandomString.nextWord();

        Random rand = new Random();
        int index1 = rand.nextInt(reviews.size());
        int index2 = index1;
        while (index2 == index1) index2 = rand.nextInt(reviews.size());
        Review review1 = reviews.getItem(index1);
        Review review2 = reviews.getItem(index2);
        MdCommentList comments1 = review1.getComments();
        MdCommentList comments2 = review2.getComments();
        MdCommentList ofInterest = new MdCommentList(RandomReviewId.nextId());
        ofInterest.add(comments1.getItem(rand.nextInt(comments1.size())));
        ofInterest.add(comments2.getItem(rand.nextInt(comments2.size())));
        GvCommentList comments = MdGvConverter.convert(ofInterest);

        ReviewNode meta = ReviewMaker.createMetaReview(mContext, comments, subject);

        float averageRating = 0.5f * (review1.getRating().get() + review2.getRating().get());
        assertEquals(subject, meta.getSubject().get());
        assertEquals(averageRating, meta.getRating().get());
        IdableList<ReviewNode> children = meta.getChildren();
        assertEquals(2, children.size());
        assertEquals(review1, children.getItem(0).getReview());
        assertEquals(review2, children.getItem(1).getReview());
    }

}

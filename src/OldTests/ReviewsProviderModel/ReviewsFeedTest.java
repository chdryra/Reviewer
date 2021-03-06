package com.chdryra.android.startouch.test.ReviewsProviderModel;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Application.ApplicationInstance;
import com.chdryra.android.startouch.DataDefinitions.DataAdapterModel.MdGvConverter;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewId;
import com.chdryra.android.startouch.test.TestUtils.TestDatabase;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 23/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsFeedTest extends InstrumentationTestCase {
    private static final int NUM = 3;
    ReviewsFeed mRepo;
    Context mContext;

    @SmallTest
    public void testGetReviewDatum() {
        //Just check for comments as generic wrt GvData
        MdCommentList allComments = getComments();
        assertTrue(allComments.size() > 1);
        ArrayList<MdReviewId> numReviewsWithComments = new ArrayList<>();
        for (MdComment comment : allComments) {
            MdReviewId id = comment.getReviewId();
            if (!numReviewsWithComments.contains(id)) numReviewsWithComments.add(id);
        }
        assertTrue(numReviewsWithComments.size() > 1);

        GvCommentList comments = MdGvConverter.toGvDataList(allComments);
        for (GvComment comment : comments) {
            Review review = mRepo.getReview(comment);
            assertNotNull(review);
            assertEquals(comment.getReviewId().getId(), review.getMdReviewId().toString());
            GvCommentList reviewComments = MdGvConverter.toGvDataList(review.getComments());
            assertTrue(reviewComments.contains(comment));
        }
    }

    @SmallTest
    public void testGetReviewData() {
        //Just check for comments as generic wrt GvData
        MdCommentList allComments = getComments();
        assertTrue(allComments.size() > 1);

        //Get comments from 2 reviews
        MdCommentList commentsOfInterest = new MdCommentList(null);
        MdReviewId initial = allComments.getItem(0).getReviewId();
        ArrayList<String> ids = new ArrayList<>();
        for (MdComment comment : allComments) {
            MdReviewId id = comment.getReviewId();
            if (!ids.contains(id.toString())) ids.add(id.toString());
            commentsOfInterest.add(comment);
            if (!comment.getReviewId().equals(initial)) break;
        }
        assertEquals(2, ids.size());

        //Create meta review
        GvCommentList comments = MdGvConverter.toGvDataList(commentsOfInterest);
        Review review = mRepo.getReview(comments);
        assertNotNull(review);

        //Check all comments of interest present
        GvCommentList reviewComments = MdGvConverter.toGvDataList(review.getComments());
        for (GvComment comment : comments) {
            assertTrue(reviewComments.contains(comment));
        }

        //Check appropriate reviews represented
        for (GvComment comment : reviewComments) {
            assertTrue(ids.contains(comment.getReviewId().getId()));
        }
    }

    @SmallTest
    public void testCreateMetaReviewDataList() {
        assertTrue(NUM > 2);
        MdIdableCollection<Review> reviews = mRepo.getReviews();
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
        GvCommentList comments = MdGvConverter.toGvDataList(ofInterest);

        ReviewNode meta = mRepo.getMetaReview(comments, subject);

        float averageRating = 0.5f * (review1.getRating().getRating() + review2.getRating()
                .getRating());
        assertEquals(subject, meta.getSubject().getSubject());
        assertEquals(averageRating, meta.getRating().getRating());
        MdIdableCollection<ReviewNode> children = meta.getChildren();
        assertEquals(2, children.size());
        assertEquals(review1, children.getItem(0).getReview());
        assertEquals(review2, children.getItem(1).getReview());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        TestDatabase.recreateDatabase(getInstrumentation());
        mContext = getInstrumentation().getTargetContext();
        mRepo = ApplicationInstance.getInstance(mContext).getReviewsRepository();
        assertTrue(mRepo.getReviews().size() > 0);
    }

    @Override
    protected void tearDown() throws Exception {
        TestDatabase.deleteDatabase(getInstrumentation());
    }

    private MdCommentList getComments() {
        MdIdableCollection<Review> reviews = mRepo.getReviews();
        MdCommentList comments = new MdCommentList(null);
        for (Review review : reviews) {
            comments.addList(review.getComments());
        }

        return comments;
    }
}

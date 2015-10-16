package com.chdryra.android.reviewer.test.ReviewsProviderModel;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.TestDatabase;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryTest extends InstrumentationTestCase {
    ReviewsRepository mRepo;
    Context mContext;

    @SmallTest
    public void testGetReviewDatum() {
        //Just check for comments as generic wrt GvData
        MdCommentList allComments = getComments();
        assertTrue(allComments.size() > 1);
        ArrayList<ReviewId> numReviewsWithComments = new ArrayList<>();
        for (MdCommentList.MdComment comment : allComments) {
            ReviewId id = comment.getReviewId();
            if (!numReviewsWithComments.contains(id)) numReviewsWithComments.add(id);
        }
        assertTrue(numReviewsWithComments.size() > 1);

        GvCommentList comments = MdGvConverter.convert(allComments);
        for (GvCommentList.GvComment comment : comments) {
            Review review = mRepo.getReview(comment);
            assertNotNull(review);
            assertEquals(comment.getReviewId().getId(), review.getId().toString());
            GvCommentList reviewComments = MdGvConverter.convert(review.getComments());
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
        ReviewId initial = allComments.getItem(0).getReviewId();
        ArrayList<String> ids = new ArrayList<>();
        for (MdCommentList.MdComment comment : allComments) {
            ReviewId id = comment.getReviewId();
            if (!ids.contains(id.toString())) ids.add(id.toString());
            commentsOfInterest.add(comment);
            if (!comment.getReviewId().equals(initial)) break;
        }
        assertEquals(2, ids.size());

        //Create meta review
        GvCommentList comments = MdGvConverter.convert(commentsOfInterest);
        Review review = mRepo.getReview(comments);
        assertNotNull(review);

        //Check all comments of interest present
        GvCommentList reviewComments = MdGvConverter.convert(review.getComments());
        for (GvCommentList.GvComment comment : comments) {
            assertTrue(reviewComments.contains(comment));
        }

        //Check appropriate reviews represented
        for (GvCommentList.GvComment comment : reviewComments) {
            assertTrue(ids.contains(comment.getReviewId().getId()));
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        TestDatabase.recreateDatabase(getInstrumentation());
        mContext = getInstrumentation().getTargetContext();
        mRepo = Administrator.get(mContext).getReviewsRepository();
        assertTrue(mRepo.getReviews().size() > 0);
    }

    @Override
    protected void tearDown() throws Exception {
        TestDatabase.deleteDatabase(getInstrumentation());
    }

    private MdCommentList getComments() {
        IdableList<Review> reviews = mRepo.getReviews();
        MdCommentList comments = new MdCommentList(null);
        for(Review review : reviews) {
            comments.addList(review.getComments());
        }

        return comments;
    }
}

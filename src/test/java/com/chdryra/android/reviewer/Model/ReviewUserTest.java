package com.chdryra.android.reviewer.Model;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewUserTest {
    private MdReviewId mRightId;
    private MdReviewId mWrongId;
    private MdDataMocker mWrongMocker;
    private MdDataMocker mRightMocker;
    private FactoryReviewNode mNodeFactory;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        mRightId = new MdReviewId("abc", 123l, 456);
        mWrongId = new MdReviewId("cde", 123l, 456);
        mRightMocker = new MdDataMocker(mRightId);
        mWrongMocker = new MdDataMocker(mWrongId);
        mNodeFactory = new FactoryReviewNode();
    }

    @Test
    public void noExceptionIfDataHasCorrectReviewId() {
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfAuthorHasWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mWrongMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0),mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfDateHasWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mWrongMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfSubjectHasWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mWrongMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfRatingHasWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mWrongMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfCommentsHaveWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mWrongMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfImagesHaveWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mWrongMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfFactsHaveWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mWrongMocker.newFactList(0),
                mRightMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfLocationsHaveWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mWrongMocker.newLocationList(0), mRightMocker.newCriterionList(0), false,
                mNodeFactory);
    }

    @Test
    public void throwExceptionIfCriteriaHaveWrongReviewId() {
        expectedException.expect(IllegalArgumentException.class);
        new ReviewUser(mRightId, mRightMocker.newAuthor(), mRightMocker.newDate(),
                mRightMocker.newSubject(), mRightMocker.newRating(), mRightMocker.newCommentList(0),
                mRightMocker.newImageList(0), mRightMocker.newFactList(0),
                mRightMocker.newLocationList(0), mWrongMocker.newCriterionList(0), false,
                mNodeFactory);
    }
}

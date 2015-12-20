package test.TestUtils;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;

import test.Model.ReviewsModel.Utils.MdDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReview {
    private static final int NUM = 3;
    private static final FactoryReviewNode NODE_FACTORY = new FactoryReviewNode();

    public static Review nextReview() {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        return new MockReview(id, new MdDataMocker(id), false);
    }

    public static ReviewNode nextReviewNode() {
        return nextReviewNode(false);
    }

    public static ReviewNode nextReviewNode(boolean isAverage) {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        Review review = new MockReview(id, new MdDataMocker(id), false);
        return NODE_FACTORY.createReviewNode(review, isAverage);
    }

    public static ReviewNodeComponent nextReviewNodeComponent(boolean isAverage) {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        Review review = new MockReview(id, new MdDataMocker(id), false);
        return NODE_FACTORY.createReviewNodeComponent(review, isAverage);
    }

    private static class MockReview extends ReviewUser{
        //Need to ignore images as Bitmap.createBitmap(.) not mocked in Android unit testing framework
        private MockReview(MdReviewId id, MdDataMocker mocker, boolean ratingIsAverage) {
            super(id, mocker.newAuthor(), mocker.newDate(), mocker.newSubject(), mocker.newRating(),
                    mocker.newCommentList(NUM), new MdDataList<MdImage>(id), mocker.newFactList(NUM),
                    mocker.newLocationList(NUM), mocker.newCriterionList(NUM), ratingIsAverage,
                    NODE_FACTORY);
        }
    }

}

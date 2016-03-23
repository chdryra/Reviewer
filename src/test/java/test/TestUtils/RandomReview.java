/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;

import java.util.ArrayList;

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

    public static ReviewNodeComponent nextReviewNodeComponent() {
        return nextReviewNodeComponent(false);
    }

    public static ReviewNodeComponent nextReviewNodeComponent(boolean isAverage) {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        Review review = new MockReview(id, new MdDataMocker(id), false);
        return NODE_FACTORY.createReviewNodeComponent(review, isAverage);
    }

    public static ArrayList<Review> constructTreeAndGetReviews(ReviewNodeComponent root) {
        ReviewNodeComponent child1 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent child2 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent child3 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild11 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild12 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild21 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent grandchild31 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild111 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild112 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild211 = RandomReview.nextReviewNodeComponent();
        ReviewNodeComponent greatgrandchild212 = RandomReview.nextReviewNodeComponent();

        root.addChild(child1);
        root.addChild(child2);
        root.addChild(child3);
        child1.addChild(grandchild11);
        child1.addChild(grandchild12);
        child2.addChild(grandchild21);
        child3.addChild(grandchild31);
        grandchild11.addChild(greatgrandchild111);
        grandchild11.addChild(greatgrandchild112);
        grandchild21.addChild(greatgrandchild211);
        grandchild21.addChild(greatgrandchild212);

        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(root.getReview());
        reviews.add(child1.getReview());
        reviews.add(child2.getReview());
        reviews.add(child3.getReview());
        reviews.add(grandchild11.getReview());
        reviews.add(grandchild12.getReview());
        reviews.add(grandchild21.getReview());
        reviews.add(grandchild31.getReview());
        reviews.add(greatgrandchild111.getReview());
        reviews.add(greatgrandchild112.getReview());
        reviews.add(greatgrandchild211.getReview());
        reviews.add(greatgrandchild212.getReview());

        return reviews;
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

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 09/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMaker extends ApplicationSingleton {
    private static final String NAME = "ReviewMaker";
    private static ReviewMaker sSingleton;

    private Author mAuthor;

    public ReviewMaker(Context context) {
        super(context, NAME);
        mAuthor = Administrator.get(context).getAuthor();
    }

    public static ReviewMaker get(Context c) {
        sSingleton = getSingleton(sSingleton, ReviewMaker.class, c);
        return sSingleton;
    }

    public static <T extends GvData> ReviewNode createMetaReview(Context context,
                                                                 GvDataCollection<T> data,
                                                                 String subject) {
        IdableList<Review> reviews = new IdableList<>();
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(ReviewsManager.getReview(context, data.getItem(i)));
        }

        return createMetaReview(context, reviews, subject);
    }

    public static ReviewNode createMetaReview(Context context,
                                              IdableList<Review> reviews,
                                              String subject) {
        ReviewMaker maker = get(context);
        ReviewPublisher publisher = new ReviewPublisher(maker.mAuthor, PublishDate.now());
        Review meta = FactoryReview.createReviewUser(publisher, subject, 0f);
        ReviewTreeNode parent = FactoryReview.createReviewTreeNode(meta, true);
        for (Review review : reviews) {
            parent.addChild(FactoryReview.createReviewTreeNode(review, false));
        }

        return parent.createTree();
    }
}


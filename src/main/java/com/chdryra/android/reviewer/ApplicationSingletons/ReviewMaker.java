package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
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
        ReviewMaker maker = get(context);
        Review meta = FactoryReview.createReviewUser(maker.mAuthor, PublishDate.now(), subject, 0f);
        ReviewTreeNode parent = new ReviewTreeNode(meta, true, ReviewId.generateId(maker.mAuthor));
        for (int i = 0; i < data.size(); ++i) {
            T item = data.getItem(i);
            ReviewNode node = ReviewsManager.getReviewNode(context, item);
            parent.addChild(FactoryReview.createReviewTreeNode(node, false));
        }

        return parent.createTree();
    }

    public static ReviewNode createMetaReview(Context context,
                                              ReviewIdableList<ReviewNode> nodes,
                                              String subject) {
        ReviewMaker maker = get(context);
        Review meta = FactoryReview.createReviewUser(maker.mAuthor, PublishDate.now(), subject, 0f);
        ReviewTreeNode parent = new ReviewTreeNode(meta, true, ReviewId.generateId(maker.mAuthor));
        for (ReviewNode node : nodes) {
            parent.addChild(FactoryReview.createReviewTreeNode(node, false));
        }

        return parent.createTree();
    }
}


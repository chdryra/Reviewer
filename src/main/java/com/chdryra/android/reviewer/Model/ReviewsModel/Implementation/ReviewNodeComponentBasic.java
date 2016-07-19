/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemCounter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeComponentBasic extends ReviewNodeBasic
        implements ReviewNodeComponent{
    private static final CallbackMessage OK = CallbackMessage.ok();
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;


    private ReviewNodeComponent mParent;

    public ReviewNodeComponentBasic(FactoryReviewNode nodeFactory) {
        super(nodeFactory.getBinderFactory());
        mVisitorFactory = nodeFactory.getVisitorFactory();
        mTraverserFactory = nodeFactory.getTraverserFactory();
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return mParent;
    }

    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public void setParent(ReviewNodeComponent parentNode) {
        if (mParent != null && parentNode != null
                && mParent.getReviewId().equals(parentNode.getReviewId())) {
            return;
        }

        if (mParent != null) mParent.removeChild(getReviewId());
        mParent = parentNode;
        if (mParent != null) mParent.addChild(this);
    }

    @Override
    public void getData(final ReviewsCallback callback) {
        final VisitorItemGetter<ReviewReference> visitor = mVisitorFactory.newLeavesCollector();
        doTraversal("Reviews", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onReviews(visitor.getData(), OK);
            }
        });
    }

    @Override
    public void getData(final SubjectsCallback callback) {
        final VisitorItemGetter<DataSubject> visitor = mVisitorFactory.newSubjectsCollector();
        doTraversal("Subjects", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onSubjects(visitor.getData(), OK);
            }
        });
    }

    @Override
    public void getData(final AuthorsCallback callback) {
        final VisitorItemGetter<DataAuthorReview> visitor = mVisitorFactory.newAuthorsCollector();
        doTraversal("Authors", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onAuthors(visitor.getData(), OK);
            }
        });
    }

    @Override
    public void getData(final DatesCallback callback) {
        final VisitorItemGetter<DataDateReview> visitor = mVisitorFactory.newDatesCollector();
        doTraversal("Dates", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onDates(visitor.getData(), OK);
            }
        });
    }

    @Override
    public void getSize(final ReviewsSizeCallback callback) {
        final VisitorItemCounter<ReviewId> visitor = mVisitorFactory.newReviewsCounter();
        doTraversal("ReviewsCount", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onNumReviews(visitor.getCount(), OK);
            }
        });
    }

    @Override
    public void getSize(final AuthorsSizeCallback callback) {
        final VisitorItemCounter<AuthorId> visitor = mVisitorFactory.newAuthorsCounter();
        doTraversal("AuthorsCount", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onNumAuthors(visitor.getCount(), OK);
            }
        });
    }

    @Override
    public void getSize(final SubjectsSizeCallback callback) {
        final VisitorItemCounter<String> visitor = mVisitorFactory.newSubjectsCounter();
        doTraversal("SubjectsCount", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onNumSubjects(visitor.getCount(), OK);
            }
        });
    }

    @Override
    public void getSize(final DatesSizeCallback callback) {
        final VisitorItemCounter<String> visitor = mVisitorFactory.newDatesCounter();
        doTraversal("DatesCount", visitor, new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onNumDates(visitor.getCount(), OK);
            }
        });
    }

    private TreeTraverser newTraverser(ReviewNode root) {
        return mTraverserFactory.newTreeTraverser(root);
    }

    private void doTraversal(String visitorId, VisitorReviewNode visitor,
                             TreeTraverser.TraversalCallback traversalCallback) {
        TreeTraverser traverser = newTraverser(this);
        traverser.addVisitor(visitorId, visitor);
        traverser.traverse(traversalCallback);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeComponentBasic)) return false;

        ReviewNodeComponentBasic that = (ReviewNodeComponentBasic) o;

        if (!mVisitorFactory.equals(that.mVisitorFactory)) return false;
        if (!mTraverserFactory.equals(that.mTraverserFactory)) return false;
        return mParent != null ? mParent.equals(that.mParent) : that.mParent == null;

    }

    @Override
    public int hashCode() {
        int result = mVisitorFactory.hashCode();
        result = 31 * result + mTraverserFactory.hashCode();
        result = 31 * result + (mParent != null ? mParent.hashCode() : 0);
        return result;
    }
}

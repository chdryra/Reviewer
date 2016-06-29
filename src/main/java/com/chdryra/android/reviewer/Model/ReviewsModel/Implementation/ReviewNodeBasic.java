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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemCounter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic extends MetaReferenceBasic implements ReviewNode {
    private static final CallbackMessage OK = CallbackMessage.ok();

    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;
    private ArrayList<NodeObserver> mObservers;

    public ReviewNodeBasic(BindersManagerMeta bindersManager,
                           FactoryVisitorReviewNode visitorFactory,
                           FactoryNodeTraverser traverserFactory) {
        super(bindersManager);
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
        mObservers = new ArrayList<>();
    }

    protected void notifyOnChildAdded(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildAdded(child);
        }
    }

    protected void notifyOnChildRemoved(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildRemoved(child);
        }
    }

    protected void notifyOnParentChanged(@Nullable ReviewNode oldParent, @Nullable ReviewNode 
            newParent) {
        for (NodeObserver observer : mObservers) {
            observer.onParentChanged(oldParent, newParent);
        }
    }

    protected void notifyOnNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
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

    @Override
    public void registerObserver(NodeObserver binder) {
        if (!mObservers.contains(binder)) mObservers.add(binder);
    }

    @Override
    public void unregisterObserver(NodeObserver binder) {
        if (mObservers.contains(binder)) mObservers.remove(binder);
    }

    @Override
    public boolean isLeaf() {
        return getChildren().size() == 0 && isValidReference();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeBasic)) return false;

        ReviewNodeBasic that = (ReviewNodeBasic) o;

        return mObservers.equals(that.mObservers);

    }

    @Override
    public int hashCode() {
        return mObservers.hashCode();
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
}

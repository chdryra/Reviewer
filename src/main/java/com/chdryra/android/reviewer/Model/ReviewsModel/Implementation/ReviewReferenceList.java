/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewReferenceList extends DataReferenceBasic<IdableList<ReviewReference>>
        implements
        ReviewListReference<ReviewReference>,
        ReviewNode.NodeObserver {

    private static final String REFERENCES = "References";
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;
    private ReviewNode mRoot;

    private Collection<ListItemBinder<ReviewReference>> mItemBinders;
    private Collection<ReferenceBinder<IdableList<ReviewReference>>> mValueBinders;

    private interface PostTraversalMethod {
        void onTraversal(IdableList<ReviewReference> references);
    }

    public ReviewReferenceList(FactoryVisitorReviewNode visitorFactory, FactoryNodeTraverser
            traverserFactory, ReviewNode root) {
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
        mRoot = root;

        mItemBinders = new ArrayList<>();
        mValueBinders = new ArrayList<>();

        mRoot.registerObserver(this);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        if (hasItemBinders()) {
            doTraversal(child, new PostTraversalMethod() {
                @Override
                public void onTraversal(IdableList<ReviewReference> references) {
                    notifyItemBindersAdd(references);
                }
            });
        }

        notifyValueBindersIfNecessary();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        if (hasItemBinders()) {
            doTraversal(child, new PostTraversalMethod() {
                @Override
                public void onTraversal(IdableList<ReviewReference> references) {
                    notifyItemBindersRemove(references);
                }
            });
        }

        notifyValueBindersIfNecessary();
    }

    @Override
    public void onNodeChanged() {
        invalidate();
    }

    @Override
    public void bindToItems(final ListItemBinder<ReviewReference> binder) {
        if (!mItemBinders.contains(binder)) mItemBinders.add(binder);
        doTraversal(mRoot, new PostTraversalMethod() {
            @Override
            public void onTraversal(IdableList<ReviewReference> references) {
                notifyItemBinder(binder, references);
            }
        });
    }

    @Override
    public void unbindFromItems(ListItemBinder<ReviewReference> binder) {
        if (mItemBinders.contains(binder)) mItemBinders.remove(binder);
    }

    @Override
    public void dereference(DereferenceCallback<IdableList<ReviewReference>> callback) {

    }

    @Override
    public void bindToValue(final ReferenceBinder<IdableList<ReviewReference>> binder) {
        if (!mValueBinders.contains(binder)) mValueBinders.add(binder);
        doTraversal(mRoot, new PostTraversalMethod() {
            @Override
            public void onTraversal(IdableList<ReviewReference> references) {
                notifyValueBinder(binder, references);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<IdableList<ReviewReference>> binder) {
        if (mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public boolean isValidReference() {
        return mRoot.getChildren().size() > 0 || mRoot.getReference() != null;
    }

    @Override
    public void invalidate() {

    }

    @Override
    public ReviewId getReviewId() {
        return mRoot.getReviewId();
    }

    private void notifyValueBindersIfNecessary() {
        if (hasValueBinders()) {
            doTraversal(mRoot, new PostTraversalMethod() {
                @Override
                public void onTraversal(IdableList<ReviewReference> references) {
                    notifyValueBinders(references);
                }
            });
        }
    }

    private boolean hasItemBinders() {
        return mItemBinders.size() > 0;
    }

    private boolean hasValueBinders() {
        return mValueBinders.size() > 0;
    }

    private void notifyItemBindersAdd(IdableList<ReviewReference> data) {
        for (ListItemBinder<ReviewReference> binder : mItemBinders) {
            notifyItemBinder(binder, data);
        }
    }

    private void notifyItemBinder(ListItemBinder<ReviewReference> binder,
                                  IdableList<ReviewReference> data) {
        for (ReviewReference reference : data) {
            binder.onItemValue(reference);
        }
    }

    private void notifyValueBinders(IdableList<ReviewReference> data) {
        for (ReferenceBinder<IdableList<ReviewReference>> binder : mValueBinders) {
            notifyValueBinder(binder, data);
        }
    }

    private void notifyValueBinder(ReferenceBinder<IdableList<ReviewReference>> binder,
                                   IdableList<ReviewReference> data) {
        binder.onReferenceValue(data);
    }

    private void notifyItemBindersRemove(IdableList<ReviewReference> data) {
        for (ListItemBinder<ReviewReference> binder : mItemBinders) {
            for (ReviewReference reference : data) {
                binder.onItemRemoved(reference);
            }
        }
    }

    private void doTraversal(ReviewNode root, final PostTraversalMethod post) {
        getTraverser(root).traverse(new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                VisitorItemGetter<ReviewReference> visitor = castVisitor(visitors);
                post.onTraversal(visitor.getData());
            }
        });
    }

    @NonNull
    private TreeTraverser getTraverser(ReviewNode root) {
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(root);
        VisitorItemGetter<ReviewReference> visitor = mVisitorFactory.newLeavesCollector();
        traverser.addVisitor(REFERENCES, visitor);
        return traverser;
    }


    private VisitorItemGetter<ReviewReference> castVisitor(Map<String, VisitorReviewNode>
                                                                   visitors) {
        return (VisitorItemGetter<ReviewReference>) visitors.get(REFERENCES);
    }
}

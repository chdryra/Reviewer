/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class TreeDataReferenceBasic<T extends HasReviewId> extends
        BindableListReferenceBasic<T>
        implements
        ReviewListReference<T>,
        ReviewNode.NodeObserver {

    private static final String VISITOR = "References";

    private ReviewNode mRoot;
    private FactoryNodeTraverser mTraverserFactory;

    public interface GetDataCallback<T extends HasReviewId> {
        void onData(IdableList<T> items);
    }

    public interface TreeTraversalCallback {
        void onTraversed(VisitorReviewNode visitor);
    }

    public abstract VisitorReviewNode newVisitor();

    public abstract void onDataTraversalComplete(VisitorReviewNode visitor,
                                                 GetDataCallback<T> callback);

    public TreeDataReferenceBasic(ReviewNode root, FactoryNodeTraverser traverserFactory) {
        mRoot = root;
        mTraverserFactory = traverserFactory;
        mRoot.registerObserver(this);
    }

    public void getData(GetDataCallback<T> callback) {
        getData(mRoot, callback);
    }

    public void registerObserver(ReviewNode.NodeObserver observer) {
        mRoot.registerObserver(observer);
    }

    public void unregisterObserver(ReviewNode.NodeObserver observer) {
        mRoot.unregisterObserver(observer);
    }

    public void getData(ReviewId childId, final GetDataCallback<T> callback) {
        ReviewNode child = mRoot.getChild(childId);
        if (child == null) return;
        getData(child, callback);
    }

    public void doTraversal(TreeTraversalCallback callback) {
        doTraversal(mRoot, callback);
    }

    public void doTraversal(ReviewId childId, TreeTraversalCallback callback) {
        ReviewNode child = mRoot.getChild(childId);
        if (child == null) return;
        doTraversal(child, callback);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        if (hasItemBinders()) {
            getData(child, new GetDataCallback<T>() {
                @Override
                public void onData(IdableList<T> items) {
                    notifyItemBindersAdd(items);
                }
            });
        }

        notifyValueBinders();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        if (hasItemBinders()) {
            getData(child, new GetDataCallback<T>() {
                @Override
                public void onData(IdableList<T> items) {
                    notifyItemBindersRemove(items);
                }
            });
        }

        notifyValueBinders();
    }

    @Override
    public void onNodeChanged() {

    }

    @Override
    public void onDescendantsChanged() {
        notifyAllBinders();
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<IdableList<T>> callback) {
        getData(new GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                callback.onDereferenced(items, CallbackMessage.ok());
            }
        });
    }

    @Override
    protected void fireForBinder(final ListItemBinder<T> binder) {
        getData(new TreeDataReferenceBasic.GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                notifyItemBinderAdd(binder, items);
            }
        });
    }

    @Override
    public boolean isValidReference() {
        return super.isValidReference() && mRoot.getChildren().size() > 0 || mRoot.getReference() != null;
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mTraverserFactory = null;
        mRoot = null;
    }

    @Override
    public ReviewId getReviewId() {
        return mRoot.getReviewId();
    }

    private void getData(ReviewNode root, final GetDataCallback<T> post) {
        doTraversal(root, new TreeTraversalCallback() {
            @Override
            public void onTraversed(VisitorReviewNode visitor) {
                onDataTraversalComplete(visitor, post);
            }
        });
    }

    private void doTraversal(ReviewNode root, final TreeTraversalCallback callback) {
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(root);
        traverser.addVisitor(VISITOR, newVisitor());
        traverser.traverse(new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onTraversed(visitors.get(VISITOR));
            }
        });
    }
}


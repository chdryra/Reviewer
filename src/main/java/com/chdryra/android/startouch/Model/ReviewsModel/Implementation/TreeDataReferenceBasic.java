/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation
        .IdableListReferenceBasic;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class TreeDataReferenceBasic<Value extends HasReviewId, Reference extends
        ReviewItemReference<Value>> extends
        IdableListReferenceBasic<Value>
        implements
        ReviewListReference<Value, Reference>,
        ReviewNode.NodeObserver {
    private static final String VISITOR
            = TagKeyGenerator.getTag(TreeDataReferenceBasic.class, "Visitor");

    private ReviewNode mRoot;
    private FactoryNodeTraverser mTraverserFactory;

    public interface GetDataCallback<T extends HasReviewId> {
        void onData(IdableList<T> items);
    }

    public interface TreeTraversalCallback {
        void onTraversed(VisitorReviewNode visitor);
    }

    protected abstract VisitorReviewNode newVisitor();

    protected abstract void onDataTraversalComplete(VisitorReviewNode visitor,
                                                    GetDataCallback<Value> callback);

    TreeDataReferenceBasic(ReviewNode root, FactoryNodeTraverser traverserFactory) {
        mRoot = root;
        mTraverserFactory = traverserFactory;
        mRoot.registerObserver(this);
    }

    public void getData(GetDataCallback<Value> callback) {
        getData(mRoot, callback);
    }

    public void registerObserver(ReviewNode.NodeObserver observer) {
        mRoot.registerObserver(observer);
    }

    public void unregisterObserver(ReviewNode.NodeObserver observer) {
        mRoot.unregisterObserver(observer);
    }

    public void getData(ReviewId childId, final GetDataCallback<Value> callback) {
        ReviewNode child = mRoot.getChild(childId);
        if (child == null) return;
        getData(child, callback);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        if (hasItemBinders()) {
            getData(child, new GetDataCallback<Value>() {
                @Override
                public void onData(IdableList<Value> items) {
                    notifyOnAdded(items);
                }
            });
        }

        notifySubscribers();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        if (hasItemBinders()) {
            getData(child, new GetDataCallback<Value>() {
                @Override
                public void onData(IdableList<Value> items) {
                    notifyOnRemoved(items);
                }
            });
        }

        notifySubscribers();
    }

    @Override
    public void onNodeChanged() {

    }

    @Override
    public void onTreeChanged() {
        notifyAllSubscribers();
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<IdableList<Value>> callback) {
        getData(new GetDataCallback<Value>() {
            @Override
            public void onData(IdableList<Value> items) {
                callback.onDereferenced(new DataValue<>(items));
            }
        });
    }

    @Override
    protected void onBinding(final ItemSubscriber<Value> subscriber) {
        getData(new TreeDataReferenceBasic.GetDataCallback<Value>() {
            @Override
            public void onData(IdableList<Value> items) {
                notifyOnAdded(subscriber, items);
            }
        });
    }

    @Override
    protected void onUnbinding(ItemSubscriber<Value> subscriber) {

    }

    @Override
    public boolean isValidReference() {
        return super.isValidReference() && mRoot.getChildren().size() > 0 || mRoot.getReference()
                != null;
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

    void doTraversal(TreeTraversalCallback callback) {
        doTraversal(mRoot, callback);
    }

    void doTraversal(ReviewId childId, TreeTraversalCallback callback) {
        ReviewNode child = mRoot.getChild(childId);
        if (child == null) return;
        doTraversal(child, callback);
    }

    private void getData(ReviewNode root, final GetDataCallback<Value> post) {
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


/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.v4.app.FragmentManager;

import com.chdryra.android.mygenerallibrary.Ui.PagerAdapterBasic;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFormatReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 08/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FormattedPagerAdapter extends PagerAdapterBasic<FragmentFormatReview> implements ReviewNode.NodeObserver {
    private final Comparator<ReviewNode> mComparator;
    private final ReviewNode mNode;
    private final FragmentsObserver mObserver;
    private final boolean mIsClickable;

    private boolean mIsSorted = false;
    private ArrayList<ReviewNode> mSortedNodes;

    public interface FragmentsObserver {
        void updateTitle(String title);

        void onNoFragmentsLeft();
    }

    public FormattedPagerAdapter(ReviewNode node,
                                 Comparator<ReviewNode> comparator,
                                 FragmentManager manager,
                                 FragmentsObserver observer,
                                 boolean isClickable) {
        super(manager);
        mNode = node;
        mComparator = comparator;
        mObserver = observer;
        mIsClickable = isClickable;
        mNode.registerObserver(this);
    }

    public ReviewNode getNode(ReviewId id) {
        ReviewNode child = mNode.getChild(id);
        return child != null ? child : mNode;
    }

    public void detach() {
        mNode.unregisterObserver(this);
    }

    @Override
    protected void onFragmentRemoved(int position) {
        mObserver.updateTitle(newTitle(position, getCount() - 1));
        if(getCachedFragments().size() == 0) mObserver.onNoFragmentsLeft();
    }

    @Override
    public String getId(int position) {
        if (!mIsSorted) {
            mSortedNodes = new ArrayList<>(mNode.getChildren());
            Collections.sort(mSortedNodes, mComparator);
            sorted();
        }

        ReviewId nodeId = mSortedNodes.size() > 0 ?
                mSortedNodes.get(position).getReviewId() : mNode.getReviewId();

        return nodeId.toString();
    }

    @Override
    protected FragmentFormatReview newFragmentInstance(int position) {
        return FragmentFormatReview.newInstance(getId(position), mIsClickable);
    }

    @Override
    public int getCount() {
        int size = mNode.getChildren().size();
        return size > 0 ? size : 1;
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        notifyChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyChanged();
    }

    @Override
    public void onNodeChanged() {
        notifyChanged();
    }

    @Override
    public void onTreeChanged() {
        notifyChanged();
    }


    private void sorted() {
        mIsSorted = true;
    }

    private void notifyChanged() {
        mIsSorted = false;
        notifyDataSetChanged();
    }
}

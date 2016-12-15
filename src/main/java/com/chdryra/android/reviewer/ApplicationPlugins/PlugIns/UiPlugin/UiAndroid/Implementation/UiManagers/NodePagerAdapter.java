/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 08/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodePagerAdapter extends FragmentStatePagerAdapter implements ReviewNode.NodeObserver {
    private final Comparator<ReviewNode> mComparator;
    private final ReviewNode mNode;
    private final boolean mIsClickable;
    private final SparseArray<FragmentFormatReview> mFragments;

    private boolean mIsSorted = false;
    private ArrayList<ReviewNode> mSortedNodes;

    public NodePagerAdapter(ReviewNode node,
                            Comparator<ReviewNode> comparator,
                            FragmentManager manager,
                            boolean isClickable) {
        super(manager);
        mNode = node;
        mComparator = comparator;
        mIsClickable = isClickable;
        mFragments = new SparseArray<>();
        mNode.registerObserver(this);
    }

    public ReviewNode getNode(ReviewId id) {
        ReviewNode child = mNode.getChild(id);
        return child != null ? child : mNode;
    }

    @Nullable
    public FragmentFormatReview getFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        FragmentFormatReview fragment;
        if(mFragments.get(position) == null) {
            fragment = FragmentFormatReview.newInstance(getNodeId(position), mIsClickable);
            mFragments.put(position, fragment);
        }

        return mFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup group, int position, Object object) {
        super.destroyItem(group, position, object);
        mFragments.remove(position);
    }

    @Override
    public int getItemPosition(Object object) {
        FragmentFormatReview fragment = (FragmentFormatReview) object;
        ReviewId nodeId = fragment.getNodeId();
        int position = POSITION_UNCHANGED;
        int i;
        for(i = 0; i < getCount(); i++) {
            if(getNodeId(i).equals(nodeId)) {
                position = i;
                break;
            }
        }

        return position;
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

    private ReviewId getNodeId(int position) {
        if (!mIsSorted) {
            mSortedNodes = new ArrayList<>(mNode.getChildren());
            Collections.sort(mSortedNodes, mComparator);
            sorted();
        }

        return mSortedNodes.size() > 0 ?
                mSortedNodes.get(position).getReviewId() : mNode.getReviewId();
    }

    private void sorted() {
        mIsSorted = true;
    }

    private void notifyChanged() {
        mIsSorted = false;
        notifyDataSetChanged();
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
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
    private boolean mIsSorted = false;

    public NodePagerAdapter(ReviewNode node, Comparator<ReviewNode> comparator,
                            FragmentManager manager, boolean isClickable) {
        super(manager);
        mNode = node;
        mComparator = comparator;
        mIsClickable = isClickable;
        mNode.registerObserver(this);
    }

    public ReviewNode getNode(ReviewId id) {
        ReviewNode child = mNode.getChild(id);
        return child != null ? child : mNode;
    }

    @Override
    public Fragment getItem(int position) {
        IdableList<ReviewNode> children = mNode.getChildren();
        ArrayList<ReviewNode> nodes = new ArrayList<>(children);
        if (!mIsSorted) {
            Collections.sort(nodes, mComparator);
            sorted();
        }
        int size = nodes.size();
        ReviewNode item = size > 0 ? nodes.get(position) : mNode;

        return FragmentFormatReview.newInstance(item.getReviewId(), mIsClickable);
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

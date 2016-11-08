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
    private static final Comparator<ReviewNode> RECENT_FIRST = new NodeComparator();
    private boolean mIsSorted = false;
    private ReviewNode mNode;

    public NodePagerAdapter(ReviewNode node, FragmentManager manager) {
        super(manager);
        mNode = node;
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
            Collections.sort(nodes, RECENT_FIRST);
            sorted();
        }
        int size = nodes.size();
        ReviewNode item = size > 0 ? nodes.get(position) : mNode;
        return FragmentFormatReview.newInstance(item.getReviewId());
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

    private static class NodeComparator implements Comparator<ReviewNode> {
        @Override
        public int compare(ReviewNode lhs, ReviewNode rhs) {
            long lTime = lhs.getPublishDate().getTime();
            long rTime = rhs.getPublishDate().getTime();
            if (lTime > rTime) {
                return -1;
            } else if (lTime < rTime) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

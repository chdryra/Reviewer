/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 08/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodePagerAdapter extends FragmentStatePagerAdapter implements ReviewNode.NodeObserver {
    private ReviewNode mNode;

    public NodePagerAdapter(ReviewNode node, FragmentManager manager) {
        super(manager);
        mNode = node;
        mNode.registerObserver(this);
    }

    @Override
    public Fragment getItem(int position) {
        int size = mNode.getChildren().size();
        ReviewNode item = size > 0 ? mNode.getChildren().getItem(position) : mNode;
        return FragmentFormatReview.newInstance(item.getReviewId());
    }

    @Override
    public int getCount() {
        int size = mNode.getChildren().size();
        return size > 0 ? size : 1;
    }

    public ReviewNode getNode(ReviewId id) {
        ReviewNode child = mNode.getChild(id);
        return child != null ? child : mNode;
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        notifyDataSetChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyDataSetChanged();
    }

    @Override
    public void onNodeChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onTreeChanged() {
        notifyDataSetChanged();
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.support.v4.app.FragmentManager;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 08/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataPagerAdapter<T extends HasReviewId, F extends PagerAdapterBasic.PageableFragment>
        extends PagerAdapterBasic<F> implements ReviewNode.NodeObserver {

    private final FragmentFactory<F> mFactory;
    private IdableList<ReviewItemReference<T>> mItemReferences;

    public interface FragmentFactory<F extends PageableFragment> {
        F newFragment(String pageId, int position);
    }

    public NodeDataPagerAdapter(FragmentManager manager, RefDataList<T> data, FragmentFactory<F> factory) {
        super(manager);
        mFactory = factory;
        mItemReferences = new IdableDataList<>(data.getReviewId());
        data.toItemReferences(new ReviewListReference.ItemReferencesCallback<T, ReviewItemReference<T>>() {
            @Override
            public void onItemReferences(IdableList<ReviewItemReference<T>> itemReferences) {
                mItemReferences = itemReferences;
            }
        });
    }

    public ReviewItemReference<T> getReference(int position) {
        return position < getCount() ? mItemReferences.getItem(position)
                : new NullDataReference.Item<T>();
    }

    @Override
    public String getId(int position) {
        return getId(mItemReferences.getReviewId().toString(), position);
    }

    public static String getId(String reviewId, int index) {
        return reviewId + ":" + String.valueOf(index);
    }

    @Override
    protected F newFragmentInstance(int position) {
        return mFactory.newFragment(mItemReferences.getReviewId().toString(), position);
    }

    @Override
    public int getCount() {
        return mItemReferences.size();
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

    private void notifyChanged() {
        notifyDataSetChanged();
    }
}

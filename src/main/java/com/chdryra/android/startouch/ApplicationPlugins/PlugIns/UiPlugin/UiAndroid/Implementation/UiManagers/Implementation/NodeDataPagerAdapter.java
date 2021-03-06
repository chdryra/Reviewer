/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

import com.chdryra.android.corelibrary.Ui.PagerAdapterBasic;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 08/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NodeDataPagerAdapter<Data extends HasReviewId, FragmentType extends
        NodeDataPagerAdapter.DataFragment<Data>>
        extends PagerAdapterBasic<FragmentType> implements ReviewNode.NodeObserver {

    private final FragmentFactory<FragmentType> mFactory;
    private IdableList<ReviewItemReference<Data>> mReferences;
    private boolean mDataReady = false;

    public interface FragmentFactory<F extends PageableFragment> {
        F newFragment(String pageId);
    }

    public NodeDataPagerAdapter(FragmentManager manager, DataListRef<Data> data,
                                FragmentFactory<FragmentType> factory) {
        super(manager);
        mFactory = factory;
        mReferences = new IdableDataList<>(data.getReviewId());
        data.toItemReferences(new ReviewListReference.ItemReferencesCallback<Data,
                ReviewItemReference<Data>>() {
            @Override
            public void onItemReferences(IdableList<ReviewItemReference<Data>> itemReferences) {
                mReferences = itemReferences;
                dataReady();
            }
        });
    }

    public void onFragmentReady(FragmentType fragment) {
        if (mDataReady) updateFragment(fragment);
    }

    @Override
    public String getId(int position) {
        return String.valueOf(position);
    }

    @Override
    protected FragmentType newFragmentInstance(int position) {
        return mFactory.newFragment(getId(position));
    }

    @Override
    public int getCount() {
        return mReferences.size();
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

    private void dataReady() {
        mDataReady = true;
        SparseArray<FragmentType> fragments = getCachedFragments();
        for (int i = 0; i < fragments.size(); ++i) {
            updateFragment(fragments.get(fragments.keyAt(i)));
        }
        notifyDataSetChanged();
    }

    private void updateFragment(FragmentType fragment) {
        fragment.onData(mReferences.get(getItemPosition(fragment)));
    }

    public static abstract class DataFragment<Data extends HasReviewId> extends PageableFragment {
        public abstract void onData(ReviewItemReference<Data> data);
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Bucketing.Bucket;
import com.chdryra.android.mygenerallibrary.Bucketing.BucketDistribution;
import com.chdryra.android.reviewer.Algorithms.DataSorting.DataBucketer;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvBucketList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;


import java.util.Collections;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerBuckets<BucketingValue, Data extends HasReviewId> extends
        ViewerNodeBasic<GvBucket> {
    private static final GvDataType<GvBucket> TYPE = GvBucket.TYPE;
    private final FactoryReviewViewAdapter mAdapterFactory;
    private final ViewHolderFactory<VhBucket<BucketingValue, Data>> mVhFactory;
    private final DataBucketer<BucketingValue, Data> mBucketer;

    public ViewerBuckets(ReviewNode node,
                         FactoryReviewViewAdapter adapterFactory,
                         ViewHolderFactory<VhBucket<BucketingValue, Data>> vhFactory,
                         DataBucketer<BucketingValue, Data> bucketer) {
        super(node, TYPE);
        mAdapterFactory = adapterFactory;
        mVhFactory = vhFactory;
        mBucketer = bucketer;
    }

    @Override
    public boolean isExpandable(GvBucket bucket) {
        GvDataList<GvBucket> cache = getCache();
        return cache != null && cache.contains(bucket) &&
                bucket.getBucket().getBucketedItems().size() > 0;
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvBucket bucket) {
        return isExpandable(bucket) ? getExpansionAdapter(bucket) : null;
    }

    @Override
    protected GvDataList<GvBucket> makeGridData() {
        ReviewNode node = getReviewNode();
        final GvReviewId id = new GvReviewId(node.getReviewId());
        final GvBucketList<BucketingValue, Data> data = new GvBucketList<>(id);

        mBucketer.bucketData(node, new DataBucketer.DataBucketerCallback<BucketingValue, Data>() {
            @Override
            public void onDataBucketed(BucketDistribution<BucketingValue, Data> distribution) {
                List<Bucket<BucketingValue, Data>> buckets = distribution.getBuckets();
                Collections.reverse(buckets);
                int size = distribution.size();
                for (Bucket<BucketingValue, Data> bucket : buckets) {
                    data.add(new GvBucket<>(bucket, size, mVhFactory));
                }
                notifyDataObservers();
            }
        });

        return data;
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        GvDataList<GvBucket> gridData = getGridData();
        IdableCollection<GvReviewId> data = new IdableDataCollection<>();
        for(GvBucket bucket : gridData) {
            data.addAll(getBucketedIds((Bucket<BucketingValue, Data>) bucket.getBucket()));
        }
        return mAdapterFactory.newReviewsListAdapter(data, getReviewNode().getSubject().getSubject());
    }

    @Nullable
    private ReviewViewAdapter<?> getExpansionAdapter(GvBucket<BucketingValue, Data> datum) {
        Bucket<BucketingValue, Data> bucket = datum.getBucket();
        IdableCollection<GvReviewId> data = getBucketedIds(bucket);
        return mAdapterFactory.newReviewsListAdapter(data, bucket.getRange().toString());
    }

    @NonNull
    private IdableCollection<GvReviewId> getBucketedIds(Bucket<BucketingValue, Data> bucket) {
        IdableCollection<GvReviewId> data = new IdableDataCollection<>();
        for (Data item : bucket.getBucketedItems()) {
            data.add(new GvReviewId(item.getReviewId()));
        }
        return data;
    }
}

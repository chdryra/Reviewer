/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterBasic<T> implements ReviewNode.NodeObserver {
    private ReviewNode mNode;
    private DataConverter<DataImage, GvImage, GvImageList> mCoversConverter;

    public AdapterReviewNode(ReviewNode node,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                             GridDataWrapper<T> viewer) {
        this(node, coversConverter);
        setWrapper(viewer);
    }

    public AdapterReviewNode(ReviewNode node,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter) {
        mNode = node;
        mCoversConverter = coversConverter;
        node.registerObserver(this);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        notifyDataObservers();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyDataObservers();
    }

    @Override
    public void onNodeChanged() {
        notifyDataObservers();
    }

    @Override
    public String getSubject() {
        return mNode.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mNode.getRating().getRating();
    }

    @Override
    public void getCover(final CoverCallback callback) {
        mNode.getCover().dereference(new DataReference.DereferenceCallback<DataImage>() {
            @Override
            public void onDereferenced(@Nullable DataImage data, CallbackMessage message) {
                if(data != null && !message.isError()) {
                    callback.onAdapterCover(mCoversConverter.convert(data));
                } else {
                    callback.onAdapterCover(new GvImage());
                }
            }
        });
    }
}

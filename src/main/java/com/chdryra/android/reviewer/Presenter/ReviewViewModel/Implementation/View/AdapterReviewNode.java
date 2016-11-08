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

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterImpl<T> implements ReviewNode.NodeObserver {
    private final ReviewNode mNode;
    private final DataConverter<DataImage, GvImage, GvImageList> mCoversConverter;

    public AdapterReviewNode(ReviewNode node,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                             GridDataWrapper<T> viewer) {
        super(viewer);
        mNode = node;
        mCoversConverter = coversConverter;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    @Override
    protected void onAttach() {
        mNode.registerObserver(this);
    }

    @Override
    protected void onDetach() {
        mNode.unregisterObserver(this);
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
    public void onTreeChanged() {
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
            public void onDereferenced(DataValue<DataImage> value) {
                GvImage image = value.hasValue() ?
                        mCoversConverter.convert(value.getData()) : new GvImage();
                callback.onAdapterCover(image);
            }
        });
    }
}

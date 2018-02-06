/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterImpl<T> implements ReviewNode.NodeObserver {
    private final ReviewNode mNode;
    private final DataReference<ProfileImage> mProfileImage;
    private final DataConverter<DataImage, GvImage, GvImageList> mCoversConverter;
    private GvImage mCover;
    private boolean mFindingCover = false;

    public AdapterReviewNode(ReviewNode node,
                             DataReference<ProfileImage> profileImage,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                             GridDataWrapper<T> viewer) {
        super(viewer);
        mNode = node;
        mProfileImage = profileImage;
        mCoversConverter = coversConverter;
        mCover = new GvImage();
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
        if(!mFindingCover && mCover.getBitmap() == null) {
            mFindingCover = true;
            mNode.getCover().dereference(new DataReference.DereferenceCallback<DataImage>() {
                @Override
                public void onDereferenced(DataValue<DataImage> value) {
                    if(value.hasValue()) {
                        mCover = mCoversConverter.convert(value.getData());
                        mFindingCover = false;
                        callback.onAdapterCover(mCover);
                    } else {
                        dereferenceProfileImage(callback);
                    }
                }
            });
        } else {
            callback.onAdapterCover(mCover);
        }
    }

    private void dereferenceProfileImage(final CoverCallback callback) {
        mProfileImage.dereference(new DataReference.DereferenceCallback<ProfileImage>() {
            @Override
            public void onDereferenced(DataValue<ProfileImage> value) {
                if(value.hasValue()) {
                    ProfileImage image = value.getData();
                    mCover = new GvImage(image.getBitmap(), new GvDate(mNode.getPublishDate().getTime()), null, "", true);
                } else {
                    mCover = new GvImage();
                }
                mFindingCover = false;
                callback.onAdapterCover(mCover);
            }
        });
    }
}

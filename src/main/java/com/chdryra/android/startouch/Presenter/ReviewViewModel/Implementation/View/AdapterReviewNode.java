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

import android.graphics.Bitmap;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SubscribableReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;

/**
 * {@link ReviewViewAdapter} for a {@link ReviewNode}.
 */

public class AdapterReviewNode<T extends GvData> extends ReviewViewAdapterBasic<T> implements ReviewNode.NodeObserver {
    private final ReviewNode mNode;
    private final SubscribableReference<String> mSubject;
    private final SubscribableReference<Float> mRating;
    private final SubscribableReference<Bitmap> mCover;
    private final DataReference<ProfileImage> mProfileImage;
    private final DataConverter<DataImage, GvImage, GvImageList> mCoversConverter;
    private boolean mFindingCover = false;

    public AdapterReviewNode(ReviewNode node,
                             DataReference<ProfileImage> profileImage,
                             DataConverter<DataImage, GvImage, GvImageList> coversConverter,
                             GridDataWrapper<T> viewer) {
        super(viewer);
        mNode = node;
        mProfileImage = profileImage;
        mCoversConverter = coversConverter;
        mCover = new CoverReference();
        mSubject = new SubscribableReference<String>() {
            @Override
            protected void doDereferencing(DereferenceCallback<String> callback) {
                callback.onDereferenced(new DataValue<>(mNode.getSubject().getSubject()));
            }
        };
        mRating = new SubscribableReference<Float>() {
            @Override
            protected void doDereferencing(DereferenceCallback<Float> callback) {
                callback.onDereferenced(new DataValue<>(mNode.getRating().getRating()));
            }
        };
    }

    @Override
    public DataReference<String> getSubjectReference() {
        return mSubject;
    }

    @Override
    public DataReference<Float> getRatingReference() {
        return mRating;
    }

    @Override
    public DataReference<Bitmap> getCoverReference() {
        return mCover;
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
    protected void notifyDataObservers() {
        super.notifyDataObservers();
        mSubject.notifySubscribers();
        mRating.notifySubscribers();
        mCover.notifySubscribers();
    }

    private class CoverReference extends SubscribableReference<Bitmap> {
        private GvImage mCover = new GvImage();

        @Override
        protected void doDereferencing(final DereferenceCallback<Bitmap> callback) {
            if(!mFindingCover && mCover.getBitmap() == null) {
                mFindingCover = true;
                mNode.getCover().dereference(new DataReference.DereferenceCallback<DataImage>() {
                    @Override
                    public void onDereferenced(DataValue<DataImage> value) {
                        if(value.hasValue()) {
                            mCover = mCoversConverter.convert(value.getData());
                            mFindingCover = false;
                            callback.onDereferenced(new DataValue<>(mCover.getBitmap()));
                        } else {
                            dereferenceProfileImage(callback);
                        }
                    }
                });
            } else {
                callback.onDereferenced(new DataValue<>(mCover.getBitmap()));
            }
        }

        private void dereferenceProfileImage(final DereferenceCallback<Bitmap> callback) {
            mProfileImage.dereference(new DataReference.DereferenceCallback<ProfileImage>() {
                @Override
                public void onDereferenced(DataValue<ProfileImage> value) {
                    if (value.hasValue()) {
                        ProfileImage image = value.getData();
                        mCover = new GvImage(image.getBitmap(), new GvDate(mNode.getPublishDate().getTime()), null, "", true);
                    } else {
                        mCover = new GvImage();
                    }
                    mFindingCover = false;
                    callback.onDereferenced(new DataValue<>(mCover.getBitmap()));
                }
            });
        }
    }
}

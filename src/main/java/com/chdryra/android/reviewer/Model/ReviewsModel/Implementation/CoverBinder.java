/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 07/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class CoverBinder implements ReferenceBinder<DataImage> {
    private final Bitmap mPlaceholder;
    private final DataReference<ProfileImage> mProfileImage;
    private final CoverListener mListener;

    private ProfileImageBinder mProfileImageBinder;
    private boolean mCancelBinding = false;

    public interface CoverListener {
        void onCover(Bitmap cover);
    }

    public CoverBinder(DataReference<ProfileImage> profileImage,
                       Bitmap placeholder,
                       CoverListener listener) {
        mProfileImage = profileImage;
        mPlaceholder = placeholder;
        mListener = listener;
        mCancelBinding = false;
    }

    public void unbind() {
        mCancelBinding = true;
        if(mProfileImageBinder != null) mProfileImage.unbindFromValue(mProfileImageBinder);
    }

    @Override
    public void onInvalidated(DataReference<DataImage> reference) {
        bindToProfileImage();
    }

    @Override
    public void onReferenceValue(DataImage value) {
        Bitmap bitmap = value.getBitmap();
        if (bitmap == null) {
            if(!mCancelBinding) bindToProfileImage();
        } else {
            unbindFromProfile();
            notifyOnCover(bitmap);
        }
    }

    private void unbindFromProfile() {
        if (mProfileImage != null) {
            mProfileImage.unbindFromValue(mProfileImageBinder);
            mProfileImageBinder = null;
        }
    }

    private void bindToProfileImage() {
        if (mProfileImageBinder == null) {
            notifyOnCover(null);
            mProfileImageBinder = new ProfileImageBinder();
            mProfileImage.bindToValue(mProfileImageBinder);
        }
    }

    private void notifyOnCover(@Nullable Bitmap bitmap) {
        if(!mCancelBinding) {
            bitmap = bitmap != null ? bitmap : mPlaceholder;
            mListener.onCover(bitmap);
        }
    }

    private class ProfileImageBinder implements ReferenceBinder<ProfileImage> {
        @Override
        public void onInvalidated(DataReference<ProfileImage> reference) {
            notifyOnCover(null);
        }

        @Override
        public void onReferenceValue(ProfileImage value) {
            notifyOnCover(value.getBitmap());
        }
    }
}

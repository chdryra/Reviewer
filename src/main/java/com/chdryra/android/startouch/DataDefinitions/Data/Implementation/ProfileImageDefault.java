/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ProfileImageDefault implements ProfileImage {
    private Bitmap mBitmap;
    private AuthorId mAuthorId;

    public ProfileImageDefault() {
        mAuthorId = new DatumAuthorId();
        mBitmap = null;
    }

    public ProfileImageDefault(AuthorId authorId) {
        this(authorId, null);
    }

    public ProfileImageDefault(AuthorId authorId, @Nullable Bitmap bitmap) {
        mAuthorId = authorId;
        mBitmap = bitmap;
    }

    @Override
    @Nullable
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileImageDefault)) return false;

        ProfileImageDefault that = (ProfileImageDefault) o;

        if (mBitmap != null ? !mBitmap.equals(that.mBitmap) : that.mBitmap != null) return false;
        return mAuthorId != null ? mAuthorId.equals(that.mAuthorId) : that.mAuthorId == null;

    }

    @Override
    public int hashCode() {
        int result = mBitmap != null ? mBitmap.hashCode() : 0;
        result = 31 * result + (mAuthorId != null ? mAuthorId.hashCode() : 0);
        return result;
    }
}

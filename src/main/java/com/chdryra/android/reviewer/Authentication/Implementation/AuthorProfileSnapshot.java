
/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.AuthorReferenceDefault;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataReferenceWrapper;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorProfileSnapshot implements AuthorProfile{
    private NamedAuthor mAuthor;
    private DateTime mDateJoined;
    private Bitmap mProfilePhoto;

    public AuthorProfileSnapshot() {
    }

    public AuthorProfileSnapshot(NamedAuthor author, DateTime dateJoined, @Nullable Bitmap profilePhoto) {
        mAuthor = author;
        mDateJoined = dateJoined;
        mProfilePhoto = profilePhoto;
    }

    public NamedAuthor getNamedAuthor() {
        return mAuthor;
    }

    public DateTime getJoined() {
        return mDateJoined;
    }

    public Bitmap getPhoto() {
        return mProfilePhoto;
    }

    @Override
    public AuthorReference getAuthor() {
        return new AuthorReferenceDefault(mAuthor.getAuthorId(), new DataReferenceWrapper<>(mAuthor));
    }

    @Override
    public void getProfileSnapshot(ProfileCallback callback) {
        callback.onProfile(this, CallbackMessage.ok());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorProfileSnapshot)) return false;

        AuthorProfileSnapshot that = (AuthorProfileSnapshot) o;

        if (!mAuthor.getName().equals(that.mAuthor.getName())) return false;
        if (!mAuthor.getAuthorId().toString().equals(that.mAuthor.getAuthorId().toString())) return false;
        if (!(mDateJoined.getTime() == that.mDateJoined.getTime())) return false;
        return mProfilePhoto != null ? mProfilePhoto.equals(that.mProfilePhoto) : that
                .mProfilePhoto == null;

    }

    @Override
    public int hashCode() {
        int result = mAuthor.hashCode();
        result = 31 * result + mDateJoined.hashCode();
        result = 31 * result + (mProfilePhoto != null ? mProfilePhoto.hashCode() : 0);
        return result;
    }
}


/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.AuthorReferenceDefault;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataReferenceWrapper;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorProfile implements ProfileReference {
    private NamedAuthor mAuthor;
    private DateTime mDateJoined;
    private ProfileImage mProfilePhoto;

    public AuthorProfile() {
    }

    public AuthorProfile(NamedAuthor author, DateTime dateJoined, @Nullable ProfileImage profilePhoto) {
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

    public ProfileImage getImage() {
        return mProfilePhoto;
    }

    @Override
    public AuthorReference getAuthor() {
        return new AuthorReferenceDefault(mAuthor.getAuthorId(), new DataReferenceWrapper<>(mAuthor));
    }

    @Override
    public DataReference<ProfileImage> getProfileImage() {
        return new DataReferenceWrapper<>(mProfilePhoto);
    }

    @Override
    public void dereference(Callback callback) {
        callback.onProfile(this, CallbackMessage.ok());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorProfile)) return false;

        AuthorProfile that = (AuthorProfile) o;

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

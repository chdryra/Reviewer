/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ProfileImageDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorProfileDefault implements AuthorProfile {
    private AuthorName mAuthor;
    private DateTime mDateJoined;
    private ProfileImage mProfilePhoto;

    public AuthorProfileDefault() {
        this(new AuthorNameDefault(), new DatumDateTime(), new ProfileImageDefault());
    }

    public AuthorProfileDefault(AuthorName author, DateTime dateJoined, ProfileImage profilePhoto) {
        mAuthor = author;
        mDateJoined = dateJoined;
        mProfilePhoto = profilePhoto;
    }

    @Override
    public AuthorName getAuthor() {
        return mAuthor;
    }

    @Override
    public DateTime getJoined() {
        return mDateJoined;
    }

    @Override
    public ProfileImage getImage() {
        return mProfilePhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorProfileDefault)) return false;

        AuthorProfileDefault that = (AuthorProfileDefault) o;

        if (!mAuthor.getName().equals(that.mAuthor.getName())) return false;
        if (!mAuthor.getAuthorId().toString().equals(that.mAuthor.getAuthorId().toString()))
            return false;
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

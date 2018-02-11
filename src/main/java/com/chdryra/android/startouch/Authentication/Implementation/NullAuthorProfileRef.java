
/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.AuthorReferenceDefault;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullAuthorProfileRef implements AuthorProfileRef {
    @Override
    public AuthorId getAuthorId() {
        return new DatumAuthorId();
    }

    @Override
    public void dereference(DereferenceCallback<AuthorProfile> callback) {

    }

    @Override
    public AuthorReference getAuthor() {
        return new AuthorReferenceDefault();
    }

    @Override
    public void bindToValue(ReferenceBinder<AuthorProfile> binder) {

    }

    @Override
    public DataReference<ProfileImage> getProfileImage() {
        return new NullDataReference<>();
    }

    @Override
    public void unbindFromValue(ReferenceBinder<AuthorProfile> binder) {

    }

    @Override
    public void registerListener(InvalidationListener listener) {

    }

    @Override
    public void unregisterListener(InvalidationListener listener) {

    }

    @Override
    public boolean isValidReference() {
        return false;
    }

    @Override
    public void invalidate() {

    }
}

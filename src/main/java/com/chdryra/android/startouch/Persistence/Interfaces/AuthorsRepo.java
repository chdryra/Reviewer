/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Interfaces;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AuthorsRepo {
    int SEARCH_LIMIT = 8;
    enum Error{NAME_NOT_FOUND, NETWORK_ERROR, CANCELLED}

    interface SearchAuthorsCallback {
        void onAuthors(List<AuthorName> suggestions, CallbackMessage message);
    }

    interface AuthorIdCallback {
        void onAuthorId(DataReference<AuthorId> authorId, CallbackMessage message);
    }

    AuthorReference getReference(AuthorId authorId);

    AuthorProfileRef getAuthorProfile(AuthorId authorId);

    SocialProfileRef getSocialProfile(AuthorId authorId);

    void getAuthorId(String name, AuthorIdCallback callback);

    void search(String nameQuery, SearchAuthorsCallback callback);
}

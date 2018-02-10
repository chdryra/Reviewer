/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Interfaces;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;
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
        void onAuthors(List<NamedAuthor> suggestions, CallbackMessage message);
    }

    interface AuthorIdCallback {
        void onAuthorId(DataReference<AuthorId> authorId, CallbackMessage message);
    }

    AuthorReference getReference(AuthorId authorId);

    ProfileReference getProfile(AuthorId authorId);

    void getAuthorId(String name, AuthorIdCallback callback);

    void search(String nameQuery, SearchAuthorsCallback callback);
}

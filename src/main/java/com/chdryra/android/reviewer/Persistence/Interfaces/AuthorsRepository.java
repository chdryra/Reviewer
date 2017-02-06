/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AuthorsRepository {
    int SEARCH_LIMIT = 8;
    enum Error{NAME_NOT_FOUND, NETWORK_ERROR, CANCELLED}

    interface SearchAuthorsCallback {
        void onAuthors(List<NamedAuthor> suggestions, CallbackMessage message);
    }

    interface AuthorIdCallback {
        void onAuthorId(DataReference<AuthorId> authorId, CallbackMessage message);
    }

    AuthorReference getReference(AuthorId authorId);

    AuthorProfile getProfile(AuthorId authorId);

    void getAuthorId(String name, AuthorIdCallback callback);

    void search(String nameQuery, SearchAuthorsCallback callback);
}

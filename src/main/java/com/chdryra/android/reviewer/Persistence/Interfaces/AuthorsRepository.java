/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AuthorsRepository {
    enum Error{NAME_NOT_FOUND, NETWORK_ERROR}

    interface GetAuthorIdCallback {
        void onAuthorId(DataReference<AuthorId> authorId, @Nullable AuthorsRepository.Error error);
    }

    DataReference<NamedAuthor> getName(AuthorId authorId);

    void getAuthorId(String name, GetAuthorIdCallback callback);
}

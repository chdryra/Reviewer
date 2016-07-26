/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DefaultAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;

import java.util.UUID;

/**
 * Unique user ID for author credentials. A Wrapper for a UUID.
 * <p/>
 * <p>
 * Use static method <code>generateId()</code> to return a unique UserId.
 * </p>
 *
 * @see DefaultNamedAuthor
 */

public class AuthorIdGenerator {
    private AuthorIdGenerator() {

    }

    public static AuthorId newId() {
        return new DefaultAuthorId(UUID.randomUUID().toString());
    }

    public static AuthorId toId(String id) {
        return new DefaultAuthorId(id);
    }
}
/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterAuthorId implements SnapshotConverter<AuthorId> {
    @Override
    @Nullable
    public AuthorId convert(DataSnapshot snapshot) {
        String authorId = snapshot.getValue(String.class);
        return authorId != null ? new AuthorIdParcelable(authorId) : null;
    }

    public static class AsIndex extends ConverterAuthorId {
        @Nullable
        @Override
        public AuthorId convert(DataSnapshot snapshot) {
            String authorId = snapshot.getKey();
            return authorId != null ? new AuthorIdParcelable(authorId) : null;
        }
    }
}

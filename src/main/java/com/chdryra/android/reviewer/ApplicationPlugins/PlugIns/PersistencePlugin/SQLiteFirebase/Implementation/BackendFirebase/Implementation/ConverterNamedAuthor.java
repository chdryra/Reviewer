/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterNamedAuthor implements SnapshotConverter<NamedAuthor> {
    @Override
    @Nullable
    public NamedAuthor convert(DataSnapshot snapshot) {
        String id = snapshot.getKey();
        String name = snapshot.getValue(String.class);
        return name != null ? new DefaultNamedAuthor(name, new AuthorIdParcelable(id)) : null;
    }
}

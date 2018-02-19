/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorListRef extends FbListReference<AuthorId, List<AuthorId>> implements AuthorListRef {
    public FbAuthorListRef(Firebase reference,
                           SnapshotConverter<List<AuthorId>> listConverter,
                           SnapshotConverter<AuthorId> itemConverter,
                           SizeReferencer sizeReferencer) {
        super(reference, listConverter, itemConverter, sizeReferencer);
    }


    @Nullable
    @Override
    protected List<AuthorId> getNullValue() {
        return new ArrayList<>();
    }
}

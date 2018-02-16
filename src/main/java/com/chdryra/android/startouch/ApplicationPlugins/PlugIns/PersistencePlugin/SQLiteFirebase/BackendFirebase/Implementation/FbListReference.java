/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SizeReferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.firebase.client.Firebase;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbListReference<T, C extends Collection<T>> extends FbListReferenceBasic<T, C, Size> {
    private SizeReferencer mSizeReferencer;

    public FbListReference(Firebase reference,
                           SnapshotConverter<C> listConverter,
                           SnapshotConverter<T> itemConverter,
                           SizeReferencer sizeReferencer) {
        super(reference, listConverter, itemConverter);
        mSizeReferencer = sizeReferencer;
    }

    @Override
    public DataReference<Size> getSize() {
        return mSizeReferencer.newSizeReference(this);
    }
}

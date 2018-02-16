/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;


import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsDbReadable extends FbReviewsRepoBasic {
    private final FbAuthorsDb mStructure;

    public FbAuthorsDbReadable(Firebase dataBase,
                               FbAuthorsDb structure,
                               SnapshotConverter<ReviewReference> converter,
                               ReviewDereferencer dereferencer,
                               SizeReferencer sizeReferencer) {
        super(dataBase, structure, converter, dereferencer, sizeReferencer);
        mStructure = structure;
    }

    FbAuthorsDb getStructure() {
        return mStructure;
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories;


import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterCollectionItem;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbReviewCollection;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbReviewIdList;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryFbCollection {
    private final FbReviewsStructure mStructure;
    private ReviewsRepoReadable mMasterRepo;

    public FactoryFbCollection(FbReviewsStructure structure) {
        mStructure = structure;
    }

    public void setMasterRepo(ReviewsRepoReadable masterRepo) {
        mMasterRepo = masterRepo;
    }

    ReviewCollection newCollection(Firebase root, String name, AuthorId authorId,
                                   ReviewDereferencer dereferencer, SizeReferencer sizeReferencer) {
        FbReviewIdList list = new FbReviewIdList(root, mStructure, sizeReferencer, name,
                authorId, mMasterRepo, new ConverterCollectionItem());
        return new FbReviewCollection(list, dereferencer);
    }
}

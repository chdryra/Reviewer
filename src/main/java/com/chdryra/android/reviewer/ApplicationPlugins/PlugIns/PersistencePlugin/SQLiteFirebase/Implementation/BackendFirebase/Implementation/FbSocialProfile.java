/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbSocialStructure;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbSocialProfile implements SocialProfile {
    private AuthorId mId;
    private Firebase mRoot;
    private FbSocialStructure mStructure;
    private FactoryFbReference mReferencer;

    public FbSocialProfile(AuthorId id, Firebase root, FbSocialStructure structure,
                           FactoryFbReference referencer) {
        mId = id;
        mRoot = root;
        mStructure = structure;
        mReferencer = referencer;
    }

    @Override
    public AuthorId getAuthorId() {
        return mId;
    }

    @Override
    public RefAuthorList getFollowing() {
        return mReferencer.newAuthorList(mStructure.getFollowingDb(mRoot, mId));
    }

    @Override
    public RefAuthorList getFollowers() {
        return mReferencer.newAuthorList(mStructure.getFollowersDb(mRoot, mId));
    }
}

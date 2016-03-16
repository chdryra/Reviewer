/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendDatabase;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFirebaseDb {
    private Context mContext;

    public FactoryFirebaseDb(Context context) {
        Firebase.setAndroidContext(context);
        mContext = context;
    }

    public Firebase newFirebase(int urlStringId) {
        return new Firebase(mContext.getString(urlStringId));
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewRefData<T extends HasReviewId> extends FbRefData<T> implements ReviewDataReference<T>{
    private ReviewId mId;

    public FbReviewRefData(ReviewId id, Firebase reference, SnapshotConverter<T> converter) {
        super(reference, converter);
        mId = id;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }
}

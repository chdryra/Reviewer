/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbSentence extends FbReviewItemRef<DataComment> implements CommentReference.Sentence {
    private CommentReference mParent;

    public FbSentence(ReviewId id, Firebase reference, SnapshotConverter<DataComment> converter,
                      CommentReference parent) {
        super(id, reference, converter);
        mParent = parent;
    }

    @Override
    public CommentReference getParent() {
        return mParent;
    }
}

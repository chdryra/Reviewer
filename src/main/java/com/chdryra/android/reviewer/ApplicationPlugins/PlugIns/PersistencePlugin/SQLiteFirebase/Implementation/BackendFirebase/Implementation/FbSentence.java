/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbSentence extends FbReviewItemRef<DataComment> implements RefComment {
    private RefComment mParent;

    public FbSentence(ReviewId id,
                      Firebase reference,
                      SnapshotConverter<DataComment> converter,
                      RefComment parent) {
        super(id, reference, converter);
        mParent = parent;
    }

    @Override
    public RefComment getParent() {
        return mParent;
    }

    @Override
    public RefComment getFirstSentence() {
        return this;
    }

    @Override
    public void toSentences(SentencesCallback callback) {
        IdableList<RefComment> sentences = new IdableDataList<>(getReviewId());
        sentences.add(this);
        callback.onSentenceReferences(sentences);
    }

    @Override
    public boolean isHeadline() {
        return mParent.isHeadline();
    }
}

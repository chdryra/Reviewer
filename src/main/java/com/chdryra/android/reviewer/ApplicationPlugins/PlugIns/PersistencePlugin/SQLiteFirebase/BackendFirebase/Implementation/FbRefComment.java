/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryListItemsReferencer;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbRefComment extends FbReviewItemRef<DataComment> implements RefComment {
    private static final String SENTENCES = Comment.SENTENCES;
    private final ReviewItemReference<DataSize> mSizeReference;
    private final boolean mIsHeadline;
    private final ListItemsReferencer<DataComment, RefComment> mReferencer;

    public FbRefComment(ReviewId id,
                        Firebase reference,
                        ReviewItemReference<DataSize> numSentences,
                        SnapshotConverter<DataComment> commentConverter,
                        FactoryListItemsReferencer referencerFactory,
                        boolean isHeadline) {
        super(id, reference, commentConverter);
        mSizeReference = numSentences;
        mIsHeadline = isHeadline;
        mReferencer = referencerFactory.newSentencesReferencer(this);
    }

    @Nullable
    @Override
    public RefComment getParent() {
        return null;
    }

    @Override
    public RefComment getFirstSentence() {
        return mReferencer.toItemReference(getReviewId(), getReference().child(SENTENCES), 0);
    }

    @Override
    public void toSentences(final SentencesCallback callback) {
        mReferencer.toItemReferences(getReference().child(SENTENCES), mSizeReference,
                new ListItemsReferencer.Callback<DataComment, RefComment>() {
            @Override
            public void onItemReferences(IdableList<RefComment> sentences) {
                callback.onSentenceReferences(sentences);
            }
        });
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }
}

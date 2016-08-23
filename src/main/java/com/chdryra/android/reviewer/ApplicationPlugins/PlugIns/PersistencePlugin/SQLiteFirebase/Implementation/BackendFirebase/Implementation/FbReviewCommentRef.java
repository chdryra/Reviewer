/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryListItemsReferencer;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReviewCommentRef extends FbReviewItemRef<DataComment> implements CommentReference {
    private ReviewItemReference<DataSize> mSizeReference;
    private boolean mIsHeadline;
    private ListItemsReferencer<DataComment, Sentence> mReferencer;

    public FbReviewCommentRef(ReviewId id,
                              Firebase reference,
                              ReviewItemReference<DataSize> numSentences,
                              SnapshotConverter<DataComment> converter,
                              FactoryListItemsReferencer referencerFactory,
                              boolean isHeadline) {
        super(id, reference, converter);
        mSizeReference = numSentences;
        mIsHeadline = isHeadline;
        mReferencer = referencerFactory.newSentencesReferencer(converter, this);
    }

    @Override
    public Sentence getFirstSentence() {
        return mReferencer.toItemReference(getReference(), getReviewId(), 0);
    }

    @Override
    public void toSentences(final SentencesCallback callback) {
        mReferencer.toItemReferences(getReference(), mSizeReference,
                new ListItemsReferencer.Callback<DataComment, Sentence>() {
            @Override
            public void onItemReferences(IdableList<Sentence> sentences) {
                callback.onSentenceReferences(sentences);
            }
        });
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }

}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentsListReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbCommentsListReference extends FbReviewListRef<DataComment> implements CommentsListReference {
    private ListItemsReferencer<DataComment, CommentReference> mReferencer;

    public FbCommentsListReference(ReviewId id, Firebase reference, ReviewItemReference<DataSize>
            sizeReference, ListConverter<DataComment> converter, ListItemsReferencer<DataComment, CommentReference> referencer) {
        super(id, reference, sizeReference, converter, referencer.asBaseReferencer());
        mReferencer = referencer;
    }

    @Override
    public void toCommentReferences(final CommentReferencesCallback callback) {
        if(isValidReference()) {
            mReferencer.toItemReferences(getReference(), getSize(), new ListItemsReferencer.Callback<DataComment, CommentReference>() {
                @Override
                public void onItemReferences(IdableList<CommentReference> references) {
                    callback.onCommentReferences(references);
                }
            });
        } else {
            callback.onCommentReferences(new IdableDataList<CommentReference>(getReviewId()));
        }
    }

    @Override
    public void toSentences(final CommentReference.SentencesCallback callback) {
        toCommentReferences(new CommentReferencesCallback() {
            @Override
            public void onCommentReferences(IdableList<CommentReference> references) {
                new SentencesCollection(references, callback).collect();
            }
        });
    }

    private class SentencesCollection {
        private IdableList<CommentReference> mReferences;
        private CommentReference.SentencesCallback mCallback;
        private int mCount = 0;
        private IdableList<CommentReference.Sentence> mResults;

        private SentencesCollection(IdableList<CommentReference> references, CommentReference
                .SentencesCallback callback) {
            mReferences = references;
            mCallback = callback;
        }

        private void collect() {
            mResults = new IdableDataList<>(mReferences.getReviewId());
            mCount = 0;
            for(CommentReference reference : mReferences) {
                if(reference.isValidReference()) {
                    reference.toSentences(new CommentReference.SentencesCallback() {
                        @Override
                        public void onSentenceReferences(IdableList<CommentReference.Sentence> references) {
                            mResults.addAll(references);
                            if(++mCount == mReferences.size()) callback();
                        }
                    });
                }
            }
        }

        private void callback() {
            mCallback.onSentenceReferences(mResults);
        }
    }
}

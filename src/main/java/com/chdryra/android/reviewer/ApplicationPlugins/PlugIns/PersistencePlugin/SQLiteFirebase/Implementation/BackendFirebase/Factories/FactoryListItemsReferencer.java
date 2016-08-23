/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbReviewCommentRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbSentence;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryListItemsReferencer {
    private FactoryFbDataReference mReferenceFactory;

    public FactoryListItemsReferencer(FactoryFbDataReference referenceFactory) {
        mReferenceFactory = referenceFactory;
    }

    public <T extends HasReviewId, R extends ReviewItemReference<T>>
    ListItemsReferencer<T, R> newReferencer(ListItemsReferencer.ItemReferenceFactory<T, R>
                                                    factory) {
        return new ListItemsReferencer<>(factory);
    }

    public <T extends HasReviewId>
    ListItemsReferencer<T, ReviewItemReference<T>> newReferencer(final SnapshotConverter<T>
                                                                         converter) {
        return newReferencer(newDefaultFactory(converter));
    }

    public ListItemsReferencer<DataComment, CommentReference> newCommentsReferencer
            (SnapshotConverter<DataComment> converter) {
        return newReferencer(newCommentReferenceFactory(converter));
    }

    public ListItemsReferencer<DataComment, CommentReference.Sentence> newSentencesReferencer
            (final SnapshotConverter<DataComment> converter, final CommentReference parent) {
        return newReferencer(new ListItemsReferencer.ItemReferenceFactory<DataComment,
                CommentReference.Sentence>() {
            @Override
            public CommentReference.Sentence newReference(ReviewId id, Firebase child, int index) {
                return new FbSentence(id, child, converter, parent);
            }
        });
    }

    @NonNull
    private <T extends HasReviewId> ListItemsReferencer.ItemReferenceFactory<T,
            ReviewItemReference<T>> newDefaultFactory(final SnapshotConverter<T> converter) {
        return new ListItemsReferencer.ItemReferenceFactory<T, ReviewItemReference<T>>() {
            @Override
            public ReviewItemReference<T> newReference(ReviewId id, Firebase child, int index) {
                return new FbReviewItemRef<>(id, child, converter);
            }
        };
    }

    @NonNull
    private ListItemsReferencer.ItemReferenceFactory<DataComment, CommentReference>
    newCommentReferenceFactory(final SnapshotConverter<DataComment> converter) {
        return new ListItemsReferencer.ItemReferenceFactory<DataComment, CommentReference>() {
            @Override
            public CommentReference newReference(ReviewId id, Firebase child, int index) {
                ReviewItemReference<DataSize> numSentences = mReferenceFactory.newSize(child
                        .child(Comment.NUM_SENTENCES), id);
                return new FbReviewCommentRef(id, child, numSentences, converter,
                        FactoryListItemsReferencer.this, index == 0);

            }
        };
    }
}

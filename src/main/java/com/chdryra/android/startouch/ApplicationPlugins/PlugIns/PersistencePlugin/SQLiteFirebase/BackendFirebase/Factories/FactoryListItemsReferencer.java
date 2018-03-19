/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Comment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterComments;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbCommentRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbSentence;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryListItemsReferencer {
    private final FbDataReferencer mReferenceFactory;

    public FactoryListItemsReferencer(FbDataReferencer referenceFactory) {
        mReferenceFactory = referenceFactory;
    }

    public <T extends HasReviewId>
    ListItemsReferencer<T, ReviewItemReference<T>> newReferencer(final SnapshotConverter<T>
                                                                         converter) {
        return newReferencer(newDefaultFactory(converter));
    }

    public ListItemsReferencer<DataComment, CommentRef> newCommentsReferencer
            (SnapshotConverter<DataComment> converter) {
        return newReferencer(newCommentReferenceFactory(converter));
    }

    public ListItemsReferencer<DataComment, CommentRef> newSentencesReferencer
            (final CommentRef parent) {
        final SnapshotConverter<DataComment> converter =
                new ConverterComments.SentenceConverter(parent.getReviewId(),
                        new ConverterComment.ConverterSentence(), parent.isHeadline());
        return newReferencer(new ListItemsReferencer.ItemReferenceFactory<DataComment,
                CommentRef>() {
            @Override
            public CommentRef newReference(ReviewId id, Firebase child, int index) {
                return new FbSentence(id, child, converter, parent);
            }
        });
    }

    private <T extends HasReviewId, R extends ReviewItemReference<T>>
    ListItemsReferencer<T, R> newReferencer(ListItemsReferencer.ItemReferenceFactory<T, R>
                                                    factory) {
        return new ListItemsReferencer<>(factory);
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
    private ListItemsReferencer.ItemReferenceFactory<DataComment, CommentRef>
    newCommentReferenceFactory(final SnapshotConverter<DataComment> converter) {
        return new ListItemsReferencer.ItemReferenceFactory<DataComment, CommentRef>() {
            @Override
            public CommentRef newReference(ReviewId id, Firebase child, int index) {
                ReviewItemReference<DataSize> numSentences = mReferenceFactory.newSize(child
                        .child(Comment.NUM_SENTENCES), id);
                boolean isHeadline = index == 0;
                return new FbCommentRef(id, child, numSentences, converter,
                        FactoryListItemsReferencer.this, isHeadline);

            }
        };
    }
}

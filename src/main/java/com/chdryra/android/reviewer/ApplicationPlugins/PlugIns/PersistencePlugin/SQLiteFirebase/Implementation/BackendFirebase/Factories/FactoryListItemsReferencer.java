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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbRefComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbSentence;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryListItemsReferencer {
    private FactoryFbReference mReferenceFactory;

    public FactoryListItemsReferencer(FactoryFbReference referenceFactory) {
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

    public ListItemsReferencer<DataComment, RefComment> newCommentsReferencer
            (SnapshotConverter<DataComment> converter) {
        return newReferencer(newCommentReferenceFactory(converter));
    }

    public ListItemsReferencer<DataComment, RefComment> newSentencesReferencer
            (final SnapshotConverter<DataComment> converter, final RefComment parent) {
        return newReferencer(new ListItemsReferencer.ItemReferenceFactory<DataComment, RefComment>() {
            @Override
            public RefComment newReference(ReviewId id, Firebase child, int index) {
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
    private ListItemsReferencer.ItemReferenceFactory<DataComment, RefComment>
    newCommentReferenceFactory(final SnapshotConverter<DataComment> converter) {
        return new ListItemsReferencer.ItemReferenceFactory<DataComment, RefComment>() {
            @Override
            public RefComment newReference(ReviewId id, Firebase child, int index) {
                ReviewItemReference<DataSize> numSentences = mReferenceFactory.newSize(child
                        .child(Comment.NUM_SENTENCES), id);
                return new FbRefComment(id, child, numSentences, converter,
                        FactoryListItemsReferencer.this, index == 0);

            }
        };
    }
}

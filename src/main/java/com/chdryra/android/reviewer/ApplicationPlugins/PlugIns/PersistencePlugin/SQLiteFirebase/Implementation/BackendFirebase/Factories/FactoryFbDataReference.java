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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterAuthorId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterNamedAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterReviewTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterSize;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbCommentsListReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbRefData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewListRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ListConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ReviewItemConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.NullDataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.CommentsListReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFbDataReference {
    private FactoryListItemsReferencer mReferencerFactory;

    public FactoryFbDataReference() {
        mReferencerFactory = new FactoryListItemsReferencer(this);
    }

    public DataReference<AuthorId> newAuthorId(Firebase ref) {
        return new FbRefData<>(ref, new ConverterAuthorId());
    }

    public DataReference<NamedAuthor> newNamedAuthor(Firebase ref) {
        return new FbRefData<>(ref, new ConverterNamedAuthor());
    }

    public ReviewItemReference<DataSize> newSize(Firebase ref, ReviewId id) {
        return new FbReviewItemRef<>(id, ref, new ConverterSize(id));
    }

    public ReviewItemReference<DataImage> newImage(Firebase ref, ReviewId id) {
        return new FbReviewItemRef<>(id, ref, new ConverterImage(id));
    }

    public ReviewListReference<DataCriterion> newCriteria(Firebase ref,
                                                          ReviewId id,
                                                          ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterCriterion());
    }

    public ReviewListReference<DataTag> newTags(Firebase ref, ReviewId id, 
                                                ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterReviewTag());
    }

    public ReviewListReference<DataImage> newImages(Firebase ref, ReviewId id, 
                                                    ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterImage());
    }

    public ReviewListReference<DataLocation> newLocations(Firebase ref, ReviewId id, 
                                                          ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterLocation());
    }

    public ReviewListReference<DataFact> newFacts(Firebase ref, ReviewId id, 
                                                  ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterFact());
    }

    public CommentsListReference newComments(Firebase ref, ReviewId id,
                                             ReviewItemReference<DataSize> size) {
        ListConverter<DataComment> converter = new ListConverter<>(id, new ConverterComment());
        ListItemsReferencer<DataComment, CommentReference> referencer
                = mReferencerFactory.newCommentsReferencer(converter.getItemConverter());
        return new FbCommentsListReference(id, ref, size, converter, referencer);
    }



    public <T> DataReference<T> newNullReference(Class<T> clazz) {
        return new NullDataReference<>();
    }

    @NonNull
    private <T extends HasReviewId> ReviewListReference<T> newListReference(Firebase ref, 
                                                                            ReviewId id, 
                                                                            ReviewItemReference<DataSize> size, ReviewItemConverter<T> itemConverter) {
        final ListConverter<T> converter = newListConverter(id, itemConverter);
        return new FbReviewListRef<>(id, ref, size, converter, newItemsReferencer(converter
                .getItemConverter()));
    }

    @NonNull
    private <T extends HasReviewId> ListItemsReferencer<T, ReviewItemReference<T>> 
    newItemsReferencer(final SnapshotConverter<T> converter) {
        return mReferencerFactory.newReferencer(converter);
    }

    @NonNull
    private <T extends HasReviewId> ListConverter<T> newListConverter(ReviewId id, 
                                                                      ReviewItemConverter<T> 
                                                                              itemConverter) {
        return new ListConverter<>(id, itemConverter);
    }
}

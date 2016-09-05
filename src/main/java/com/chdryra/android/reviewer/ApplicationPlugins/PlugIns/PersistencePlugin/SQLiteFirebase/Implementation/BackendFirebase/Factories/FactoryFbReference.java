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

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.CommentsConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterAuthorId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterNamedAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterReviewTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterSize;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbRefCommentList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbRefData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbRefDatalist;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ListConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ReviewItemConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.SnapshotConverter;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.SentencesCollector;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFbReference {
    private final FactoryListItemsReferencer mReferencerFactory;

    public FactoryFbReference() {
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

    public RefDataList<DataCriterion> newCriteria(Firebase ref,
                                                  ReviewId id,
                                                  ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterCriterion());
    }

    public RefDataList<DataTag> newTags(Firebase ref, ReviewId id,
                                                ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterReviewTag());
    }

    public RefDataList<DataImage> newImages(Firebase ref, ReviewId id,
                                                    ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterImage());
    }

    public RefDataList<DataLocation> newLocations(Firebase ref, ReviewId id,
                                                          ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterLocation());
    }

    public RefDataList<DataFact> newFacts(Firebase ref, ReviewId id,
                                                  ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterFact());
    }

    public RefCommentList newComments(Firebase ref, ReviewId id,
                                      ReviewItemReference<DataSize> size) {
        CommentsConverter converter = new CommentsConverter(id, new ConverterComment());
        ListItemsReferencer<DataComment, RefComment> referencer
                = mReferencerFactory.newCommentsReferencer(converter.getItemConverter());
        return new FbRefCommentList(id, ref, size, converter, referencer, this);
    }



    public <T> DataReference<T> newNullReference(Class<T> clazz) {
        return new NullDataReference<>();
    }

    @NonNull
    private <Value extends HasReviewId> RefDataList<Value> newListReference(Firebase ref,
                                                                            ReviewId id,
                                                                            ReviewItemReference<DataSize> size,
                                                                            ReviewItemConverter<Value> itemConverter) {
        final ListConverter<Value> converter = newListConverter(id, itemConverter);
        return new FbRefDatalist<>(id, ref, size, converter, newItemsReferencer(converter.getItemConverter()));
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

    public SentencesCollector newSentencesCollector(IdableList<RefComment> comments) {
        return new SentencesCollector(comments);
    }
}

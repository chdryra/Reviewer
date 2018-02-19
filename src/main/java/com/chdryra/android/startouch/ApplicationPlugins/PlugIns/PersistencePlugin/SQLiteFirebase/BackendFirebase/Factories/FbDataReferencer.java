/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbAuthorListRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterAuthorId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterComments;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterIdNamedAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterProfileImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterReviewTag;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterSize;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbCommentListRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbRefData;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbDataListRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.IdableListConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ListConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.AuthorRefDefault;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.NullDataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SentencesCollector;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbDataReferencer {
    private final SizeReferencer mSizeReferencer;
    private final FactoryListItemsReferencer mReferencerFactory;

    public FbDataReferencer() {
        mSizeReferencer = new SizeReferencer();
        mReferencerFactory = new FactoryListItemsReferencer(this);
    }

    public AuthorListRef newAuthorList(Firebase ref) {
        ConverterAuthorId converter = new ConverterAuthorId.AsIndex();
        return new FbAuthorListRef(ref, new ListConverter<>(converter), converter, mSizeReferencer);
    }

    public DataReference<AuthorId> newAuthorId(Firebase ref) {
        return new FbRefData<>(ref, new ConverterAuthorId());
    }

    public AuthorRef newAuthorName(Firebase ref, AuthorId id) {
        return new AuthorRefDefault(id, new FbRefData<>(ref, new ConverterIdNamedAuthor()));
    }

    public DataReference<ProfileImage> newImageReference(Firebase ref, AuthorId id) {
        return new FbRefData<>(ref, new ConverterProfileImage(id));
    }

    public ReviewItemReference<DataSize> newSize(Firebase ref, ReviewId id) {
        return new FbReviewItemRef<>(id, ref, new ConverterSize(id));
    }

    public ReviewItemReference<DataImage> newImage(Firebase ref, ReviewId id) {
        return new FbReviewItemRef<>(id, ref, new ConverterImage(id));
    }

    public DataListRef<DataCriterion> newCriteria(Firebase ref,
                                                  ReviewId id,
                                                  ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterCriterion());
    }

    public DataListRef<DataTag> newTags(Firebase ref, ReviewId id,
                                        ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterReviewTag());
    }

    public DataListRef<DataImage> newImages(Firebase ref, ReviewId id,
                                            ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterImage());
    }

    public DataListRef<DataLocation> newLocations(Firebase ref, ReviewId id,
                                                  ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterLocation());
    }

    public DataListRef<DataFact> newFacts(Firebase ref, ReviewId id,
                                          ReviewItemReference<DataSize> size) {
        return newListReference(ref, id, size, new ConverterFact());
    }

    public CommentListRef newComments(Firebase ref, ReviewId id,
                                      ReviewItemReference<DataSize> size) {
        ConverterComments converter = new ConverterComments(id, new ConverterComment());
        ListItemsReferencer<DataComment, CommentRef> referencer
                = mReferencerFactory.newCommentsReferencer(converter.getItemConverter());
        return new FbCommentListRef(id, ref, size, converter, referencer, this);
    }



    public <T> DataReference<T> newNullReference(Class<T> clazz) {
        return new NullDataReference<>();
    }

    @NonNull
    private <Value extends HasReviewId> DataListRef<Value> newListReference(Firebase ref,
                                                                            ReviewId id,
                                                                            ReviewItemReference<DataSize> size,
                                                                            ReviewItemConverter<Value> itemConverter) {
        final IdableListConverter<Value> converter = newListConverter(id, itemConverter);
        return new FbDataListRef<>(id, ref, size, converter, newItemsReferencer(converter.getItemConverter()));
    }

    @NonNull
    private <T extends HasReviewId> ListItemsReferencer<T, ReviewItemReference<T>> 
    newItemsReferencer(final SnapshotConverter<T> converter) {
        return mReferencerFactory.newReferencer(converter);
    }

    @NonNull
    private <T extends HasReviewId> IdableListConverter<T> newListConverter(ReviewId id,
                                                                            ReviewItemConverter<T>
                                                                              itemConverter) {
        return new IdableListConverter<>(id, itemConverter);
    }

    public SentencesCollector newSentencesCollector(IdableList<CommentRef> comments) {
        return new SentencesCollector(comments);
    }
}

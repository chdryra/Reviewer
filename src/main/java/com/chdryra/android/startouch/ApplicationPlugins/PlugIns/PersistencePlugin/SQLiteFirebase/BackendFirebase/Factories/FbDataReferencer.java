/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterAuthorId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterProfileImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterComments;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterIdNamedAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterReviewTag;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterSize;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbListReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbRefCommentList;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbRefData;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbRefDatalist;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbReviewItemRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbSocialProfile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.IdableListConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ListConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ListItemsReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbSocialStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.Authentication.Implementation.NullSocialProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileSocial;
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
import com.chdryra.android.startouch.DataDefinitions.References.Implementation
        .AuthorReferenceDefault;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SentencesCollector;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefAuthorList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbDataReferencer {
    private final FactoryListItemsReferencer mReferencerFactory;

    public FbDataReferencer() {
        mReferencerFactory = new FactoryListItemsReferencer(this);
    }

    public ProfileSocial newSocialProfile(@Nullable AuthorId id, Firebase root, FbSocialStructure social) {
        return id == null ? new NullSocialProfile() : new FbSocialProfile(id, root, social, this);
    }

    public RefAuthorList newAuthorList(Firebase ref) {
        ConverterAuthorId converter = new ConverterAuthorId.AsIndex();
        return new FbListReference.AuthorList(ref, new ListConverter<>(converter), converter);
    }

    public DataReference<AuthorId> newAuthorId(Firebase ref) {
        return new FbRefData<>(ref, new ConverterAuthorId());
    }

    public AuthorReference newNamedAuthor(Firebase ref, AuthorId id) {
        return new AuthorReferenceDefault(id, new FbRefData<>(ref, new ConverterIdNamedAuthor()));
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
        ConverterComments converter = new ConverterComments(id, new ConverterComment());
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
        final IdableListConverter<Value> converter = newListConverter(id, itemConverter);
        return new FbRefDatalist<>(id, ref, size, converter, newItemsReferencer(converter.getItemConverter()));
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

    public SentencesCollector newSentencesCollector(IdableList<RefComment> comments) {
        return new SentencesCollector(comments);
    }
}

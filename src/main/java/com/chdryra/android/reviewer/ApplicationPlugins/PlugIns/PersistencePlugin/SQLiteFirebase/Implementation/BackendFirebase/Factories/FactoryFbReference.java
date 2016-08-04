/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendDataConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterAuthorId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterNamedAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterReviewTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterSize;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbRefData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewRefItem;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewRefList;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NullReviewReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFbReference {
    private BackendDataConverter mDataConverter;
    private BackendReviewConverter mReviewConverter;
    private FactoryReviews mReviewsFactory;
    private ReviewsCache mCache;

    public FactoryFbReference(BackendDataConverter dataConverter,
                              BackendReviewConverter reviewConverter,
                              FactoryReviews reviewsFactory,
                              ReviewsCache cache) {
        mDataConverter = dataConverter;
        mReviewConverter = reviewConverter;
        mReviewsFactory = reviewsFactory;
        mCache = cache;
    }

    public TagsManager getTagsManager() {
        return mReviewConverter.getTagsManager();
    }

    public DataReference<AuthorId> newAuthorId(Firebase ref) {
        return new FbRefData<>(ref, new ConverterAuthorId());
    }

    public DataReference<NamedAuthor> newNamedAuthor(Firebase ref) {
        return new FbRefData<>(ref, new ConverterNamedAuthor());
    }

    public ReviewItemReference<DataSize> newSize(Firebase ref, ReviewId id) {
        return new FbReviewRefItem<>(id, ref, new ConverterSize(id));
    }

    public ReviewItemReference<DataImage> newImage(Firebase ref, ReviewId id) {
        return new FbReviewRefItem<>(id, ref, new ConverterImage(id));
    }

    public ReviewListReference<DataCriterion> newCriteria(Firebase ref, ReviewId id) {
        ConverterCriterion converter = new ConverterCriterion();
        return new FbReviewRefList<>(id, ref, new ConverterList<>(id, converter), converter);
    }

    public ReviewListReference<DataTag> newTags(Firebase ref, ReviewId id) {
        ConverterReviewTag converter = new ConverterReviewTag();
        return new FbReviewRefList<>(id, ref, new ConverterList<>(id, converter), converter);
    }

    public ReviewListReference<DataImage> newImages(Firebase ref, ReviewId id) {
        ConverterImage converter = new ConverterImage();
        return new FbReviewRefList<>(id, ref, new ConverterList<>(id, converter), converter);
    }

    public ReviewListReference<DataLocation> newLocations(Firebase ref, ReviewId id) {
        ConverterLocation converter = new ConverterLocation();
        return new FbReviewRefList<>(id, ref, new ConverterList<>(id, converter), converter);
    }

    public ReviewListReference<DataFact> newFacts(Firebase ref, ReviewId id) {
        ConverterFact converter = new ConverterFact();
        return new FbReviewRefList<>(id, ref, new ConverterList<>(id, converter), converter);
    }

    public ReviewListReference<DataComment> newComments(Firebase ref, ReviewId id) {
        ConverterComment converter = new ConverterComment();
        return new FbReviewRefList<>(id, ref, new ConverterList<>(id, converter), converter);
    }

    public ReviewReference newReview(ReviewListEntry entry, Firebase reviewDb, Firebase aggregatesDb) {
        return new FbReviewReference(mDataConverter.convert(entry), reviewDb, aggregatesDb,
                new ConverterReview(mReviewConverter), this, mCache, mReviewsFactory);
    }

    public ReviewReference newNullReview() {
        return new NullReviewReference();
    }
}

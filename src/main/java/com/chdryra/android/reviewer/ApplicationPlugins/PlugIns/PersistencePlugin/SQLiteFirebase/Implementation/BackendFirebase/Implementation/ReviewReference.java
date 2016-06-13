/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference {
    interface ValueObserver<T> {
        void onValue(ReviewId id, T value);
    }

    interface DereferenceCallback {
        void onDereferenced(ReviewDb review, @Nullable BackendError message);
    }

    ReviewListEntry getBasicInfo();

    void registerSubjectObserver(ValueObserver<String> observer);

    void registerRatingObserver(ValueObserver<Rating> observer);

    void registerAuthorObserver(ValueObserver<Author> observer);

    void registerPublishDateObserver(ValueObserver<Long> observer);

    void registerCoverObserver(ValueObserver<ImageData> observer);

    void registerCriteriaObserver(ValueObserver<List<Criterion>> observer);

    void registerCommentsObserver(ValueObserver<List<Comment>> observer);

    void registerFactsObserver(ValueObserver<List<Fact>> observer);

    void registerImagesObserver(ValueObserver<List<ImageData>> observer);

    void registerLocationsObserver(ValueObserver<List<Location>> observer);

    void registerTagsObserver(ValueObserver<List<String>> observer);

    void unregisterSubjectObserver(ValueObserver<String> observer);

    void unregisterRatingObserver(ValueObserver<Rating> observer);

    void unregisterAuthorObserver(ValueObserver<Author> observer);

    void unregisterPublishDateObserver(ValueObserver<Long> observer);

    void unregisterCriteriaObserver(ValueObserver<List<Criterion>> observer);

    void unregisterCommentsObserver(ValueObserver<List<Comment>> observer);

    void unregisterFactsObserver(ValueObserver<List<Fact>> observer);

    void unregisterImagesObserver(ValueObserver<List<ImageData>> observer);

    void unregisterLocationsObserver(ValueObserver<List<Location>> observer);

    void unregisterTagsObserver(ValueObserver<List<String>> observer);

    void dereference(DereferenceCallback callback);
}

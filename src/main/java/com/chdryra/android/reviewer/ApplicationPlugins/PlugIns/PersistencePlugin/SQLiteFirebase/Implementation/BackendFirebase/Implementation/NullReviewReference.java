/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Comment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Fact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ImageData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Location;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewReference implements ReviewReference {

    public NullReviewReference() {
    }

    @Override
    public ReviewListEntry getBasicInfo() {
        return new ReviewListEntry();
    }

    @Override
    public void registerSubjectObserver(ValueObserver<String> observer) {
    }

    @Override
    public void registerRatingObserver(ValueObserver<Rating> observer) {
    }

    @Override
    public void registerAuthorObserver(ValueObserver<Author> observer) {
    }

    @Override
    public void registerPublishDateObserver(ValueObserver<Long> observer) {
    }

    @Override
    public void registerCoverObserver(ValueObserver<ImageData> observer) {
    }

    @Override
    public void registerCriteriaObserver(ValueObserver<List<Criterion>> observer) {
    }

    @Override
    public void registerCommentsObserver(ValueObserver<List<Comment>> observer) {
    }

    @Override
    public void registerFactsObserver(ValueObserver<List<Fact>> observer) {
    }

    @Override
    public void registerImagesObserver(ValueObserver<List<ImageData>> observer) {
    }

    @Override
    public void registerLocationsObserver(ValueObserver<List<Location>> observer) {
    }

    @Override
    public void registerTagsObserver(ValueObserver<List<String>> observer) {
    }

    @Override
    public void unregisterSubjectObserver(ValueObserver<String> observer) {
    }

    @Override
    public void unregisterRatingObserver(ValueObserver<Rating> observer) {
    }

    @Override
    public void unregisterAuthorObserver(ValueObserver<Author> observer) {
    }

    @Override
    public void unregisterPublishDateObserver(ValueObserver<Long> observer) {
    }

    @Override
    public void unregisterCriteriaObserver(ValueObserver<List<Criterion>> observer) {
    }

    @Override
    public void unregisterCommentsObserver(ValueObserver<List<Comment>> observer) {
    }

    @Override
    public void unregisterFactsObserver(ValueObserver<List<Fact>> observer) {
    }

    @Override
    public void unregisterImagesObserver(ValueObserver<List<ImageData>> observer) {
    }

    @Override
    public void unregisterLocationsObserver(ValueObserver<List<Location>> observer) {
    }

    @Override
    public void unregisterTagsObserver(ValueObserver<List<String>> observer) {
    }

    @Override
    public void dereference(DereferenceCallback callback) {
    }

}

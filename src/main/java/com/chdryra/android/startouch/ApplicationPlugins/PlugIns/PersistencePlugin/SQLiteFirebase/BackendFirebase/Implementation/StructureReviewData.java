/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewAggregates;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.StructureReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbStructureBasic;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.Path;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureReviewData<T> extends DbStructureBasic<ReviewDb> implements StructureReview {
    private final DataGetter<T> mGetter;

    public interface DataGetter<T> {
        T getDataObject(ReviewDb review);
    }

    StructureReviewData(String path, DataGetter<T> getter) {
        setPathToStructure(path);
        mGetter = getter;
    }

    StructureReviewData(Path<ReviewDb> path, DataGetter<T> getter) {
        setPathToStructure(path);
        mGetter = getter;
    }

    @Override
    public String relativePathToReview(String reviewId) {
        return reviewId;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        String reviewId = review.getReviewId();

        Updates updates = new Updates(updateType);
        updates.atPath(review, reviewId).putObject(mGetter.getDataObject(review));

        return updates.toMap();
    }

    public static class Reviews extends StructureReviewData<ReviewDb> {
        public Reviews(String path) {
            super(path, new ReviewGetter());
        }

        public Reviews(Path<ReviewDb> path) {
            super(path, new ReviewGetter());
        }

        private static class ReviewGetter implements DataGetter<ReviewDb> {
            @Override
            public ReviewDb getDataObject(ReviewDb review) {
                return review;
            }
        }
    }

    public static class List extends StructureReviewData<ReviewListEntry> {
        public List(String path) {
            super(path, new ListGetter());
        }

        public List(Path<ReviewDb> path) {
            super(path, new ListGetter());
        }

        private static class ListGetter implements DataGetter<ReviewListEntry> {
            @Override
            public ReviewListEntry getDataObject(ReviewDb review) {
                return new ReviewListEntry(review).toInverseDate();
            }
        }
    }

    public static class Aggregates extends StructureReviewData<ReviewAggregates> {
        public Aggregates(String path) {
            super(path, new AggregateGetter());
        }

        public Aggregates(Path<ReviewDb> path) {
            super(path, new AggregateGetter());
        }

        private static class AggregateGetter implements DataGetter<ReviewAggregates> {
            @Override
            public ReviewAggregates getDataObject(ReviewDb review) {
                return new ReviewAggregates(review);
            }
        }
    }
}

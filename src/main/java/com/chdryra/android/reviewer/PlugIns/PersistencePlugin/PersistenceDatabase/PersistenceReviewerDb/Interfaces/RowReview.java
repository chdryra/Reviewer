/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Implementation.ColumnInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowReview extends ReviewDataRow, DataDateReview,
        DataSubject, DataRating {
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<String> PARENT_ID = new ColumnInfo<>("parent_id", DbEntryType.TEXT);
    ColumnInfo<String> USER_ID = new ColumnInfo<>("user_id", DbEntryType.TEXT);
    ColumnInfo<Long> PUBLISH_DATE = new ColumnInfo<>("publish_date", DbEntryType.LONG);
    ColumnInfo<String> SUBJECT = new ColumnInfo<>("subject", DbEntryType.TEXT);
    ColumnInfo<Float> RATING = new ColumnInfo<>("rating", DbEntryType.FLOAT);
    ColumnInfo<Integer> RATING_WEIGHT = new ColumnInfo<>("rating_weight", DbEntryType.INTEGER);
    ColumnInfo<Boolean> IS_AVERAGE = new ColumnInfo<>("rating_is_average", DbEntryType.BOOLEAN);

    String getParentId();

    String getAuthorId();

    long getPublishDate();

    boolean isRatingIsAverage();

    @Override
    long getTime();

    @Override
    float getRating();

    @Override
    int getRatingWeight();

    @Override
    String getSubject();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}

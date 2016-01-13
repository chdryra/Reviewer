package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowReview extends ReviewDataRow, DataDateReview,
        DataSubject, DataRating {
    String COLUMN_REVIEW_ID = "review_id";
    String COLUMN_PARENT_ID = "parent_id";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_PUBLISH_DATE = "publish_date";
    String COLUMN_SUBJECT = "subject";
    String COLUMN_RATING = "rating";
    String COLUMN_RATING_WEIGHT = "rating_weight";
    String COLUMN_RATING_IS_AVERAGE = "rating_is_average";

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

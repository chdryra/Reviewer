package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewLoader {
    @Nullable
    Review loadReview(RowReview reviewRow, ReviewerReadableDb database, TableTransactor db);
}

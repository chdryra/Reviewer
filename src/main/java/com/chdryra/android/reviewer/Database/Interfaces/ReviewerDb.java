package com.chdryra.android.reviewer.Database.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.Persistence.Api.TableTransactor;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerReadableDb{
    TagsManager getTagsManager();

    TableTransactor beginWriteTransaction();

    boolean addReviewToDb(Review review, TableTransactor db);

    boolean deleteReviewFromDb(String reviewId, TableTransactor db);

    @Override
    TableTransactor beginReadTransaction();

    @Override
    void endTransaction(TableTransactor db);

    @Override
    ArrayList<Review> loadReviewsFromDbWhere(TableTransactor db, String col, @Nullable String val);
}

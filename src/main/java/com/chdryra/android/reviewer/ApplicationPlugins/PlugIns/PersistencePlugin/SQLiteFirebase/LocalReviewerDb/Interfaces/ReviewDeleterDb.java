/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDeleterDb {
    boolean deleteReviewFromDb(RowReview row, ReviewerDb db, TableTransactor transactor);
}

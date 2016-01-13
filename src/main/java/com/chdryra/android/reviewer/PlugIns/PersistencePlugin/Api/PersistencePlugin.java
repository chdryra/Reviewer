package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistencePlugin {
    ReviewsRepositoryMutable newPersistenceRepository(Context context,
                                                      ModelContext modelContext);
}

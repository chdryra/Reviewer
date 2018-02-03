/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewDereferencer {
    public void getReview(ReviewId id, ReviewsRepository repo, final RepositoryCallback callback) {
        repo.getReference(id, dereferenceOnReturn(callback));
    }

    @NonNull
    private RepositoryCallback dereferenceOnReturn(final RepositoryCallback callback) {
        return new RepositoryCallback() {
            @Override
            public void onRepoCallback(RepositoryResult result) {
                if (result.isReference()) dereference(result.getReference(), callback);
            }
        };
    }

    private void dereference(ReviewReference reference, final RepositoryCallback callback) {
        reference.dereference(new DataReference.DereferenceCallback<Review>() {
            @Override
            public void onDereferenced(DataValue<Review> review) {
                RepositoryResult result
                        = new RepositoryResult(review.getData(), review.getMessage());
                callback.onRepoCallback(result);
            }
        });
    }
}

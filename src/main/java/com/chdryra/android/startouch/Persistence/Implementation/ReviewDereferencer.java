/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewDereferencer {
    public void getReview(ReviewId id, ReviewsRepoReadable repo, final RepoCallback callback) {
        repo.getReference(id, dereferenceOnReturn(callback));
    }

    @NonNull
    private RepoCallback dereferenceOnReturn(final RepoCallback callback) {
        return new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (result.isReference()) dereference(result.getReference(), callback);
            }
        };
    }

    private void dereference(ReviewReference reference, final RepoCallback callback) {
        reference.dereference(new DataReference.DereferenceCallback<Review>() {
            @Override
            public void onDereferenced(DataValue<Review> review) {
                RepoResult result
                        = new RepoResult(review.getData(), review.getMessage());
                callback.onRepoCallback(result);
            }
        });
    }
}

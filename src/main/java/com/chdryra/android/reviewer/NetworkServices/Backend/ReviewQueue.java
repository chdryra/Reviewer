/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.AsyncWorkQueueImpl;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkerToken;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewQueue extends AsyncWorkQueueImpl<Review> {
    private Map<String, WorkerToken> mMasterTokens;

    public ReviewQueue(ReviewStore store) {
        super(store);
        mMasterTokens = new HashMap<>();
    }

    @Override
    public void onAddedToStore(Review item, String storeId, CallbackMessage result) {
        if(!mMasterTokens.containsKey(storeId)) {
            mMasterTokens.put(storeId, addWorker(storeId, this));
        }

        super.onAddedToStore(item, storeId, result);
    }

}

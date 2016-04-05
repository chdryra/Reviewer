/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.WorkQueueModel;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WorkerToken {
    private String mId;
    private AsyncStoreCallback mInterestedParty;

    public WorkerToken(String id, AsyncStoreCallback interestedParty) {
        mId = id;
        mInterestedParty = interestedParty;
    }

    public String getId() {
        return mId;
    }
}

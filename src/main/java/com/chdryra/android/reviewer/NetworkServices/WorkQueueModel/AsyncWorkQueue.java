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
public interface AsyncWorkQueue<T> {
    interface QueueObserver {
        void onAddedToQueue(String itemId);
    }

    void addForWork(T item, String itemId, AsyncStoreCallback<T> callback);

    WorkerToken requestForWork(String itemId, AsyncStoreCallback<T> callback);

    WorkerToken requestForWork(AsyncStoreCallback<T> callback);

    void workComplete(WorkerToken token);
}

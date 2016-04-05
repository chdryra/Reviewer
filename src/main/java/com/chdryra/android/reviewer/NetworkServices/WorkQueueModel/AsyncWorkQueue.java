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

    void addItemToQueue(T item, String itemId, WorkStoreCallback<T> callback);

    WorkerToken getItemForWork(String itemId, WorkStoreCallback<T> callback, Object worker);

    WorkerToken addWorker(String itemId, Object worker);

    void workComplete(WorkerToken token);

    boolean hasWorkers(String itemId);

    void removeItemOnWorkCompleted(String itemId);

    void registerObserver(QueueObserver observer);

    void unregisterObserver(QueueObserver observer);
}

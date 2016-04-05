/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.WorkQueueModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AsyncWorkQueueImpl<T> implements AsyncWorkQueue<T>, WorkStoreCallback<T> {
    private WorkStore<T> mQueueStore;
    private WorkStoreCallback<T> mWorkStoreCallback;
    private ArrayList<String> mBacklogToDelete;
    private Map<String, List<WorkerToken>> mWorkers;
    private ArrayList<String> mToClear;
    private ArrayList<QueueObserver> mObservers;

    public AsyncWorkQueueImpl(WorkStore<T> queueStore) {
        mQueueStore = queueStore;
        mBacklogToDelete = new ArrayList<>();
        mWorkers = new HashMap<>();
        mToClear = new ArrayList<>();
        mObservers = new ArrayList<>();
    }

    @Override
    public void addItemToQueue(T item, String itemId, WorkStoreCallback<T> callback) {
        mWorkStoreCallback = callback;
        mQueueStore.addItemAsync(item, this);
    }

    @Override
    public WorkerToken addWorker(String itemId, Object worker) {
        return newWorker(itemId, worker);
    }

    @Override
    public WorkerToken getItemForWork(String id, WorkStoreCallback<T> callback, Object worker) {
        mWorkStoreCallback = callback;
        mQueueStore.getItemAsync(id, this);
        return newWorker(id, worker);
    }

    @Override
    public void onAddedToStore(T item, String storeId, CallbackMessage result) {
        mWorkStoreCallback.onAddedToStore(item, storeId, result);
        notifyObserversOnAdd(storeId);
    }

    @Override
    public void onRetrievedFromStore(T item, String requestedId, CallbackMessage result) {
        mWorkStoreCallback.onRetrievedFromStore(item, requestedId, result);
    }

    @Override
    public void onFailed(@Nullable T item, @Nullable String itemId, CallbackMessage result) {
        mWorkStoreCallback.onFailed(item, itemId, result);
    }

    @Override
    public void workComplete(WorkerToken token) {
        String id = token.getItemId();
        List<WorkerToken> interestedParties = mWorkers.get(id);
        interestedParties.remove(token);
        removeItemIfAllWorkCompleted(id);
    }

    @Override
    public void onRemovedFromStore(String itemId, CallbackMessage result) {
        if (!result.isError()) mBacklogToDelete.remove(itemId);
    }

    @Override
    public boolean hasWorkers(String itemId) {
        List<WorkerToken> workers = mWorkers.get(itemId);
        return workers != null && workers.size() > 0;
    }

    @Override
    public void removeItemOnWorkCompleted(String itemId) {
        mToClear.add(itemId);
        removeItemIfAllWorkCompleted(itemId);
    }

    @Override
    public void registerObserver(QueueObserver observer) {
        if(!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(QueueObserver observer) {
        if(mObservers.contains(observer)) mObservers.remove(observer);
    }

    private void notifyObserversOnAdd(String itemId) {
        for(QueueObserver observer : mObservers) {
            observer.onAddedToQueue(itemId);
        }
    }

    private void removeItemIfAllWorkCompleted(String itemId) {
        if(!hasWorkers(itemId) && mToClear.contains(itemId)) {
            mWorkers.remove(itemId);
            mToClear.remove(itemId);
            mBacklogToDelete.add(itemId);
            deleteBacklog();
        }
    }

    private WorkerToken newWorker(String id, Object worker) {
        if (!mWorkers.containsKey(id)) {
            mWorkers.put(id, new ArrayList<WorkerToken>());
        }
        WorkerToken token = new WorkerToken(id, worker);
        mWorkers.get(id).add(token);

        return token;
    }

    private void deleteBacklog() {
        for (String toDelete : mBacklogToDelete) {
            mQueueStore.removeItemAsync(toDelete, this);
        }
    }
}

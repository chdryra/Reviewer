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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AsyncWorkQueueImpl<T> implements AsyncWorkQueue<T>, AsyncStoreCallback<T> {
    private AsyncStore<T> mQueueStore;
    private AsyncStoreCallback<T> mAsyncStoreCallback;
    private LinkedList<String> mOrder;
    private ArrayList<String> mBacklogToDelete;
    private Map<String, List<WorkerToken>> mWorkers;

    public AsyncWorkQueueImpl(AsyncStore<T> queueStore) {
        mQueueStore = queueStore;
        mBacklogToDelete = new ArrayList<>();
        mOrder = new LinkedList<>();
        mWorkers = new HashMap<>();
    }

    @Override
    public void addForWork(T item, String itemId, AsyncStoreCallback<T> callback) {
        mAsyncStoreCallback = callback;
        mQueueStore.addItemAsync(item, this);
    }

    @Override
    public WorkerToken requestForWork(String id, AsyncStoreCallback<T> callback) {
        mAsyncStoreCallback = callback;
        mQueueStore.getItemAsync(id, this);
        return newWorker(id, callback);
    }

    @Override
    public WorkerToken requestForWork(AsyncStoreCallback<T> callback) {
        mAsyncStoreCallback = callback;
        String fetchingId = mOrder.peek();
        mQueueStore.getItemAsync(fetchingId, this);
        return newWorker(fetchingId, callback);
    }

    @Override
    public void onAddedToStore(T item, String storeId, CallbackMessage result) {
        mOrder.add(storeId);
        mAsyncStoreCallback.onAddedToStore(item, storeId, result);
    }

    @Override
    public void onRetrievedFromStore(T item, String requestedId, CallbackMessage result) {
        mOrder.remove(requestedId);
        mAsyncStoreCallback.onRetrievedFromStore(item, requestedId, result);
    }

    @Override
    public void onFailed(@Nullable T item, @Nullable String itemId, CallbackMessage result) {
        mAsyncStoreCallback.onFailed(item, itemId, result);
    }

    @Override
    public void workComplete(WorkerToken token) {
        String id = token.getId();
        List<WorkerToken> interestedParties = mWorkers.get(id);
        interestedParties.remove(token);
        if(interestedParties.size() == 0) {
            mBacklogToDelete.add(id);
            deleteBacklog();
        }
    }

    @Override
    public void onRemovedFromStore(String itemId, CallbackMessage result) {
        if (!result.isError()) mBacklogToDelete.remove(itemId);
    }

    private WorkerToken newWorker(String id, AsyncStoreCallback<T> callback) {
        if (!mWorkers.containsKey(id)) {
            mWorkers.put(id, new ArrayList<WorkerToken>());
        }
        WorkerToken token = new WorkerToken(id, callback);
        mWorkers.get(id).add(token);

        return token;
    }

    protected WorkerToken addWorker(String itemId, AsyncStoreCallback<T> callback) {
        return newWorker(itemId, callback);
    }

    private void deleteBacklog() {
        for (String  toDelete : mBacklogToDelete) {
            mOrder.remove(toDelete);
            mQueueStore.removeItemAsync(toDelete, this);
        }
    }
}

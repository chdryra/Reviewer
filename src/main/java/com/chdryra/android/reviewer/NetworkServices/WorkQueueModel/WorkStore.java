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
public interface WorkStore<T> {
    void addItemAsync(T item, WorkStoreCallback<T> callback);

    void getItemAsync(String itemId, WorkStoreCallback<T> callback);

    void removeItemAsync(String itemId, WorkStoreCallback<T> callback);
}

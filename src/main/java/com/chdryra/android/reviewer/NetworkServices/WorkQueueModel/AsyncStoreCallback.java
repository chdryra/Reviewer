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

/**
 * Created by: Rizwan Choudrey
 * On: 04/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AsyncStoreCallback<T> {
    void onAddedToStore(T item, String storeId, CallbackMessage result);

    void onRetrievedFromStore(@Nullable T item, String requestedId, CallbackMessage result);

    void onRemovedFromStore(String itemId, CallbackMessage result);

    void onFailed(@Nullable T item, @Nullable String itemId, CallbackMessage result);
}

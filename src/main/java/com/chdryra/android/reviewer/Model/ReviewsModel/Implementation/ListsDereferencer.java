/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ListsDereferencer<T extends HasReviewId> {
    private IdableList<ReviewListReference<T>> mRefs;
    private TreeDataReferenceBasic.GetDataCallback<T> mPost;
    private IdableList<T> mData;
    private int mNumDereferences = 0;

    public ListsDereferencer(IdableList<ReviewListReference<T>> refs,
                             TreeDataReferenceBasic.GetDataCallback<T> post) {
        mRefs = refs;
        mPost = post;
    }

    public void dereference() {
        mData = new IdableDataList<>(mRefs.getReviewId());
        for (ReviewListReference<T> ref : mRefs) {
            ref.dereference(new DataReference.DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                    add(data, message);
                }
            });
        }
    }

    private void add(@Nullable IdableList<T> data, CallbackMessage message) {
        if (data != null && !message.isError()) mData.addAll(data);
        mNumDereferences++;
        if (mNumDereferences == mRefs.size()) mPost.onData(mData);
    }
}

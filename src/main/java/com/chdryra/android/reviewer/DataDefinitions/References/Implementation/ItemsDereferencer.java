/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeDataReferenceBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemsDereferencer<T extends HasReviewId> {
    private IdableList<ReviewItemReference<T>> mRefs;
    private TreeDataReferenceBasic.GetDataCallback<T> mPost;
    private IdableList<T> mData;
    private int mNumDereferences = 0;

    public ItemsDereferencer(IdableList<ReviewItemReference<T>> refs,
                             TreeDataReferenceBasic.GetDataCallback<T> post) {
        mRefs = refs;
        mPost = post;
    }

    public void dereference() {
        mData = new IdableDataList<>(mRefs.getReviewId());
        for (ReviewItemReference<T> ref : mRefs) {
            ref.dereference(new DataReference.DereferenceCallback<T>() {
                @Override
                public void onDereferenced(@Nullable T data, CallbackMessage message) {
                    add(data, message);
                }
            });
        }
    }

    private void add(@Nullable T data, CallbackMessage message) {
        if (data != null && !message.isError()) addToDereferencedData(data);
        mNumDereferences++;
        if (mNumDereferences == mRefs.size()) mPost.onData(mData);
    }

    protected void addToDereferencedData(@Nullable T data) {
        mData.add(data);
    }
}

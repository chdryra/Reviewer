/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeDataReferenceBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemsDereferencer<T extends HasReviewId> {
    private final IdableList<ReviewItemReference<T>> mRefs;
    private final TreeDataReferenceBasic.GetDataCallback<T> mPost;
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
                public void onDereferenced(DataValue<T> value) {
                    add(value);
                }
            });
        }
    }

    private void add(DataValue<T> value) {
        if (value.hasValue()) addToDereferencedData(value.getData());
        mNumDereferences++;
        if (mNumDereferences == mRefs.size()) mPost.onData(mData);
    }

    private void addToDereferencedData(@Nullable T data) {
        mData.add(data);
    }
}

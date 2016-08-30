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
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeDataReferenceBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ListsDereferencer<Value extends HasReviewId,
        Reference extends ReviewItemReference<Value>,
        List extends ReviewListReference<Value, Reference>> {
    private IdableList<List> mListOfListReferences;
    private TreeDataReferenceBasic.GetDataCallback<Value> mOnDereferencing;
    private IdableList<Value> mValues;
    private int mNumDereferences = 0;

    public ListsDereferencer(IdableList<List> listOfListReferences,
                             TreeDataReferenceBasic.GetDataCallback<Value> onDereferencing) {
        mListOfListReferences = listOfListReferences;
        mOnDereferencing = onDereferencing;
    }

    public void dereference() {
        mValues = new IdableDataList<>(mListOfListReferences.getReviewId());
        for (List ref : mListOfListReferences) {
            ref.dereference(new DataReference.DereferenceCallback<IdableList<Value>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<Value> data, CallbackMessage message) {
                    add(data, message);
                }
            });
        }
    }

    private void add(@Nullable IdableList<Value> data, CallbackMessage message) {
        if (data != null && !message.isError()) mValues.addAll(data);
        mNumDereferences++;
        if (mNumDereferences == mListOfListReferences.size()) mOnDereferencing.onData(mValues);
    }
}

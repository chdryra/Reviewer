/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.TreeDataReferenceBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ListsDereferencer<Value extends HasReviewId,
        Reference extends ReviewItemReference<Value>,
        List extends ReviewListReference<Value, Reference>> {
    private final IdableList<List> mListOfListReferences;
    private final TreeDataReferenceBasic.GetDataCallback<Value> mOnDereferencing;
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
                public void onDereferenced(DataValue<IdableList<Value>> value) {
                    add(value);
                }
            });
        }
    }

    private void add(DataValue<IdableList<Value>> value) {
        if (value.hasValue()) mValues.addAll(value.getData());
        mNumDereferences++;
        if (mNumDereferences == mListOfListReferences.size()) mOnDereferencing.onData(mValues);
    }
}

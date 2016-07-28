/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Criterion;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterCriteria implements FbDataReference.SnapshotConverter<IdableList<DataCriterion>> {
    private ReviewId mId;
    private FbDataReference.SnapshotConverter<DataCriterion> mItemConverter;

    public ConverterCriteria(ReviewId id) {
        mId = id;
        mItemConverter = new ConverterCriterion(id);
    }

    @Override
    @Nullable
    public IdableList<DataCriterion> convert(DataSnapshot snapshot) {
        IdableList<DataCriterion> data = new IdableDataList<>(mId);
        for(DataSnapshot item : snapshot.getChildren()) {
            data.add(mItemConverter.convert(item));
        }

        return data;
    }

    private static class ConverterCriterion implements FbDataReference.SnapshotConverter<DataCriterion> {
        private ReviewId mId;

        private ConverterCriterion(ReviewId id) {
            mId = id;
        }

        @Override
        @Nullable
        public DataCriterion convert(DataSnapshot snapshot) {
            Criterion value = snapshot.getValue(Criterion.class);
            return value == null ? new DatumCriterion(mId) :
                    new DatumCriterion(mId, value.getSubject(), (float) value.getRating());
        }
    }
}

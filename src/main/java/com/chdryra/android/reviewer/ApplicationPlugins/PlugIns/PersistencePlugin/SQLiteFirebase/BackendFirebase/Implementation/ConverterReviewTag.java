/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterReviewTag implements ReviewItemConverter<DataTag> {
    @Override
    public DataTag convert(ReviewId id, DataSnapshot snapshot) {
        String value = snapshot.getValue(String.class);
        return value == null ? null : new DatumTag(id, value);
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Comment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.ReviewItemConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterComment implements ReviewItemConverter<DataComment> {
    @Override
    public DataComment convert(ReviewId id, DataSnapshot snapshot) {
        Comment value = snapshot.getValue(Comment.class);
        return value == null ? null :
                new DatumComment(id, value.toComment(), value.isHeadline());
    }

    public static class ConverterSentence {
        @Nullable
        public DataComment convert(ReviewId id, boolean isHeadline, DataSnapshot snapshot) {
            String value = snapshot.getValue(String.class);
            return value == null ? null :
                    new DatumComment(id, value, isHeadline);
        }
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterComments extends IdableListConverter<DataComment> implements
        SnapshotConverter<IdableList<DataComment>> {
    protected ReviewId mId;

    public ConverterComments(ReviewId id, ConverterComment commentConverter) {
        super(id, commentConverter);
    }

    public static class SentenceConverter implements SnapshotConverter<DataComment> {
        private final ReviewId mId;
        private final ConverterComment.ConverterSentence mConverter;
        private final boolean mIsHeadline;

        public SentenceConverter(ReviewId id, ConverterComment.ConverterSentence converter,
                                 boolean isHeadline) {
            mId = id;
            mConverter = converter;
            mIsHeadline = isHeadline;
        }

        @Nullable
        @Override
        public DataComment convert(DataSnapshot snapshot) {
            return mConverter.convert(mId, mIsHeadline, snapshot);
        }
    }
}

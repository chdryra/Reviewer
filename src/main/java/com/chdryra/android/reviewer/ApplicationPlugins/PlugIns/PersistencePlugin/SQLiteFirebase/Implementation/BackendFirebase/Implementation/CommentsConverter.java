/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 29/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentsConverter extends ListConverter<DataComment> implements SnapshotConverter<IdableList<DataComment>> {
    protected ReviewId mId;

    public CommentsConverter(ReviewId id, ConverterComment commentConverter) {
        super(id, commentConverter);
    }

    public static class SentenceConverter implements SnapshotConverter<DataComment> {
        private ReviewId mId;
        private ConverterComment.ConverterSentence mConverter;
        private boolean mIsHeadline;

        public SentenceConverter(ReviewId id, ConverterComment.ConverterSentence converter, boolean isHeadline) {
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

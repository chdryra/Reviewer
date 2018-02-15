/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.SimpleFirstSentenceRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SentencesCollector;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleCommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.WrapperCommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.StaticItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.WrapperCommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.WrapperDataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReferences {
    public SentencesCollector newSentencesCollector(IdableList<CommentRef> comments) {
        return new SentencesCollector(comments);
    }

    public <T extends HasReviewId> ReviewItemReference<T> newWrapper(T datum) {
        return new StaticItemReference<>(datum);
    }

    public <T extends HasReviewId> DataListRef<T> newWrapper(IdableList<T> data) {
        return new WrapperDataListRef<>(data, this);
    }

    public CommentListRef newCommentsWrapper(IdableList<? extends DataComment> data) {
        IdableList<DataComment> list = new IdableDataList<>(data.getReviewId());
        list.addAll(data);
        return new WrapperCommentListRef(list, this);
    }

    public <T extends HasReviewId> DataListRef<T> newSuperClassWrapper(IdableList<? extends T> data) {
        IdableList<T> list = new IdableDataList<>(data.getReviewId());
        list.addAll(data);
        return new WrapperDataListRef<>(list, this);
    }

    public CommentRef newWrapper(DataComment comment, @Nullable CommentRef parent) {
        return new WrapperCommentRef(comment, parent);
    }

    public CommentRef newCommentReference(boolean isHeadline, SimpleItemReference.Dereferencer<DataComment> dereferencer) {
        return new SimpleCommentRef(isHeadline, this, dereferencer);
    }

    public CommentRef newFirstSentenceReference(CommentRef parent) {
        return new SimpleFirstSentenceRef(parent, this);
    }
}

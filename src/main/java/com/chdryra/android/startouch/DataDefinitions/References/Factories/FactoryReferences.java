/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.SimpleRefFirstSentence;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SentencesCollector;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleRefComment;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.WrapperRefComment;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.StaticItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.WrapperRefCommentList;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.WrapperRefDataList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReferences {
    public SentencesCollector newSentencesCollector(IdableList<RefComment> comments) {
        return new SentencesCollector(comments);
    }

    public <T extends HasReviewId> ReviewItemReference<T> newWrapper(T datum) {
        return new StaticItemReference<>(datum);
    }

    public <T extends HasReviewId> RefDataList<T> newWrapper(IdableList<T> data) {
        return new WrapperRefDataList<>(data, this);
    }

    public RefCommentList newCommentsWrapper(IdableList<? extends DataComment> data) {
        IdableList<DataComment> list = new IdableDataList<>(data.getReviewId());
        list.addAll(data);
        return new WrapperRefCommentList(list, this);
    }

    public <T extends HasReviewId> RefDataList<T> newSuperClassWrapper(IdableList<? extends T> data) {
        IdableList<T> list = new IdableDataList<>(data.getReviewId());
        list.addAll(data);
        return new WrapperRefDataList<>(list, this);
    }

    public RefComment newWrapper(DataComment comment, @Nullable RefComment parent) {
        return new WrapperRefComment(comment, parent);
    }

    public RefComment newCommentReference(boolean isHeadline, SimpleItemReference.Dereferencer<DataComment> dereferencer) {
        return new SimpleRefComment(isHeadline, this, dereferencer);
    }

    public RefComment newFirstSentenceReference(RefComment parent) {
        return new SimpleRefFirstSentence(parent, this);
    }
}
/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 29/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class VisitorItemCounter<T> implements VisitorReviewNode {
    private ReviewId mId;
    protected Set<T> mData;

    protected abstract T getDataIfLeaf(@NonNull ReviewNode node);

    public VisitorItemCounter() {
        mData = new HashSet<>();
    }

    public DataSize getCount() {
        return new DatumSize(mId, mData.size());
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        if(mId == null) mId = node.getReviewId();
        if(node.isLeaf()) mData.add(getDataIfLeaf(node));
    }

    public static class SubjectsCounter extends VisitorItemCounter<String> {
        @Override
        protected String getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getSubject().getSubject();
        }
    }

    public static class AuthorsCounter extends VisitorItemCounter<AuthorId> {
        @Override
        protected AuthorId getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getAuthorId();
        }
    }

    public static class ReviewsCounter extends VisitorItemCounter<ReviewId> {
        @Override
        protected ReviewId getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getReviewId();
        }
    }

    public static class DatesCounter extends VisitorItemCounter<String> {
        @Override
        protected String getDataIfLeaf(@NonNull ReviewNode node) {
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
            return date.format(new Date(node.getPublishDate().getTime()));
        }
    }
}

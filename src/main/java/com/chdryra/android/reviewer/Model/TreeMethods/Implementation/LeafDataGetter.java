/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class LeafDataGetter<T extends HasReviewId> implements NodeDataGetter<T> {
    public abstract T getDataIfLeaf(ReviewNode node);

    @Override
    public T getData(@NonNull ReviewNode node) {
        return node.isLeaf() ? getDataIfLeaf(node) : null;
    }

    public static class LeafGetter extends LeafDataGetter<ReviewReference> {
        @Override
        public ReviewReference getDataIfLeaf(@NonNull ReviewNode node) {
            return node;
        }
    }

    public static class AuthorGetter extends LeafDataGetter<DataAuthorReview> {
        @Override
        public DataAuthorReview getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getAuthor();
        }
    }

    public static class SubjectGetter extends LeafDataGetter<DataSubject> {
        @Override
        public DataSubject getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getSubject();
        }
    }

    public static class DateGetter extends LeafDataGetter<DataDateReview> {
        @Override
        public DataDateReview getDataIfLeaf(@NonNull ReviewNode node) {
            return node.getPublishDate();
        }
    }
}

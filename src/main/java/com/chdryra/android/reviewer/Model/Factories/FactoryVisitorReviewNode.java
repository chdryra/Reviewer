/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.ConditionalDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorItemCounter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    private static final ConditionalDataGetter.Condition IS_LEAF
            = new ConditionalDataGetter.Condition() {
        @Override
        public boolean passesOnNode(ReviewNode node) {
            return node.isLeaf();
        }
    };

    private <T extends HasReviewId> VisitorDataGetter<T> newItemCollector(NodeDataGetter<T> getter) {
        return new VisitorDataGetter<>(new ConditionalDataGetter<>(IS_LEAF, getter));
    }

    private <T extends HasReviewId> VisitorDataGetter<ReviewListReference<T>>
    newListCollector(NodeDataGetter<ReviewListReference<T>> getter) {
        return new VisitorDataGetter<>(new ConditionalDataGetter<>(IS_LEAF, getter));
    }

    public VisitorDataGetter<DataSubject> newSubjectsCollector() {
        return newItemCollector(new NodeDataGetter<DataSubject>() {
            @Nullable
            @Override
            public DataSubject getData(@NonNull ReviewNode node) {
                return node.getSubject();
            }
        });
    }

    public VisitorDataGetter<DataAuthorId> newAuthorsCollector() {
        return newItemCollector(new NodeDataGetter<DataAuthorId>() {
            @Nullable
            @Override
            public DataAuthorId getData(@NonNull ReviewNode node) {
                return node.getAuthorId();
            }
        });
    }

    public VisitorDataGetter<DataDate> newDatesCollector() {
        return newItemCollector(new NodeDataGetter<DataDate>() {
            @Nullable
            @Override
            public DataDate getData(@NonNull ReviewNode node) {
                return node.getPublishDate();
            }
        });
    }

    public VisitorDataGetter<ReviewListReference<ReviewReference>> newReviewsCollector() {
        return newListCollector(new NodeDataGetter<ReviewListReference<ReviewReference>>() {
            @Nullable
            @Override
            public ReviewListReference<ReviewReference> getData(@NonNull ReviewNode node) {
                return node.getReviews();
            }
        });
    }

    public VisitorItemCounter<String> newSubjectsCounter() {
        return new VisitorItemCounter.SubjectsCounter();
    }

    public VisitorItemCounter<AuthorId> newAuthorsCounter() {
        return new VisitorItemCounter.AuthorsCounter();
    }

    public VisitorItemCounter<String> newDatesCounter() {
        return new VisitorItemCounter.DatesCounter();
    }

    public VisitorItemCounter<ReviewId> newReviewsCounter() {
        return new VisitorItemCounter.ReviewsCounter();
    }

}

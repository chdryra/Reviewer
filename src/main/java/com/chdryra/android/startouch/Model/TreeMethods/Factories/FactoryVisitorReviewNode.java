/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Algorithms.DataSorting.RatingsDistribution;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.ConditionalDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.ConditionalValueGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataBucketer;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeValueGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    private static final ConditionalDataGetter.Condition IS_LEAF
            = new ConditionalDataGetter.Condition() {
        @Override
        public boolean isTrue(ReviewNode node) {
            return node.isLeaf();
        }
    };

    public VisitorDataGetter<DataSubject> newSubjectsCollector() {
        return newItemCollector(new NodeDataGetter<DataSubject>() {
            @Nullable
            @Override
            public DataSubject getData(@NonNull ReviewNode node) {
                return node.getSubject();
            }
        });
    }

    public VisitorDataBucketer<Float, DataRating> newRatingValueBucketer() {
        return new VisitorDataBucketer<>(new RatingsDistribution(),
                newLeafValueGetter(new NodeValueGetter<Float>() {
                    @Nullable
                    @Override
                    public Float getData(@NonNull ReviewNode node) {
                        return node.getRating().getRating();
                    }
                }), newLeafDataGetter(new NodeDataGetter<DataRating>() {
            @Nullable
            @Override
            public DataRating getData(@NonNull ReviewNode node) {
                return node.getRating();
            }
        }));
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

    public VisitorDataGetter<ReviewReference> newReviewsCollector() {
        return newItemCollector(new NodeDataGetter<ReviewReference>() {
            @Nullable
            @Override
            public ReviewReference getData(@NonNull ReviewNode node) {
                return node.getReference();
            }
        });
    }

    public VisitorDataGetter<DataListRef<DataCriterion>> newCriteriaCollector() {
        return newListCollector(new NodeDataGetter<DataListRef<DataCriterion>>() {
            @Nullable
            @Override
            public DataListRef<DataCriterion> getData(@NonNull ReviewNode node) {
                return node.getCriteria();
            }
        });
    }

    public VisitorDataGetter<CommentListRef> newCommentsCollector() {
        return newListCollector(new NodeDataGetter<CommentListRef>() {
            @Nullable
            @Override
            public CommentListRef getData(@NonNull ReviewNode node) {
                return node.getComments();
            }
        });
    }

    public VisitorDataGetter<DataListRef<DataImage>> newImagesCollector() {
        return newListCollector(new NodeDataGetter<DataListRef<DataImage>>() {
            @Nullable
            @Override
            public DataListRef<DataImage> getData(@NonNull ReviewNode node) {
                return node.getImages();
            }
        });
    }

    public VisitorDataGetter<DataListRef<DataLocation>> newLocationsCollector() {
        return newListCollector(new NodeDataGetter<DataListRef<DataLocation>>() {
            @Nullable
            @Override
            public DataListRef<DataLocation> getData(@NonNull ReviewNode node) {
                return node.getLocations();
            }
        });
    }

    public VisitorDataGetter<DataListRef<DataFact>> newFactsCollector() {
        return newListCollector(new NodeDataGetter<DataListRef<DataFact>>() {
            @Nullable
            @Override
            public DataListRef<DataFact> getData(@NonNull ReviewNode node) {
                return node.getFacts();
            }
        });
    }

    public VisitorDataGetter<DataListRef<DataTag>> newTagsCollector() {
        return newListCollector(new NodeDataGetter<DataListRef<DataTag>>() {
            @Nullable
            @Override
            public DataListRef<DataTag> getData(@NonNull ReviewNode node) {
                return node.getTags();
            }
        });
    }

    private <Value extends HasReviewId> VisitorDataGetter<Value> newItemCollector
            (NodeDataGetter<Value> getter) {
        return new VisitorDataGetter<>(newLeafDataGetter(getter));
    }

    @NonNull
    private <Value extends HasReviewId> ConditionalDataGetter<Value> newLeafDataGetter
            (NodeDataGetter<Value> getter) {
        return new ConditionalDataGetter<>(IS_LEAF, getter);
    }

    @NonNull
    private <Value> ConditionalValueGetter<Value> newLeafValueGetter(NodeValueGetter<Value>
                                                                             getter) {
        return new ConditionalValueGetter<>(IS_LEAF, getter);
    }

    private <List extends ReviewListReference<?, ?>> VisitorDataGetter<List>
    newListCollector(NodeDataGetter<List> getter) {
        return new VisitorDataGetter<>(newLeafDataGetter(getter));
    }
}

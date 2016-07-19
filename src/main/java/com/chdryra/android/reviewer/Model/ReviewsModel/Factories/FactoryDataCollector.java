/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.TreeDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 24/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataCollector {
    public NodeDataCollector<DataTag> newCollector(IdableList<ReviewNode> nodes, ReviewReference.TagsCallback callback) {
        return new TreeDataCollector.Tags(nodes, callback);
    }

    public NodeDataCollector<DataCriterion> newCollector(IdableList<ReviewNode> nodes, ReviewReference.CriteriaCallback callback) {
        return new TreeDataCollector.Criteria(nodes, callback);
    }

    public NodeDataCollector<DataImage> newCollector(IdableList<ReviewNode> nodes, ReviewReference.ImagesCallback callback) {
        return new TreeDataCollector.Images(nodes, callback);
    }

    public NodeDataCollector<DataComment> newCollector(IdableList<ReviewNode> nodes, ReviewReference.CommentsCallback callback) {
        return new TreeDataCollector.Comments(nodes, callback);
    }

    public NodeDataCollector<DataLocation> newCollector(IdableList<ReviewNode> nodes, ReviewReference.LocationsCallback callback) {
        return new TreeDataCollector.Locations(nodes, callback);
    }

    public NodeDataCollector<DataFact> newCollector(IdableList<ReviewNode> nodes, ReviewReference.FactsCallback callback) {
        return new TreeDataCollector.Facts(nodes, callback);
    }

    public NodeDataCollector<DataImage> newCollector(IdableList<ReviewNode> nodes, TreeDataCollector.CoversCallback callback) {
        return new TreeDataCollector.Covers(nodes, callback);
    }

    public NodeDataCollector.Size newCollector(IdableList<ReviewNode> nodes, ReviewReference.TagsSizeCallback callback) {
        return new TreeDataCollector.TagsSize(nodes, callback);
    }

    public NodeDataCollector.Size newCollector(IdableList<ReviewNode> nodes, ReviewReference.CriteriaSizeCallback callback) {
        return new TreeDataCollector.CriteriaSize(nodes, callback);
    }

    public NodeDataCollector.Size newCollector(IdableList<ReviewNode> nodes, ReviewReference.ImagesSizeCallback callback) {
        return new TreeDataCollector.ImagesSize(nodes, callback);
    }

    public NodeDataCollector.Size newCollector(IdableList<ReviewNode> nodes, ReviewReference.CommentsSizeCallback callback) {
        return new TreeDataCollector.CommentsSize(nodes, callback);
    }

    public NodeDataCollector.Size newCollector(IdableList<ReviewNode> nodes, ReviewReference.LocationsSizeCallback callback) {
        return new TreeDataCollector.LocationsSize(nodes, callback);
    }

    public NodeDataCollector.Size newCollector(IdableList<ReviewNode> nodes, ReviewReference.FactsSizeCallback callback) {
        return new TreeDataCollector.FactsSize(nodes, callback);
    }
}

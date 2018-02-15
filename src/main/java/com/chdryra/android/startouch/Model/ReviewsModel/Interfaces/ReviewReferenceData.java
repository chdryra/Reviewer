/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Interfaces;


import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReferenceData extends DataReviewInfo {
    //Can't do ReviewItemReference<? extends DataImage> etc. as I want to dereference to known class
    ReviewItemReference<DataImage> getCover();

    DataListRef<DataCriterion> getCriteria();

    CommentListRef getComments();

    DataListRef<DataFact> getFacts();

    DataListRef<DataImage> getImages();

    DataListRef<DataLocation> getLocations();

    DataListRef<DataTag> getTags();
}

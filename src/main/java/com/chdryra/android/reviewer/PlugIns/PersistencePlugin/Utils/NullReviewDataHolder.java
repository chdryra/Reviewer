/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewDataHolder implements ReviewDataHolder{
    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId();
    }

    @Override
    public DataAuthor getAuthor() {
        return new DatumAuthor();
    }

    @Override
    public DataDate getPublishDate() {
        return new DatumDateReview();
    }

    @Override
    public String getSubject() {
        return "";
    }

    @Override
    public float getRating() {
        return 0f;
    }

    @Override
    public int getRatingWeight() {
        return 0;
    }

    @Override
    public Iterable<? extends DataComment> getComments() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<? extends DataImage> getImages() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<? extends DataFact> getFacts() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<? extends DataLocation> getLocations() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<? extends DataCriterion> getCriteria() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAverage() {
        return false;
    }

    @Override
    public boolean isValid(DataValidator validator) {
        return false;
    }
}

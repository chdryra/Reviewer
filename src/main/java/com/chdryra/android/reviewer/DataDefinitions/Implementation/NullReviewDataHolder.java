/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewDataHolder implements ReviewDataHolder {
    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId();
    }

    @Override
    public AuthorId getAuthorId() {
        return new AuthorIdParcelable();
    }

    @Override
    public DateTime getPublishDate() {
        return new DatumDate();
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
    public boolean isValid(DataValidator validator) {
        return false;
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataEquivalence {
    public static void checkEquivalence(DataComment lhs, DataComment rhs) {
        checkEquivalence(lhs, rhs, lhs.getReviewId());
    }

    public static void checkEquivalence(DataComment lhs, DataComment rhs, ReviewId rhsIdExpected) {
        assertThat(rhs.getReviewId().toString(), is(rhsIdExpected.toString()));
        assertThat(rhs.getComment(), is(lhs.getComment()));
        assertThat(rhs.isHeadline(), is(lhs.isHeadline()));
    }

    public static void checkEquivalence(DataCriterionReview lhs, DataCriterionReview rhs) {
        checkEquivalence(lhs, rhs, lhs.getReviewId());
    }

    public static void checkEquivalence(DataCriterionReview lhs, DataCriterionReview rhs, ReviewId rhsIdExpected) {
        assertThat(rhs.getReviewId().toString(), is(rhsIdExpected.toString()));
        assertThat(rhs.getSubject(), is(lhs.getSubject()));
        assertThat(rhs.getRating(), is(lhs.getRating()));
        assertThat(rhs.getReview().getSubject(), is(lhs.getReview().getSubject()));
        assertThat(rhs.getReview().getRating(), is(lhs.getReview().getRating()));
    }

    public static void checkEquivalence(ReviewId parentId, Review review, DataCriterionReview rhs) {
        assertThat(rhs.getReviewId().toString(), is(parentId.toString()));
        assertThat(rhs.getSubject(), is(review.getSubject().getSubject()));
        assertThat(rhs.getRating(), is(review.getRating().getRating()));
        assertThat(rhs.getReview(), is(review));
    }
    
    public static void checkEquivalence(DataFact lhs, DataFact rhs) {
        checkEquivalence(lhs, rhs, lhs.getReviewId());
    }

    public static void checkEquivalence(DataFact lhs, DataFact rhs, ReviewId rhsIdExpected) {
        assertThat(rhs.getReviewId().toString(), is(rhsIdExpected.toString()));
        assertThat(rhs.getLabel(), is(lhs.getLabel()));
        assertThat(rhs.getValue(), is(lhs.getValue()));
        assertThat(rhs.isUrl(), is(lhs.isUrl()));
    }

    public static void checkEquivalence(DataImage lhs, DataImage rhs) {
        checkEquivalence(lhs, rhs, lhs.getReviewId());
    }

    public static void checkEquivalence(DataImage lhs, DataImage rhs, ReviewId rhsIdExpected) {
        assertThat(rhs.getReviewId().toString(), is(rhsIdExpected.toString()));
        assertThat(rhs.getBitmap(), is(lhs.getBitmap()));
        assertThat(rhs.getDate().getTime(), is(lhs.getDate().getTime()));
        assertThat(rhs.getCaption(), is(lhs.getCaption()));
        assertThat(rhs.isCover(), is(lhs.isCover()));
    }

    public static void checkEquivalence(DataLocation lhs, DataLocation rhs) {
        checkEquivalence(lhs, rhs, lhs.getReviewId());
    }

    public static void checkEquivalence(DataLocation lhs, DataLocation rhs, ReviewId rhsIdExpected) {
        assertThat(rhs.getReviewId().toString(), is(rhsIdExpected.toString()));
        assertThat(rhs.getLatLng(), is(lhs.getLatLng()));
        assertThat(rhs.getName(), is(lhs.getName()));
    }

    public static void checkEquivalence(DataUrl lhs, MdUrl rhs) {
        checkEquivalence(lhs, rhs, lhs.getReviewId());
    }

    public static void checkEquivalence(DataUrl lhs, MdUrl rhs, ReviewId rhsIdExpected) {
        //Don't test urls are equal as extremely slow. Value check good enough.
        assertThat(rhs.getReviewId().toString(), is(rhsIdExpected.toString()));
        assertThat(rhs.getLabel(), is(lhs.getLabel()));
        assertThat(rhs.getValue(), is(lhs.getValue()));
        assertThat(rhs.isUrl(), is(lhs.isUrl()));
    }

}

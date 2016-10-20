/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class StringParser {
    public static String parse(NamedAuthor author) {
        return author.getName();
    }

    public static String parse(AuthorId authorId) {
        return authorId.toString();
    }

    public static String parse(DataComment comment) {
        return comment.getComment();
    }

    public static String parse(DataCriterion criterion) {
        String subject = criterion.getSubject();
        float rating = criterion.getRating();
        ReviewId id = criterion.getReviewId();
        return getSubjectRating(new DatumSubject(id, subject), new DatumRating(id, rating, 1));
    }

    public static String parse(DateTime date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(new Date(date.getTime()));
    }

    public static String parse(DataFact fact) {
        return fact.getLabel() + ": " + fact.getValue();
    }

    public static String parse(DataImage image) {
        String caption = image.getCaption();
        String datumName = GvImage.TYPE.getDatumName();
        return caption != null ? datumName + ": " + caption : datumName;
    }

    public static String parse(DataLocation location) {
        return "@" + location.getShortenedName();
    }

    public static String parse(DataTag tag) {
        return "#" + tag.getTag();
    }

    public static String parse(DataReviewInfo info) {
        return getSubjectRating(info.getSubject(), info.getRating())
                + " (" + parse((info.getPublishDate())) + ")";
    }

    public static String parse(DataSubject subject) {
        return subject.getSubject();
    }

    public static String parse(DataRating rating) {
        return RatingFormatter.outOfFive(rating.getRating());
    }

    public static String parse(DataSize size) {
        return String.valueOf(size.getSize());
    }

    public static String parse(DataSocialPlatform platform) {
        return platform.getName();
    }

    @NonNull
    private static String getSubjectRating(DataSubject subject, DataRating rating) {
        return parse(subject) + ": " + parse(rating);
    }
}

/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;



import com.chdryra.android.mygenerallibrary.Comparators.DataGetter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSocialPlatform;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class DataGetters {
    public static class NamedAuthorName implements DataGetter<NamedAuthor, String> {
        @Override
        public String getData(NamedAuthor item) {
            return item.getName();
        }
    }

    public static class SubjectString implements DataGetter<DataSubject, String> {
        @Override
        public String getData(DataSubject item) {
            return item.getSubject();
        }
    }

    public static class RatingFloat implements DataGetter<DataRating, Float> {
        @Override
        public Float getData(DataRating item) {
            return item.getRating();
        }
    }

    public static class CriterionSubject implements DataGetter<DataCriterion, String> {
        @Override
        public String getData(DataCriterion item) {
            return item.getSubject();
        }
    }

    public static class CriterionRating implements DataGetter<DataCriterion, Float> {
        @Override
        public Float getData(DataCriterion item) {
            return item.getRating();
        }
    }

    public static class TagString implements DataGetter<DataTag, String> {
        @Override
        public String getData(DataTag item) {
            return item.getTag();
        }
    }

    public static class CommentIsHeadline implements DataGetter<DataComment, Boolean> {
        @Override
        public Boolean getData(DataComment item) {
            return item.isHeadline();
        }
    }

    public static class CommentString implements DataGetter<DataComment, String> {
        @Override
        public String getData(DataComment item) {
            return item.getComment();
        }
    }

    public static class FactLabel<T extends DataFact> implements DataGetter<T, String> {
        @Override
        public String getData(T item) {
            return item.getLabel();
        }
    }

    public static class FactValue<T extends DataFact> implements DataGetter<T, String> {
        @Override
        public String getData(T item) {
            return item.getValue();
        }
    }

    public static class ImageIsCover implements DataGetter<DataImage, Boolean> {
        @Override
        public Boolean getData(DataImage item) {
            return item.isCover();
        }
    }

    public static class ImageDate implements DataGetter<DataImage, DateTime> {
        @Override
        public DateTime getData(DataImage item) {
            return item.getDate();
        }
    }

    public static class LocationName implements DataGetter<DataLocation, String> {
        @Override
        public String getData(DataLocation item) {
            return item.getShortenedName();
        }
    }

    public static class PlatformName implements DataGetter<DataSocialPlatform, String> {
        @Override
        public String getData(DataSocialPlatform item) {
            return item.getName();
        }
    }

    public static class ReviewSubject implements DataGetter<DataReviewInfo, DataSubject> {
        @Override
        public DataSubject getData(DataReviewInfo item) {
            return item.getSubject();
        }
    }

    public static class ReviewRating implements DataGetter<DataReviewInfo, DataRating> {
        @Override
        public DataRating getData(DataReviewInfo item) {
            return item.getRating();
        }
    }

    public static class ReviewDate implements DataGetter<DataReviewInfo, DataDate> {
        @Override
        public DataDate getData(DataReviewInfo item) {
            return item.getPublishDate();
        }
    }
}

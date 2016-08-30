/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DateMostRecentFirst;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.ReviewMostRecentPublished;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.PublishDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMostRecentPublishedTest extends ComparatorTest<DataReviewSummary> {
    public ReviewMostRecentPublishedTest() {
        super(new ReviewMostRecentPublished(new DateMostRecentFirst()));
    }

    @Test
    public void descendingPublishDate() {
        Date date1 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();
        Date date2 = new GregorianCalendar(2015, 2, 25, 19, 20).getTime();
        Date date3 = new GregorianCalendar(2015, 2, 25, 18, 30).getTime();
        Date date4 = new GregorianCalendar(2015, 2, 24, 19, 30).getTime();

        DataReviewSummary review1 = getReview(date1);
        DataReviewSummary review2 = getReview(date2);
        DataReviewSummary review3 = getReview(date3);
        DataReviewSummary review4 = getReview(date4);

        ComparatorTester<DataReviewSummary> tester = newComparatorTester();
        tester.testFirstSecond(review1, review2);
        tester.testFirstSecond(review2, review3);
        tester.testFirstSecond(review3, review4);
    }

    @Test
    public void comparatorEqualityOnSameDate() {
        Date date1 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();

        DataReviewSummary review1 = getReview(date1);
        DataReviewSummary review2 = getReview(date1);

        ComparatorTester<DataReviewSummary> tester = newComparatorTester();
        tester.testEquals(review1, review1);
        tester.testEquals(review1, review2);
    }

    private DataReviewSummary getReview(Date publishDate) {
        return new Review(new PublishDate(publishDate.getTime()));
    }
    
    private class Review implements DataReviewSummary {
        private DateTime mDate;
        private NamedAuthor mAuthor;
        private String mHeadline;
        private ArrayList<String> mTags;
        private String mLocation;
        private float mRating;
        private String mSubject;
        private ReviewId mId;
        private ReviewId mParent;

        public Review(DateTime date) {
            mDate = date;
            mAuthor = RandomAuthor.nextAuthor();
            mHeadline = RandomString.nextSentence();
            mTags = new ArrayList<>();
            for(int i = 0; i < 3; ++i) {
                mTags.add(RandomString.nextWord());
            }
            mLocation = RandomString.nextWord();
            mRating = RandomRating.nextRating();
            mSubject = RandomString.nextWord();
            mId = RandomReviewId.nextReviewId();
            mParent = RandomReviewId.nextReviewId();
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public DateTime getPublishDate() {
            return mDate;
        }

        @Override
        public NamedAuthor getAuthorId() {
            return mAuthor;
        }

        @Override
        public String getHeadline() {
            return mHeadline;
        }

        @Override
        public ArrayList<String> getTags() {
            return mTags;
        }

        @Override
        public String getLocationString() {
            return mLocation;
        }

        @Override
        public Bitmap getCoverImage() {
            return null;
        }

        @Override
        public float getRating() {
            return mRating;
        }

        @Override
        public String getSubject() {
            return mSubject;
        }

        @Override
        public ReviewId getParentId() {
            return mParent;
        }
    }
}

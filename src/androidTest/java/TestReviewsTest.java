/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(AndroidJUnit4.class)
public class TestReviewsTest extends InstrumentationTestCase{
    private ReviewsRepository mRepo;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mRepo = TestReviews.getReviews(getInstrumentation());
    }

    @Test
    public void testGetReviews() {
        Collection<Review> reviews = mRepo.getReviews();
        checkSize(reviews, 4);
        Iterator<Review> it = reviews.iterator();
        testReview1(it.next());
        testReview2(it.next());
        testReview3(it.next());
        testReview4(it.next());
    }

    private void testReview1(Review review) {
        ReviewId reviewId = review.getReviewId();
        checkReviewBasics(review, "Tayyabs", 3.5f, true, date(2015, 1, 25, 19, 30));

        //Tags
        checkTags(reviewId, new String[]{"Restaurant", "Pakistani", "London"});

        //Children
        IdableList<? extends DataCriterionReview> criteria = review.getCriteria();
        checkSize(criteria, 3);
        checkCriterion(criteria.getItem(0), "Food", 4f, reviewId);
        checkCriterion(criteria.getItem(1), "Service", 2f, reviewId);
        checkCriterion(criteria.getItem(2), "Value", 4.5f, reviewId);


        //Comments
        IdableList<? extends DataComment> comments = review.getComments();
        checkSize(comments, 3);
        checkComment("Good food. Variable service. Very good value.", comments.getItem(0));
        checkComment("Drinks are BYO.", comments.getItem(1));
        checkComment("Be prepared to queue at peak times.", comments.getItem(2));

        //Locations
        IdableList<? extends DataLocation> locations = review.getLocations();
        checkSize(locations, 1);
        checkLocation(locations.getItem(0), "Tayyabs", 51.517972, -0.063291);

        //Facts
        IdableList<? extends DataFact> facts = review.getFacts();
        checkSize(facts, 5);
        checkFact("Starter", "5", facts.getItem(0));
        checkFact("Main", "8", facts.getItem(1));
        checkFact("Desert", "5", facts.getItem(2));
        checkFact("Drinks", "BYO", facts.getItem(3));

        //Images
        IdableList<? extends DataImage> images = review.getImages();
        checkSize(images, 2);
        checkImage(R.raw.tayyabs, "Lovely lamb chops!", date(2015, 1, 25, 19, 15), images.getItem(0));
        checkImage(R.raw.tayyabs_14, "Frontage", date(2015, 1, 25, 19, 0), images.getItem(1));
    }

    private void testReview2(Review review) {
        ReviewId reviewId = review.getReviewId();
        checkReviewBasics(review, "The Weekend", 5f, false, date(2015, 4, 26, 14, 30));

        //Tags
        checkTags(reviewId, new String[]{"Reading", "Mum", "Kew Gardens", "Baby"});

        //Children
        IdableList<? extends DataCriterionReview> criteria = review.getCriteria();
        checkSize(criteria, 3);
        checkCriterion(criteria.getItem(0), "Friday", 4f, reviewId);
        checkCriterion(criteria.getItem(1), "Saturday", 3.5f, reviewId);
        checkCriterion(criteria.getItem(2), "Sunday", 4f, reviewId);

        //Comments
        IdableList<? extends DataComment> comments = review.getComments();
        checkSize(comments, 3);
        checkComment("Mum made curry which was awesome! Had coconut cake for dessert. Also great " +
                "but ate too much.", comments.getItem(0));
        checkComment("Saturday went to Kew Gardens to see the blossom. A little late for blossom " +
                        "and lots of place overhead. However was fun to hang out with mum and she" +
                        " enjoyed it.",
                comments.getItem(1));
        checkComment("Sunday went to look at cars and cots in preparation for spot. Exciting. For" +
                " mum anyway...", comments.getItem(2));

        //Locations
        IdableList<? extends DataLocation> locations = review.getLocations();
        checkSize(locations, 4);
        checkLocation(locations.getItem(0), "Home", 51.453149, -1.058555);
        checkLocation(locations.getItem(1), "Kew Gardens", 51.478914, -0.295557);
        checkLocation(locations.getItem(2), "Car contacts", 51.460987, -1.038010);
        checkLocation(locations.getItem(3), "ToysRUs", 51.456697, -0.960154);

        //Facts
        IdableList<? extends DataFact> facts = review.getFacts();
        checkSize(facts, 6);
        checkFact("Friday dinner", "Curry and coconut & chocolate cake", facts.getItem(0));
        checkFact("Kew lunch", "Fish pie", facts.getItem(1));
        checkFact("Car", "Skoda Octavia Estate", facts.getItem(2));
        checkFact("Car price", "4495", facts.getItem(3));
        checkFact("Cot", "Sleigh Cot in antique", facts.getItem(4));
        checkFact("Cot price", "199 plus mattress", facts.getItem(5));


        //Images
        IdableList<? extends DataImage> images = review.getImages();
        checkSize(images, 3);
        checkImage(R.raw.kew, "Selfie in Kew!", date(2015, 4, 25, 14, 15), images.getItem(0));
        checkImage(R.raw.car, "Skoda Octavia", date(2015, 4, 26, 13, 0), images.getItem(1));
        checkImage(R.raw.cot, "Cot", date(2015, 4, 26, 14, 15), images.getItem(2));
    }

    private void testReview3(Review review) {
        ReviewId reviewId = review.getReviewId();
        checkReviewBasics(review, "Tayyabs", 8f/3f, true, date(2015, 7, 20, 12, 30));

        //Tags
        checkTags(reviewId, new String[]{"Restaurant", "Pakistani", "London"});

        //Children
        IdableList<? extends DataCriterionReview> criteria = review.getCriteria();
        checkSize(criteria, 3);
        checkCriterion(criteria.getItem(0), "Food", 3f, reviewId);
        checkCriterion(criteria.getItem(1), "Service", 1f, reviewId);
        checkCriterion(criteria.getItem(2), "Value", 4f, reviewId);


        //Comments
        IdableList<? extends DataComment> comments = review.getComments();
        checkSize(comments, 3);
        checkComment("Food not so good today. Variable service.", comments.getItem(0));
        checkComment("Very busy today and they couldn't cope.", comments.getItem(1));
        checkComment("Food was cold. Food came late.", comments.getItem(2));

        //Locations
        IdableList<? extends DataLocation> locations = review.getLocations();
        checkSize(locations, 1);
        checkLocation(locations.getItem(0), "Tayyabs", 51.517975, -0.063295);

        //Facts
        IdableList<? extends DataFact> facts = review.getFacts();
        checkSize(facts, 4);
        checkFact("Starter", "5", facts.getItem(0));
        checkFact("Main", "9", facts.getItem(1));
        checkFact("Desert", "4", facts.getItem(2));
        checkFact("Link", "http://www.tayyabs.co.uk/", facts.getItem(3));

        //Images
        IdableList<? extends DataImage> images = review.getImages();
        checkSize(images, 2);
        checkImage(R.raw.tayyabs_14, "Lamb chops", date(2015, 7, 20, 12, 15), images.getItem(0));
        checkImage(R.raw.tayyabs, "Restaurant", date(2015, 7, 20, 12, 0), images.getItem(1));
    }

    private void testReview4(Review review) {
        ReviewId reviewId = review.getReviewId();
        checkReviewBasics(review, "Asda Nappies", 0f, true, date(2015, 7, 20, 12, 45));

        //Tags
        checkTags(reviewId, new String[]{"Nappies", "Asda"});
    }

    private void checkComment(String comment, DataComment item) {
        assertThat(item.getComment(), is(comment));
    }

    @NonNull
    private Date date(int year, int month, int day, int hour, int minute) {
        return new GregorianCalendar(year, month, day, hour, minute).getTime();
    }

    private void checkImage(int bitmapId, String caption, Date date, DataImage image) {
        assertThat(image.getCaption(), is(caption));
        assertThat(image.getDate().getTime(), is(date.getTime()));
        Bitmap bitmap = loadBitmap(bitmapId);
        assertNotNull(bitmap);
        assertThat(bitmap.sameAs(image.getBitmap()), is(true));
    }

    private void checkSize(Collection<?> collection, int size) {
        assertThat(collection.size(), is(size));
    }

    private void checkTags(ReviewId reviewId, String[] strings) {
        ItemTagCollection tags = mRepo.getTagsManager().getTags(reviewId.toString());
        checkSize(tags, strings.length);
        for(int i = 0; i < tags.size(); ++i) {
            assertThat(tags.getItemTag(i).getTag(), is(strings[i]));
        }
    }

    private void checkReviewBasics(Review review, String subject, float rating, boolean isAverage, Date date) {
        assertThat(review.getSubject().getSubject(), is(subject));
        assertThat(review.isRatingAverageOfCriteria(), is(isAverage));
        assertThat(review.getRating().getRating(),is(rating));
        assertThat(review.getPublishDate().getTime(), is(date.getTime()));
    }

    private void checkFact(String label, String value, DataFact fact) {
        assertThat(fact.getLabel(), is(label));
        assertThat(fact.getValue(), is(value));
    }

    private void checkLocation(DataLocation location, String locationName, double lat, double lng) {
        assertThat(location.getName(), is(locationName));
        assertThat(location.getLatLng().latitude, is(lat));
        assertThat(location.getLatLng().longitude, is(lng));
    }

    private void checkCriterion(DataCriterionReview criterion, String criterionSubject, float criterionRating, ReviewId reviewId) {
        Review child = criterion.getReview();
        assertThat(criterion.getReviewId(), is(reviewId));
        assertThat(child.getSubject().getSubject(), is(criterionSubject));
        assertThat(child.getRating().getRating(), is(criterionRating));
        checkSize(child.getCriteria(), 0);
    }

    @Nullable
    private Bitmap loadBitmap(int rawResource) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            Context target = getInstrumentation().getTargetContext();
            is = target.getResources().openRawResource(rawResource);
            int width = (int) target.getResources().getDimension(R.dimen.imageMaxWidth);
            int height = (int) target.getResources().getDimension(R.dimen.imageMaxHeight);
            bitmap = BitmapFactory.decodeStream(is);
            bitmap = ImageHelper.rescalePreservingAspectRatio(bitmap, width, height);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}

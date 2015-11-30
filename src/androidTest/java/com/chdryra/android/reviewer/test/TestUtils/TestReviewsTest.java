/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestReviewsTest extends InstrumentationTestCase {

    @SmallTest
    public void testGetReviews() {
        ReviewsRepository provider = TestReviews.getReviews(getInstrumentation());
        MdIdableCollection<Review> reviews = provider.getReviews();
        assertEquals(2, reviews.size());
        testReview1(reviews.getItem(0), provider.getTagsManager());
        testReview2(reviews.getItem(1), provider.getTagsManager());
    }

    private void testReview1(Review review, TagsManager tagsManager) {
        assertEquals("Tayyabs", review.getSubject().getSubject());
        assertTrue(review.isRatingAverageOfCriteria());
        assertEquals(3.5f, review.getRating().getRating());

        //Tags
        ItemTagCollection tags = tagsManager.getTags(review.getMdReviewId());
        assertEquals(3, tags.size());
        assertEquals("Restaurant", tags.getItemTag(0).getTag());
        assertEquals("Pakistani", tags.getItemTag(1).getTag());
        assertEquals("London", tags.getItemTag(2).getTag());

        //Children
        MdCriterionList criteria = review.getCriteria();
        assertEquals(3, tags.size());

        MdCriterion criterion = criteria.getItem(0);
        assertEquals(review, criterion.getReviewId());
        Review child = criterion.getReview();
        assertEquals("Food", child.getSubject().getSubject());
        assertEquals(4f, child.getRating().getRating());
        assertEquals(0, child.getCriteria().size());

        criterion = criteria.getItem(1);
        child = criterion.getReview();
        assertEquals(review.getMdReviewId(), criterion.getReviewId());
        assertEquals("Service", child.getSubject().getSubject());
        assertEquals(2f, child.getRating().getRating());
        assertEquals(0, child.getCriteria().size());

        criterion = criteria.getItem(2);
        child = criterion.getReview();
        assertEquals(review.getMdReviewId(), criterion.getReviewId());
        assertEquals("Value", child.getSubject().getSubject());
        assertEquals(4.5f, child.getRating().getRating());
        assertEquals(0, child.getCriteria().size());

        //Comments
        MdCommentList comments = review.getComments();
        assertEquals(3, comments.size());
        assertEquals("Good food but variable service. Very good value though.", comments.getItem
                (0).getComment());
        assertEquals("Drinks are BYO.", comments.getItem(1).getComment());
        assertEquals("Be prepared to queue at peak times.", comments.getItem(2).getComment());

        //Locations
        MdLocationList locations = review.getLocations();
        assertEquals(1, locations.size());
        MdLocation location = locations.getItem(0);
        assertEquals("Tayyabs", location.getName());
        assertEquals(51.517972, location.getLatLng().latitude);
        assertEquals(-0.063291, location.getLatLng().longitude);

        //Facts
        MdFactList facts = review.getFacts();
        assertEquals(5, facts.size());

        assertEquals("Starter", facts.getItem(0).getLabel());
        assertEquals("5", facts.getItem(0).getValue());
        assertEquals("Main", facts.getItem(1).getLabel());
        assertEquals("8", facts.getItem(1).getValue());
        assertEquals("Desert", facts.getItem(2).getLabel());
        assertEquals("5", facts.getItem(2).getValue());
        assertEquals("Drinks", facts.getItem(3).getLabel());
        assertEquals("BYO", facts.getItem(3).getValue());

        //Images
        MdImageList images = review.getImages();
        assertEquals(2, images.size());

        MdImage image = images.getItem(0);
        assertEquals("Lovely lamb chops!", image.getCaption());
        GregorianCalendar cal = new GregorianCalendar(2015, 1, 25, 19, 15);
        assertEquals(cal.getTime(), image.getDate());
        assertTrue(loadBitmap("tayyabs-14.jpg").sameAs(image.getBitmap()));

        image = images.getItem(1);
        assertEquals("Frontage", image.getCaption());
        cal = new GregorianCalendar(2015, 1, 25, 19, 0);
        assertEquals(cal.getTime(), image.getDate());
        assertTrue(loadBitmap("tayyabs.jpg").sameAs(image.getBitmap()));
    }

    private void testReview2(Review review, TagsManager tagsManager) {
        assertEquals("The Weekend", review.getSubject().getSubject());
        assertFalse(review.isRatingAverageOfCriteria());
        assertEquals(5f, review.getRating().getRating());

        //Tags
        ItemTagCollection tags = tagsManager.getTags(review.getMdReviewId());
        assertEquals(4, tags.size());
        assertEquals("Reading", tags.getItemTag(0).getTag());
        assertEquals("Mum", tags.getItemTag(1).getTag());
        assertEquals("Kew Gardens", tags.getItemTag(2).getTag());
        assertEquals("Baby", tags.getItemTag(3).getTag());

        //Children
        MdCriterionList children = review.getCriteria();
        assertEquals(3, children.size());

        MdCriterion criterion = children.getItem(0);
        Review child = criterion.getReview();
        assertEquals(review.getMdReviewId(), criterion.getReviewId());
        assertEquals("Friday", child.getSubject().getSubject());
        assertEquals(4f, child.getRating().getRating());
        assertEquals(0, child.getCriteria().size());

        criterion = children.getItem(1);
        child = criterion.getReview();
        assertEquals(review.getMdReviewId(), criterion.getReviewId());
        assertEquals("Saturday", child.getSubject().getSubject());
        assertEquals(3.5f, child.getRating().getRating());
        assertEquals(0, child.getCriteria().size());

        criterion = children.getItem(2);
        child = criterion.getReview();
        assertEquals(review.getMdReviewId(), criterion.getReviewId());
        assertEquals("Sunday", child.getSubject().getSubject());
        assertEquals(4f, child.getRating().getRating());
        assertEquals(0, child.getCriteria().size());

        //Comments
        MdCommentList comments = review.getComments();
        assertEquals(3, comments.size());
        assertEquals("Mum made curry which was awesome! Had coconut cake for dessert. Also great " +
                "but ate too much.", comments.getItem(0).getComment());
        assertEquals("Saturday went to Kew Gardens to see the blossom. A little late for blossom " +
                        "and lots of place overhead. However was fun to hang out with mum and she" +
                        " enjoyed it.",
                comments.getItem(1).getComment());
        assertEquals("Sunday went to look at cars and cots in preparation for spot. Exciting. For" +
                " mum anyway...", comments.getItem(2).getComment());

        //Locations
        MdLocationList locations = review.getLocations();
        assertEquals(4, locations.size());

        MdLocation location = locations.getItem(0);
        assertEquals("Home", location.getName());
        assertEquals(51.453149, location.getLatLng().latitude);
        assertEquals(-1.058555, location.getLatLng().longitude);

        location = locations.getItem(1);
        assertEquals("Kew Gardens", location.getName());
        assertEquals(51.478914, location.getLatLng().latitude);
        assertEquals(-0.295557, location.getLatLng().longitude);

        location = locations.getItem(2);
        assertEquals("Car contacts", location.getName());
        assertEquals(51.460987, location.getLatLng().latitude);
        assertEquals(-1.038010, location.getLatLng().longitude);

        location = locations.getItem(3);
        assertEquals("ToysRUs", location.getName());
        assertEquals(51.456697, location.getLatLng().latitude);
        assertEquals(-0.960154, location.getLatLng().longitude);

        //Facts
        MdFactList facts = review.getFacts();
        assertEquals(6, facts.size());
        assertEquals("Friday dinner", facts.getItem(0).getLabel());
        assertEquals("Curry and coconut & chocolate cake", facts.getItem(0).getValue());
        assertEquals("Kew lunch", facts.getItem(1).getLabel());
        assertEquals("Fish pie", facts.getItem(1).getValue());
        assertEquals("Car", facts.getItem(2).getLabel());
        assertEquals("Skoda Octavia Estate", facts.getItem(2).getValue());
        assertEquals("Car price", facts.getItem(3).getLabel());
        assertEquals("4495", facts.getItem(3).getValue());
        assertEquals("Cot", facts.getItem(4).getLabel());
        assertEquals("Sleigh Cot in antique", facts.getItem(4).getValue());
        assertEquals("Cot price", facts.getItem(5).getLabel());
        assertEquals("199 plus mattress", facts.getItem(5).getValue());


        //Images
        MdImageList images = review.getImages();
        assertEquals(3, images.size());

        MdImage image = images.getItem(0);
        assertEquals("Selfie in Kew!", image.getCaption());
        GregorianCalendar cal = new GregorianCalendar(2015, 4, 25, 14, 15);
        assertEquals(cal.getTime(), image.getDate());
        assertTrue(loadBitmap("Kew.jpg").sameAs(image.getBitmap()));

        image = images.getItem(1);
        assertEquals("Skoda Octavia", image.getCaption());
        cal = new GregorianCalendar(2015, 4, 26, 13, 0);
        assertEquals(cal.getTime(), image.getDate());
        assertTrue(loadBitmap("Car.jpeg").sameAs(image.getBitmap()));

        image = images.getItem(2);
        assertEquals("Cot", image.getCaption());
        cal = new GregorianCalendar(2015, 4, 26, 14, 15);
        assertEquals(cal.getTime(), image.getDate());
        assertTrue(loadBitmap("Cot.jpeg").sameAs(image.getBitmap()));
    }

    private Bitmap loadBitmap(String fileName) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            Context test = getInstrumentation().getContext();
            Context target = getInstrumentation().getTargetContext();
            is = test.getAssets().open(fileName);
            int width = (int) target.getResources().getDimension(R.dimen.imageMaxWidth);
            int height = (int) target.getResources().getDimension(R.dimen.imageMaxHeight);
            bitmap = BitmapFactory.decodeStream(is);
            bitmap = ImageHelper.rescalePreservingAspectRatio(bitmap, width, height);
        } catch (IOException e) {
            e.printStackTrace();
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

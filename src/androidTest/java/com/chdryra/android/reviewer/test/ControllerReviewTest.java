/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;

import com.chdryra.android.reviewer.ControllerReview;
import com.chdryra.android.reviewer.GVCommentList;
import com.chdryra.android.reviewer.GVFactList;
import com.chdryra.android.reviewer.GVImageList;
import com.chdryra.android.reviewer.GVLocationList;
import com.chdryra.android.reviewer.GVReviewDataList;
import com.chdryra.android.reviewer.GVTagList;
import com.chdryra.android.reviewer.GVUrlList;
import com.chdryra.android.reviewer.RDCommentList;
import com.chdryra.android.reviewer.RDFactList;
import com.chdryra.android.reviewer.RDImageList;
import com.chdryra.android.reviewer.RDLocationList;
import com.chdryra.android.reviewer.RDUrlList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.test.TestUtils.RDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomStringGenerator;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

public class ControllerReviewTest extends AndroidTestCase {
    private static GVReviewDataList.GVType[] mRelevantGVTypes;
    private        Review                    mReview;
    private        ControllerReview          mController;

    public void testGetReviewNode() {
        assertNotNull(mController.getReviewNode());
    }

    public void testGetId() {
        assertEquals(mReview.getId().toString(), mController.getId());
    }

    public void testGetSubject() {
        assertEquals(mReview.getSubject().get(), mController.getSubject());
    }

    public void testGetRating() {
        assertEquals(mReview.getRating().get(), mController.getRating());
    }

    public void testGetAuthor() {
        assertEquals(mReview.getAuthor().getName(), mController.getAuthor());
    }

    public void testGetPublishDate() {
        assertEquals(mReview.getPublishDate(), mController.getPublishDate());
    }

    public void testIsPublished() {
        assertEquals(mReview.isPublished(), mController.isPublished());
    }

    public void testAddTags() {
        GVTagList getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertTrue(getTags.size() == 0);

        String[] tagArray = new String[3];
        tagArray[0] = "Alpha";
        tagArray[1] = "Beta";
        tagArray[2] = "Gamma";

        GVTagList tags = new GVTagList();
        tags.add(tagArray[0]);
        tags.add(tagArray[1]);
        tags.add(tagArray[2]);
        mController.addTags(tags);

        getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertEquals(tags.size(), getTags.size());
        for (int i = 0; i < tagArray.length; ++i) {
            assertEquals(tagArray[i], getTags.getItem(i).get());
        }
    }

    public void testRemoveTags() {
        GVTagList getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertTrue(getTags.size() == 0);

        String[] tagArray = new String[3];
        tagArray[0] = "Alpha";
        tagArray[1] = "Beta";
        tagArray[2] = "Gamma";

        GVTagList tags = new GVTagList();
        tags.add(tagArray[0]);
        tags.add(tagArray[1]);
        tags.add(tagArray[2]);
        mController.addTags(tags);

        getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertEquals(tags.size(), getTags.size());

        mController.removeTags();
        getTags = (GVTagList) mController.getData(GVReviewDataList.GVType.TAGS);
        assertTrue(getTags.size() == 0);
    }

    //TODO Anyway of doing this with Generics? Problems with type erasure limit overloading
    public void testHasData() {
        ReviewEditable editable = ReviewMocker.newReviewEditable();
        ControllerReview<Review> controller = new ControllerReview<Review>(editable);

        int num = 9;
        Random rand = new Random();

        //Comments
        testOnlyHasData(controller, null);
        editable.setComments(RDataMocker.newCommentList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GVReviewDataList.GVType.COMMENTS);
        editable.deleteComments();

        //Facts
        testOnlyHasData(controller, null);
        editable.setFacts(RDataMocker.newFactList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GVReviewDataList.GVType.FACTS);
        editable.deleteFacts();

        //Images
        testOnlyHasData(controller, null);
        editable.setImages(RDataMocker.newImageList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GVReviewDataList.GVType.IMAGES);
        editable.deleteImages();

        //Locations
        testOnlyHasData(controller, null);
        editable.setLocations(RDataMocker.newLocationList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GVReviewDataList.GVType.LOCATIONS);
        editable.deleteLocations();

        //Urls
        testOnlyHasData(controller, null);
        editable.setUrls(RDataMocker.newUrlList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GVReviewDataList.GVType.URLS);
        editable.deleteUrls();

        //Tags
        testOnlyHasData(controller, null);
        GVTagList tags = new GVTagList();
        int maxTags = rand.nextInt(num);
        for (int i = 0; i < maxTags; ++i) {
            tags.add(RandomStringGenerator.nextWord());
        }
        controller.addTags(tags);
        testOnlyHasData(controller, GVReviewDataList.GVType.TAGS);
        controller.removeTags();
        testOnlyHasData(controller, null);
    }

    //TODO Anyway of doing this with Generics? Problems with type erasure limit overloading
    public void testGetData() {
        ReviewEditable editable = ReviewMocker.newReviewEditable();
        ControllerReview<Review> controller = new ControllerReview<Review>(editable);

        int num = 9;
        Random rand = new Random();

        //Comments
        testAndGetGVList(GVReviewDataList.GVType.COMMENTS, controller, 0);
        RDCommentList rdComments = RDataMocker.newCommentList(rand.nextInt(num) + 1);
        editable.setComments(rdComments);
        GVCommentList gvComments = (GVCommentList) testAndGetGVList(GVReviewDataList.GVType
                .COMMENTS, controller, rdComments.size());
        for (int i = 0; i < rdComments.size(); ++i) {
            assertEquals(rdComments.getItem(i).getComment(), gvComments.getItem(i).getComment());
        }

        //Facts
        testAndGetGVList(GVReviewDataList.GVType.FACTS, controller, 0);
        RDFactList rdFacts = RDataMocker.newFactList(rand.nextInt(num) + 1);
        editable.setFacts(rdFacts);
        GVFactList gvFacts = (GVFactList) testAndGetGVList(GVReviewDataList.GVType.FACTS,
                controller, rdFacts.size());
        for (int i = 0; i < rdFacts.size(); ++i) {
            assertEquals(rdFacts.getItem(i).getLabel(), gvFacts.getItem(i).getLabel());
            assertEquals(rdFacts.getItem(i).getValue(), gvFacts.getItem(i).getValue());
        }

        //Images
        testAndGetGVList(GVReviewDataList.GVType.IMAGES, controller, 0);
        RDImageList rdImages = RDataMocker.newImageList(rand.nextInt(num) + 1);
        editable.setImages(rdImages);
        GVImageList gvImages = (GVImageList) testAndGetGVList(GVReviewDataList.GVType.IMAGES,
                controller, rdImages.size());
        for (int i = 0; i < rdImages.size(); ++i) {
            RDImageList.RDImage rdImage = rdImages.getItem(i);
            GVImageList.GVImage gvImage = gvImages.getItem(i);
            assertTrue(rdImage.getBitmap().sameAs(gvImage.getBitmap()));
            assertEquals(rdImage.getCaption(), gvImage.getCaption());
            assertEquals(rdImage.getLatLng(), gvImage.getLatLng());
            assertEquals(rdImage.isCover(), gvImage.isCover());
        }

        //Locations
        testAndGetGVList(GVReviewDataList.GVType.LOCATIONS, controller, 0);
        RDLocationList rdLocations = RDataMocker.newLocationList(rand.nextInt(num) + 1);
        editable.setLocations(rdLocations);
        GVLocationList gvLocations = (GVLocationList) testAndGetGVList(GVReviewDataList.GVType
                .LOCATIONS, controller, rdLocations.size());
        for (int i = 0; i < rdLocations.size(); ++i) {
            assertEquals(rdLocations.getItem(i).getLatLng(), gvLocations.getItem(i).getLatLng());
            assertEquals(rdLocations.getItem(i).getName(), gvLocations.getItem(i).getName());
        }

        //Urls
        testAndGetGVList(GVReviewDataList.GVType.URLS, controller, 0);
        RDUrlList rdUrls = RDataMocker.newUrlList(rand.nextInt(num) + 1);
        editable.setUrls(rdUrls);
        GVUrlList gvUrls = (GVUrlList) testAndGetGVList(GVReviewDataList.GVType.URLS,
                controller, rdUrls.size());
        for (int i = 0; i < rdUrls.size(); ++i) {
            assertEquals(rdUrls.getItem(i).getUrl(), gvUrls.getItem(i).getUrl());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mReview = ReviewMocker.newReview();
        mController = new ControllerReview<>(mReview);
        mRelevantGVTypes = new GVReviewDataList.GVType[]{GVReviewDataList.GVType.FACTS,
                GVReviewDataList.GVType.URLS,
                GVReviewDataList.GVType.LOCATIONS, GVReviewDataList.GVType.COMMENTS,
                GVReviewDataList.GVType.IMAGES, GVReviewDataList.GVType.TAGS};
    }

    private GVReviewDataList testAndGetGVList(GVReviewDataList.GVType dataType,
            ControllerReview controller, int expectedSize) {
        GVReviewDataList gvList = controller.getData(dataType);
        assertNotNull(gvList);
        assertEquals(expectedSize, gvList.size());

        return gvList;
    }

    private void testOnlyHasData(ControllerReview controller, GVReviewDataList.GVType dataType) {
        for (GVReviewDataList.GVType gv : mRelevantGVTypes) {
            if (gv == dataType) {
                assertTrue(controller.hasData(gv));
            } else {
                assertFalse(controller.hasData(gv));
            }
        }
    }
}

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
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdFactList;
import com.chdryra.android.reviewer.MdImageList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.MdUrlList;
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
    private static GvDataList.GvType[] sMRelevantGvTypes;
    private        Review              mReview;
    private        ControllerReview    mController;
    private        RDataMocker         mRdataMocker;

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
        GvTagList getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        assertTrue(getTags.size() == 0);

        String[] tagArray = new String[3];
        tagArray[0] = "Alpha";
        tagArray[1] = "Beta";
        tagArray[2] = "Gamma";

        GvTagList tags = new GvTagList();
        tags.add(tagArray[0]);
        tags.add(tagArray[1]);
        tags.add(tagArray[2]);
        mController.addTags(tags);

        getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        assertEquals(tags.size(), getTags.size());
        for (int i = 0; i < tagArray.length; ++i) {
            assertEquals(tagArray[i], getTags.getItem(i).get());
        }
    }

    public void testRemoveTags() {
        GvTagList getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        assertTrue(getTags.size() == 0);

        String[] tagArray = new String[3];
        tagArray[0] = "Alpha";
        tagArray[1] = "Beta";
        tagArray[2] = "Gamma";

        GvTagList tags = new GvTagList();
        tags.add(tagArray[0]);
        tags.add(tagArray[1]);
        tags.add(tagArray[2]);
        mController.addTags(tags);

        getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
        assertEquals(tags.size(), getTags.size());

        mController.removeTags();
        getTags = (GvTagList) mController.getData(GvDataList.GvType.TAGS);
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
        editable.setComments(mRdataMocker.newCommentList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GvDataList.GvType.COMMENTS);
        editable.deleteComments();

        //Facts
        testOnlyHasData(controller, null);
        editable.setFacts(mRdataMocker.newFactList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GvDataList.GvType.FACTS);
        editable.deleteFacts();

        //Images
        testOnlyHasData(controller, null);
        editable.setImages(mRdataMocker.newImageList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GvDataList.GvType.IMAGES);
        editable.deleteImages();

        //Locations
        testOnlyHasData(controller, null);
        editable.setLocations(mRdataMocker.newLocationList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GvDataList.GvType.LOCATIONS);
        editable.deleteLocations();

        //Urls
        testOnlyHasData(controller, null);
        editable.setUrls(mRdataMocker.newUrlList(rand.nextInt(num) + 1));
        testOnlyHasData(controller, GvDataList.GvType.URLS);
        editable.deleteUrls();

        //Tags
        testOnlyHasData(controller, null);
        GvTagList tags = new GvTagList();
        int maxTags = rand.nextInt(num);
        for (int i = 0; i < maxTags; ++i) {
            tags.add(RandomStringGenerator.nextWord());
        }
        controller.addTags(tags);
        testOnlyHasData(controller, GvDataList.GvType.TAGS);
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
        testAndGetGVList(GvDataList.GvType.COMMENTS, controller, 0);
        MdCommentList rdComments = mRdataMocker.newCommentList(rand.nextInt(num) + 1);
        editable.setComments(rdComments);
        GvCommentList gvComments = (GvCommentList) testAndGetGVList(GvDataList.GvType
                .COMMENTS, controller, rdComments.size());
        for (int i = 0; i < rdComments.size(); ++i) {
            assertEquals(rdComments.getItem(i).getComment(), gvComments.getItem(i).getComment());
        }

        //Facts
        testAndGetGVList(GvDataList.GvType.FACTS, controller, 0);
        MdFactList rdFacts = mRdataMocker.newFactList(rand.nextInt(num) + 1);
        editable.setFacts(rdFacts);
        GvFactList gvFacts = (GvFactList) testAndGetGVList(GvDataList.GvType.FACTS,
                controller, rdFacts.size());
        for (int i = 0; i < rdFacts.size(); ++i) {
            assertEquals(rdFacts.getItem(i).getLabel(), gvFacts.getItem(i).getLabel());
            assertEquals(rdFacts.getItem(i).getValue(), gvFacts.getItem(i).getValue());
        }

        //Images
        testAndGetGVList(GvDataList.GvType.IMAGES, controller, 0);
        MdImageList rdImages = mRdataMocker.newImageList(rand.nextInt(num) + 1);
        editable.setImages(rdImages);
        GvImageList gvImages = (GvImageList) testAndGetGVList(GvDataList.GvType.IMAGES,
                controller, rdImages.size());
        for (int i = 0; i < rdImages.size(); ++i) {
            MdImageList.MdImage mdImage = rdImages.getItem(i);
            GvImageList.GvImage gvImage = gvImages.getItem(i);
            assertTrue(mdImage.getBitmap().sameAs(gvImage.getBitmap()));
            assertEquals(mdImage.getCaption(), gvImage.getCaption());
            assertEquals(mdImage.getLatLng(), gvImage.getLatLng());
            assertEquals(mdImage.isCover(), gvImage.isCover());
        }

        //Locations
        testAndGetGVList(GvDataList.GvType.LOCATIONS, controller, 0);
        MdLocationList rdLocations = mRdataMocker.newLocationList(rand.nextInt(num) + 1);
        editable.setLocations(rdLocations);
        GvLocationList gvLocations = (GvLocationList) testAndGetGVList(GvDataList.GvType
                .LOCATIONS, controller, rdLocations.size());
        for (int i = 0; i < rdLocations.size(); ++i) {
            assertEquals(rdLocations.getItem(i).getLatLng(), gvLocations.getItem(i).getLatLng());
            assertEquals(rdLocations.getItem(i).getName(), gvLocations.getItem(i).getName());
        }

        //Urls
        testAndGetGVList(GvDataList.GvType.URLS, controller, 0);
        MdUrlList rdUrls = mRdataMocker.newUrlList(rand.nextInt(num) + 1);
        editable.setUrls(rdUrls);
        GvUrlList gvUrls = (GvUrlList) testAndGetGVList(GvDataList.GvType.URLS,
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
        mRdataMocker = new RDataMocker(mReview);
        sMRelevantGvTypes = new GvDataList.GvType[]{GvDataList.GvType.FACTS,
                GvDataList.GvType.URLS,
                GvDataList.GvType.LOCATIONS, GvDataList.GvType.COMMENTS,
                GvDataList.GvType.IMAGES, GvDataList.GvType.TAGS};
    }

    private GvDataList testAndGetGVList(GvDataList.GvType dataType,
            ControllerReview controller, int expectedSize) {
        GvDataList gvList = controller.getData(dataType);
        assertNotNull(gvList);
        assertEquals(expectedSize, gvList.size());

        return gvList;
    }

    private void testOnlyHasData(ControllerReview controller, GvDataList.GvType dataType) {
        for (GvDataList.GvType gv : sMRelevantGvTypes) {
            if (gv == dataType) {
                assertTrue(controller.hasData(gv));
            } else {
                assertFalse(controller.hasData(gv));
            }
        }
    }
}

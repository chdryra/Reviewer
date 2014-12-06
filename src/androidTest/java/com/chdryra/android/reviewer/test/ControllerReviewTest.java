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
import com.chdryra.android.reviewer.GVReviewDataList;
import com.chdryra.android.reviewer.GVTagList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

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
//
//    //TODO Anyway of doing this with Generics? Problems with type erasure limit overloading
//    public void testHasData() {
//        ReviewEditable editable = ReviewMocker.newReviewEditable();
//        ControllerReview<Review> controller = new ControllerReview<Review>(editable);
//
//        int num = 9;
//        Random rand = new Random();
//
//        //Comments
//        testOnlyHasData(controller, null);
//        editable.setComments(RDataMocker.newRDList(RDComment.class, rand.nextInt(num) + 1));
//        testOnlyHasData(controller, GVReviewDataList.GVType.COMMENTS);
//        editable.setComments(RDataMocker.newRDList(RDComment.class, 0));
//
//        //Facts
//        testOnlyHasData(controller, null);
//        editable.setFacts(RDataMocker.newRDList(RDFact.class, rand.nextInt(num) + 1));
//        testOnlyHasData(controller, GVReviewDataList.GVType.FACTS);
//        editable.setFacts(RDataMocker.newRDList(RDFact.class, 0));
//
//        //Images
//        testOnlyHasData(controller, null);
//        editable.setImages(RDataMocker.newRDList(RDImage.class, rand.nextInt(num) + 1));
//        testOnlyHasData(controller, GVReviewDataList.GVType.IMAGES);
//        editable.setImages(RDataMocker.newRDList(RDImage.class, 0));
//
//        //Locations
//        testOnlyHasData(controller, null);
//        editable.setLocations(RDataMocker.newRDList(RDLocation.class, rand.nextInt(num) + 1));
//        testOnlyHasData(controller, GVReviewDataList.GVType.LOCATIONS);
//        editable.setLocations(RDataMocker.newRDList(RDLocation.class, 0));
//
//        //Urls
//        testOnlyHasData(controller, null);
//        editable.setURLs(RDataMocker.newRDList(RDUrl.class, rand.nextInt(num) + 1));
//        testOnlyHasData(controller, GVReviewDataList.GVType.URLS);
//        editable.setURLs(RDataMocker.newRDList(RDUrl.class, 0));
//
//        //Tags
//        testOnlyHasData(controller, null);
//        GVTagList tags = new GVTagList();
//        int maxTags = rand.nextInt(num);
//        for(int i = 0; i < maxTags; ++i)
//            tags.add(RandomStringGenerator.nextWord());
//        controller.addTags(tags);
//        testOnlyHasData(controller, GVReviewDataList.GVType.TAGS);
//        controller.removeTags();
//        testOnlyHasData(controller, null);
//    }
//
//    //TODO Anyway of doing this with Generics? Problems with type erasure limit overloading
//    public void testGetData() {
//        ReviewEditable editable = ReviewMocker.newReviewEditable();
//        ControllerReview<Review> controller = new ControllerReview<Review>(editable);
//
//        int num = 9;
//        Random rand = new Random();
//
//        //Comments
//        GVCommentList gvComments = (GVCommentList)controller.getData(GVReviewDataList.GVType
//                .COMMENTS);
//        assertNotNull(gvComments);
//        assertEquals(0, gvComments.size());
//        RDCommentList rdComments = RDataMocker.newRDList(RDComment.class,
//                rand.nextInt(num) + 1);
//        editable.setComments(rdComments);
//        gvComments = (GVCommentList)controller.getData(GVReviewDataList.GVType.COMMENTS);
//        assertNotNull(gvComments);
//        assertEquals(rdComments.size(), gvComments.size());
//        for(int i = 0; i < rdComments.size(); ++ i)
//            assertEquals(rdComments.getItem(i).get(), gvComments.getItem(i).getComment());
//
//        //Facts
//        GVFactList gvFacts = (GVFactList)controller.getData(GVReviewDataList.GVType.FACTS);
//        assertNotNull(gvFacts);
//        assertEquals(0, gvFacts.size());
//        RDFactList rdFacts = RDataMocker.newRDList(RDFact.class, rand.nextInt(num) + 1);
//        editable.setFacts(rdFacts);
//        gvFacts = (GVFactList)controller.getData(GVReviewDataList.GVType.FACTS);
//        assertNotNull(gvFacts);
//        assertEquals(rdFacts.size(), gvFacts.size());
//        for(int i = 0; i < rdFacts.size(); ++ i) {
//            assertEquals(rdFacts.getItem(i).getLabel(), gvFacts.getItem(i).getLabel());
//            assertEquals(rdFacts.getItem(i).getValue(), gvFacts.getItem(i).getValue());
//        }
//
//        //Images
//        GVImageList gvImages = (GVImageList)controller.getData(GVReviewDataList.GVType.IMAGES);
//        assertNotNull(gvImages);
//        assertEquals(0, gvImages.size());
//        RDImageList rdImages = RDataMocker.newRDList(RDImage.class, rand.nextInt(num) + 1);
//        editable.setImages(rdImages);
//        gvImages = (GVImageList)controller.getData(GVReviewDataList.GVType.IMAGES);
//        assertNotNull(gvImages);
//        assertEquals(rdImages.size(), gvImages.size());
//        for(int i = 0; i < rdImages.size(); ++ i) {
//            assertEquals(rdImages.getItem(i)., gvImages.getItem(i).getLabel());
//            assertEquals(rdImages.getItem(i).getValue(), gvImages.getItem(i).getValue());
//        }
//    }
//
//    private void testOnlyHasData(ControllerReview controller, GVReviewDataList.GVType dataType) {
//        for (GVReviewDataList.GVType gv : mRelevantGVTypes) {
//            if(gv == dataType)
//                assertTrue(controller.hasData(gv));
//            else
//                assertFalse(controller.hasData(gv));
//        }
//    }

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
}

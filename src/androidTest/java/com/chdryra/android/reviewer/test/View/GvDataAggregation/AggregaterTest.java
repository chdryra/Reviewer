package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.location.Location;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 16/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregaterTest extends TestCase {
    private static final Random RAND = new Random();

    @SmallTest
    public void testAggregateAuthor() {
        GvAuthorList.GvAuthor author1 = GvDataMocker.newAuthor(null);
        GvAuthorList.GvAuthor author2 = GvDataMocker.newAuthor(null);
        GvAuthorList.GvAuthor author3 = GvDataMocker.newAuthor(null);
        GvAuthorList.GvAuthor[] references = {author1, author2, author3};

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int[] referenceNums = {num1, num2, num3};
        int maxIter = Math.max(Math.max(num1, num2), num3);
        int total = num1 + num2 + num3;

        GvAuthorList authors = new GvAuthorList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                authors.add(new GvAuthorList.GvAuthor(RandomReviewId.nextGvReviewId(), author1
                        .getName(), author1.getUserId()));
            }
            if (size2++ < num2) {
                authors.add(new GvAuthorList.GvAuthor(RandomReviewId.nextGvReviewId(), author2
                        .getName(), author2.getUserId()));
            }
            if (size3++ < num3) {
                authors.add(new GvAuthorList.GvAuthor(RandomReviewId.nextGvReviewId(), author3
                        .getName(), author3.getUserId()));
            }
        }
        assertEquals(total, authors.size());

        GvCanonicalCollection<GvAuthorList.GvAuthor> results = Aggregater.aggregate(authors);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvAuthorList.GvAuthor> gvCanonical = results.getItem(i);
            GvAuthorList.GvAuthor canonical = gvCanonical.getCanonical();
            GvAuthorList values = (GvAuthorList) gvCanonical.toList();
            GvAuthorList.GvAuthor reference = references[i];
            assertEquals(reference.getName(), canonical.getName());
            assertEquals(reference.getUserId(), canonical.getUserId());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvAuthorList.GvAuthor value = values.getItem(j);
                assertEquals(reference.getName(), value.getName());
                assertEquals(reference.getUserId(), value.getUserId());
            }
        }
    }

    @SmallTest
    public void testAggregateSubject() {
        GvSubjectList.GvSubject subject1 = GvDataMocker.newSubject(null);
        GvSubjectList.GvSubject subject2 = GvDataMocker.newSubject(null);
        GvSubjectList.GvSubject subject3 = GvDataMocker.newSubject(null);
        GvSubjectList.GvSubject[] references = {subject1, subject2, subject3};

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int[] referenceNums = {num1, num2, num3};
        int maxIter = Math.max(Math.max(num1, num2), num3);
        int total = num1 + num2 + num3;

        GvSubjectList subjects = new GvSubjectList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                subjects.add(new GvSubjectList.GvSubject(RandomReviewId.nextGvReviewId(), subject1
                        .getString()));
            }
            if (size2++ < num2) {
                subjects.add(new GvSubjectList.GvSubject(RandomReviewId.nextGvReviewId(), subject2
                        .getString()));
            }
            if (size3++ < num3) {
                subjects.add(new GvSubjectList.GvSubject(RandomReviewId.nextGvReviewId(), subject3
                        .getString()));
            }
        }
        assertEquals(total, subjects.size());

        GvCanonicalCollection<GvSubjectList.GvSubject> results = Aggregater.aggregate(subjects);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvSubjectList.GvSubject> gvCanonical = results.getItem(i);
            GvSubjectList values = (GvSubjectList) gvCanonical.toList();
            GvSubjectList.GvSubject reference = references[i];
            GvSubjectList.GvSubject canonical = gvCanonical.getCanonical();
            assertEquals(reference.getString(), canonical.getString());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvSubjectList.GvSubject value = values.getItem(j);
                assertEquals(reference.getString(), value.getString());
            }
        }
    }

    @SmallTest
    public void testAggregateTag() {
        GvTagList.GvTag tag1 = GvDataMocker.newTag(null);
        GvTagList.GvTag tag2 = GvDataMocker.newTag(null);
        GvTagList.GvTag tag3 = GvDataMocker.newTag(null);
        GvTagList.GvTag[] references = {tag1, tag2, tag3};

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int[] referenceNums = {num1, num2, num3};
        int maxIter = Math.max(Math.max(num1, num2), num3);
        int total = num1 + num2 + num3;

        GvTagList tags = new GvTagList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                tags.add(new GvTagList.GvTag(RandomReviewId.nextGvReviewId(), tag1
                        .getString()));
            }
            if (size2++ < num2) {
                tags.add(new GvTagList.GvTag(RandomReviewId.nextGvReviewId(), tag2
                        .getString()));
            }
            if (size3++ < num3) {
                tags.add(new GvTagList.GvTag(RandomReviewId.nextGvReviewId(), tag3
                        .getString()));
            }
        }
        assertEquals(total, tags.size());

        GvCanonicalCollection<GvTagList.GvTag> results = Aggregater.aggregate(tags);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvTagList.GvTag> gvCanonical = results.getItem(i);
            GvTagList.GvTag canonical = gvCanonical.getCanonical();
            GvTagList values = (GvTagList) gvCanonical.toList();
            GvTagList.GvTag reference = references[i];
            assertEquals(reference.getString(), canonical.getString());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvTagList.GvTag value = values.getItem(j);
                assertEquals(reference.getString(), value.getString());
            }
        }
    }

    @SmallTest
    public void testAggregateComment() {
        GvCommentList.GvComment comment1 = GvDataMocker.newComment(null);
        GvCommentList.GvComment comment2 = GvDataMocker.newComment(null);
        GvCommentList.GvComment comment3 = GvDataMocker.newComment(null);
        GvCommentList.GvComment[] references = {comment1, comment2, comment3};

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int[] referenceNums = {num1, num2, num3};
        int maxIter = Math.max(Math.max(num1, num2), num3);
        int total = num1 + num2 + num3;

        GvCommentList comments = new GvCommentList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                comments.add(new GvCommentList.GvComment(RandomReviewId.nextGvReviewId(), comment1
                        .getComment()));
            }
            if (size2++ < num2) {
                comments.add(new GvCommentList.GvComment(RandomReviewId.nextGvReviewId(), comment2
                        .getComment()));
            }
            if (size3++ < num3) {
                comments.add(new GvCommentList.GvComment(RandomReviewId.nextGvReviewId(), comment3
                        .getComment()));
            }
        }
        assertEquals(total, comments.size());

        GvCanonicalCollection<GvCommentList.GvComment> results = Aggregater.aggregate(comments);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvCommentList.GvComment> gvCanonical = results.getItem(i);
            GvCommentList.GvComment canonical = gvCanonical.getCanonical();
            GvCommentList values = (GvCommentList) gvCanonical.toList();
            GvCommentList.GvComment reference = references[i];
            assertEquals(reference.getComment(), canonical.getComment());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvCommentList.GvComment value = values.getItem(j);
                assertEquals(reference.getComment(), value.getComment());
            }
        }
    }

    @SmallTest
    public void testAggregateDate() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        Calendar cal5 = Calendar.getInstance();
        cal1.set(2015, 7, 6, 11, 30);
        cal2.set(2015, 7, 6, 17, 30);
        cal3.set(2015, 7, 16, 17, 30);
        cal4.set(2015, 1, 1, 11, 30);
        cal5.set(2013, 1, 1, 11, 30);
        Calendar[] refCals = {cal2, cal3, cal4, cal5};

        GvDateList.GvDate date1 = new GvDateList.GvDate(cal1.getTime());
        GvDateList.GvDate date2 = new GvDateList.GvDate(cal2.getTime());
        GvDateList.GvDate date3 = new GvDateList.GvDate(cal3.getTime());
        GvDateList.GvDate date4 = new GvDateList.GvDate(cal4.getTime());
        GvDateList.GvDate date5 = new GvDateList.GvDate(cal5.getTime());
        GvDateList.GvDate[] refDates = {date1, date2, date3, date4, date5};

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int num4 = randNum();
        int num5 = randNum();
        int[] refNums = {num1 + num2, num3, num4, num5};

        int maxIter = num1;
        int total = 0;
        for (int number : refNums) {
            if (number > maxIter) maxIter = number;
            total += number;
        }

        GvDateList dates = new GvDateList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        int size5 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                dates.add(new GvDateList.GvDate(RandomReviewId.nextGvReviewId(), date1
                        .getDate()));
            }
            if (size2++ < num2) {
                dates.add(new GvDateList.GvDate(RandomReviewId.nextGvReviewId(), date2
                        .getDate()));
            }
            if (size3++ < num3) {
                dates.add(new GvDateList.GvDate(RandomReviewId.nextGvReviewId(), date3
                        .getDate()));
            }
            if (size4++ < num4) {
                dates.add(new GvDateList.GvDate(RandomReviewId.nextGvReviewId(), date4
                        .getDate()));
            }
            if (size5++ < num5) {
                dates.add(new GvDateList.GvDate(RandomReviewId.nextGvReviewId(), date5
                        .getDate()));
            }
        }
        assertEquals(total, dates.size());

        GvCanonicalCollection<GvDateList.GvDate> results = Aggregater.aggregate(dates);
        assertEquals(refDates.length - 1, results.size());

        for (int i = 1; i < refDates.length; ++i) {
            GvCanonical<GvDateList.GvDate> gvCanonical = results.getItem(i - 1);
            GvDateList.GvDate canonical = gvCanonical.getCanonical();
            GvDateList values = (GvDateList) gvCanonical.toList();
            GvDateList.GvDate reference = refDates[i];
            assertEquals(reference.getDate(), canonical.getDate());
            assertEquals(refNums[i - 1], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvDateList.GvDate value = values.getItem(j);
                Calendar cal = Calendar.getInstance();
                cal.setTime(value.getDate());
                Calendar ref = refCals[i - 1];
                assertEquals(ref.get(Calendar.YEAR), cal.get(Calendar.YEAR));
                assertEquals(ref.get(Calendar.MONTH), cal.get(Calendar.MONTH));
                assertEquals(ref.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
            }
        }
    }

    @SmallTest
    public void testAggregateImage() {
        GvImageList.GvImage image1 = GvDataMocker.newImage(null);
        GvImageList.GvImage image2 = GvDataMocker.newImage(null);
        GvImageList.GvImage image3 = GvDataMocker.newImage(null);
        GvImageList.GvImage image4 = new GvImageList.GvImage(null, image3.getBitmap(),
                image1.getDate(), image2.getCaption(), image3.isCover());
        GvImageList.GvImage[] references = {image1, image2, image3, image4};

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int num4 = randNum();
        int[] refNums = {num1, num2, num3 + num4};

        int maxIter = num1;
        int total = 0;
        for (int number : refNums) {
            if (number > maxIter) maxIter = number;
            total += number;
        }

        GvImageList images = new GvImageList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                images.add(new GvImageList.GvImage(RandomReviewId.nextGvReviewId(), image1
                        .getBitmap(), image1.getDate(), image1.getCaption(), image1.isCover()));
            }
            if (size2++ < num2) {
                images.add(new GvImageList.GvImage(RandomReviewId.nextGvReviewId(), image2
                        .getBitmap(), image2.getDate(), image2.getCaption(), image2.isCover()));
            }
            if (size3++ < num3) {
                images.add(new GvImageList.GvImage(RandomReviewId.nextGvReviewId(), image3
                        .getBitmap(), image3.getDate(), image3.getCaption(), image3.isCover()));
            }
            if (size4++ < num4) {
                images.add(new GvImageList.GvImage(RandomReviewId.nextGvReviewId(), image4
                        .getBitmap(), image4.getDate(), image4.getCaption(), image4.isCover()));
            }
        }
        assertEquals(total, images.size());

        GvCanonicalCollection<GvImageList.GvImage> results = Aggregater.aggregate(images);
        assertEquals(references.length - 1, results.size());

        for (int i = 0; i < references.length - 1; ++i) {
            GvCanonical<GvImageList.GvImage> gvCanonical = results.getItem(i);
            GvImageList.GvImage canonical = gvCanonical.getCanonical();
            GvImageList values = (GvImageList) gvCanonical.toList();
            GvImageList.GvImage reference = references[i];
            assertEquals(reference.getBitmap(), canonical.getBitmap());
            assertEquals(refNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvImageList.GvImage value = values.getItem(j);
                assertEquals(reference.getBitmap(), value.getBitmap());
            }
        }
    }

    @SmallTest
    public void testAggregateLocation() {
        LatLng ll1 = new LatLng(51.517972, -0.063291);
        LatLng ll2 = new LatLng(51.518022, -0.063291);
        String name = "Tayyabs";
        GvLocationList.GvLocation location1 = new GvLocationList.GvLocation(ll1, name);
        GvLocationList.GvLocation location2 = new GvLocationList.GvLocation(ll2, name);
        GvLocationList.GvLocation location3 = new GvLocationList.GvLocation(RandomLatLng
                .nextLatLng(), name);
        GvLocationList.GvLocation location4 = new GvLocationList.GvLocation(ll1, RandomString
                .nextWord());

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int num4 = randNum();
        int[] refNums = {num1 + num2, num3, num4};

        int maxIter = num1;
        int total = 0;
        for (int number : refNums) {
            if (number > maxIter) maxIter = number;
            total += number;
        }

        GvLocationList locations = new GvLocationList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        LatLng[] lls = new LatLng[num1 + num2];
        int index = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                locations.add(new GvLocationList.GvLocation(RandomReviewId.nextGvReviewId(),
                        location1.getLatLng(), location1.getName()));
                lls[index++] = location1.getLatLng();
            }
            if (size2++ < num2) {
                locations.add(new GvLocationList.GvLocation(RandomReviewId.nextGvReviewId(),
                        location2.getLatLng(), location2.getName()));
                lls[index++] = location2.getLatLng();
            }
            if (size3++ < num3) {
                locations.add(new GvLocationList.GvLocation(RandomReviewId.nextGvReviewId(),
                        location3.getLatLng(), location3.getName()));
            }
            if (size4++ < num4) {
                locations.add(new GvLocationList.GvLocation(RandomReviewId.nextGvReviewId(),
                        location4.getLatLng(), location4.getName()));
            }
        }
        LatLngMidpoint mid = new LatLngMidpoint(lls);
        LatLng midpoint = mid.getGeoMidpoint();
        GvLocationList.GvLocation canon = new GvLocationList.GvLocation(midpoint, name);
        GvLocationList.GvLocation[] references = {canon, location3, location4};

        assertEquals(total, locations.size());

        GvCanonicalCollection<GvLocationList.GvLocation> results = Aggregater.aggregate(locations);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvLocationList.GvLocation> gvCanonical = results.getItem(i);
            GvLocationList.GvLocation canonical = gvCanonical.getCanonical();
            GvLocationList values = (GvLocationList) gvCanonical.toList();
            GvLocationList.GvLocation reference = references[i];
            assertEquals(reference.getLatLng().latitude, canonical.getLatLng().latitude, 0.0000001);
            assertEquals(reference.getLatLng().longitude, canonical.getLatLng().longitude,
                    0.0000001);
            assertEquals(reference.getName(), canonical.getName());
            assertEquals(refNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvLocationList.GvLocation value = values.getItem(j);
                assertEquals(reference.getName(), value.getName());
                float[] res = new float[1];
                LatLng llr = reference.getLatLng();
                LatLng llv = value.getLatLng();
                Location.distanceBetween(llr.latitude, llr.longitude, llv.latitude, llv
                        .longitude, res);
                assertTrue(res[0] < 10f);
            }
        }
    }

    @SmallTest
    public void testAggregateChildreview() {
        String subject = RandomString.nextWord();
        GvCriterionList.GvCriterion review1 =
                new GvCriterionList.GvCriterion(subject, RandomRating.nextRating());
        GvCriterionList.GvCriterion review2 =
                new GvCriterionList.GvCriterion(subject, RandomRating.nextRating());
        GvCriterionList.GvCriterion review3 = GvDataMocker.newChild(null);
        GvCriterionList.GvCriterion review4 = GvDataMocker.newChild(null);

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int num4 = randNum();
        int[] refNums = {num1 + num2, num3, num4};

        int maxIter = num1;
        int total = 0;
        for (int number : refNums) {
            if (number > maxIter) maxIter = number;
            total += number;
        }

        GvCriterionList reviews = new GvCriterionList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        float average = 0f;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                reviews.add(new GvCriterionList.GvCriterion(RandomReviewId
                        .nextGvReviewId(), review1.getSubject(), review1.getRating()));
                average += review1.getRating();
            }
            if (size2++ < num2) {
                reviews.add(new GvCriterionList.GvCriterion(RandomReviewId
                        .nextGvReviewId(), review2.getSubject(), review2.getRating()));
                average += review2.getRating();
            }
            if (size3++ < num3) {
                reviews.add(new GvCriterionList.GvCriterion(RandomReviewId
                        .nextGvReviewId(), review3.getSubject(), review3.getRating()));
            }
            if (size4++ < num4) {
                reviews.add(new GvCriterionList.GvCriterion(RandomReviewId
                        .nextGvReviewId(), review4.getSubject(), review4.getRating()));
            }
        }
        average /= num1 + num2;
        GvCriterionList.GvCriterion canon = new GvCriterionList.GvCriterion(subject,
                average);
        GvCriterionList.GvCriterion[] references = {canon, review3, review4};

        assertEquals(total, reviews.size());

        GvCanonicalCollection<GvCriterionList.GvCriterion> results = Aggregater.aggregate
                (reviews);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvCriterionList.GvCriterion> gvCanonical = results.getItem(i);
            GvCriterionList.GvCriterion canonical = gvCanonical.getCanonical();
            GvCriterionList values = (GvCriterionList) gvCanonical.toList();
            GvCriterionList.GvCriterion reference = references[i];
            assertEquals(reference.getSubject(), canonical.getSubject());
            assertEquals(reference.getRating(), canonical.getRating(), 0.0001);
            assertEquals(refNums[i], values.size());
            average = 0f;
            for (int j = 0; j < values.size(); ++j) {
                GvCriterionList.GvCriterion value = values.getItem(j);
                assertEquals(reference.getSubject(), value.getSubject());
                average += value.getRating();
            }
            assertEquals(reference.getRating(), average / values.size(), 0.0001);
        }
    }

    @SmallTest
    public void testAggregateFact() {
        String label = RandomString.nextWord();
        GvFactList.GvFact fact1 = new GvFactList.GvFact(label, RandomString.nextWord());
        GvFactList.GvFact fact2 = new GvFactList.GvFact(label, RandomString.nextWord());
        GvFactList.GvFact fact3 = GvDataMocker.newFact(null);
        GvFactList.GvFact fact4 = GvDataMocker.newFact(null);

        int num1 = randNum();
        int num2 = randNum();
        int num3 = randNum();
        int num4 = randNum();
        int[] refNums = {num1 + num2, num3, num4};

        int maxIter = num1;
        int total = 0;
        for (int number : refNums) {
            if (number > maxIter) maxIter = number;
            total += number;
        }

        GvFactList facts = new GvFactList();
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        for (int i = 0; i < maxIter; ++i) {
            if (size1++ < num1) {
                facts.add(new GvFactList.GvFact(RandomReviewId.nextGvReviewId(), fact1.getLabel(),
                        fact1.getValue()));
            }
            if (size2++ < num2) {
                facts.add(new GvFactList.GvFact(RandomReviewId.nextGvReviewId(), fact2.getLabel(),
                        fact2.getValue()));
            }
            if (size3++ < num3) {
                facts.add(new GvFactList.GvFact(RandomReviewId.nextGvReviewId(), fact3.getLabel(),
                        fact3.getValue()));
            }
            if (size4++ < num4) {
                facts.add(new GvFactList.GvFact(RandomReviewId.nextGvReviewId(), fact4.getLabel(),
                        fact4.getValue()));
            }
        }
        GvFactList.GvFact canon = new GvFactList.GvFact(label, "2 values");
        GvFactList.GvFact[] references = {canon, fact3, fact4};

        assertEquals(total, facts.size());

        GvCanonicalCollection<GvFactList.GvFact> results = Aggregater.aggregate(facts);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvFactList.GvFact> gvCanonical = results.getItem(i);
            GvFactList.GvFact canonical = gvCanonical.getCanonical();
            GvFactList values = (GvFactList) gvCanonical.toList();
            GvFactList.GvFact reference = references[i];
            assertEquals(reference.getLabel(), canonical.getLabel());
            assertEquals(reference.getValue(), canonical.getValue());
            assertEquals(refNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvFactList.GvFact value = values.getItem(j);
                assertEquals(reference.getLabel(), value.getLabel());
            }
        }
    }

    private int randNum() {
        return 10 + RAND.nextInt(20);
    }
}

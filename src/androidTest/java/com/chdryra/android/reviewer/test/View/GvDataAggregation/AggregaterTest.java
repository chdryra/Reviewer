package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.location.Location;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthorList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterionList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDateList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFactList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocationList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubjectList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTagList;
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
        GvAuthor author1 = GvDataMocker.newAuthor(null);
        GvAuthor author2 = GvDataMocker.newAuthor(null);
        GvAuthor author3 = GvDataMocker.newAuthor(null);
        GvAuthor[] references = {author1, author2, author3};

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
                authors.add(new GvAuthor(RandomReviewId.nextGvReviewId(), author1
                        .getName(), author1.getUserId()));
            }
            if (size2++ < num2) {
                authors.add(new GvAuthor(RandomReviewId.nextGvReviewId(), author2
                        .getName(), author2.getUserId()));
            }
            if (size3++ < num3) {
                authors.add(new GvAuthor(RandomReviewId.nextGvReviewId(), author3
                        .getName(), author3.getUserId()));
            }
        }
        assertEquals(total, authors.size());

        GvCanonicalCollection<GvAuthor> results = Aggregater.aggregate(authors);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvAuthor> gvCanonical = results.getItem(i);
            GvAuthor canonical = gvCanonical.getCanonical();
            GvAuthorList values = (GvAuthorList) gvCanonical.toList();
            GvAuthor reference = references[i];
            assertEquals(reference.getName(), canonical.getName());
            assertEquals(reference.getUserId(), canonical.getUserId());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvAuthor value = values.getItem(j);
                assertEquals(reference.getName(), value.getName());
                assertEquals(reference.getUserId(), value.getUserId());
            }
        }
    }

    @SmallTest
    public void testAggregateSubject() {
        GvSubject subject1 = GvDataMocker.newSubject(null);
        GvSubject subject2 = GvDataMocker.newSubject(null);
        GvSubject subject3 = GvDataMocker.newSubject(null);
        GvSubject[] references = {subject1, subject2, subject3};

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
                subjects.add(new GvSubject(RandomReviewId.nextGvReviewId(), subject1
                        .getString()));
            }
            if (size2++ < num2) {
                subjects.add(new GvSubject(RandomReviewId.nextGvReviewId(), subject2
                        .getString()));
            }
            if (size3++ < num3) {
                subjects.add(new GvSubject(RandomReviewId.nextGvReviewId(), subject3
                        .getString()));
            }
        }
        assertEquals(total, subjects.size());

        GvCanonicalCollection<GvSubject> results = Aggregater.aggregate(subjects);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvSubject> gvCanonical = results.getItem(i);
            GvSubjectList values = (GvSubjectList) gvCanonical.toList();
            GvSubject reference = references[i];
            GvSubject canonical = gvCanonical.getCanonical();
            assertEquals(reference.getString(), canonical.getString());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvSubject value = values.getItem(j);
                assertEquals(reference.getString(), value.getString());
            }
        }
    }

    @SmallTest
    public void testAggregateTag() {
        GvTag tag1 = GvDataMocker.newTag(null);
        GvTag tag2 = GvDataMocker.newTag(null);
        GvTag tag3 = GvDataMocker.newTag(null);
        GvTag[] references = {tag1, tag2, tag3};

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
                tags.add(new GvTag(RandomReviewId.nextGvReviewId(), tag1
                        .getString()));
            }
            if (size2++ < num2) {
                tags.add(new GvTag(RandomReviewId.nextGvReviewId(), tag2
                        .getString()));
            }
            if (size3++ < num3) {
                tags.add(new GvTag(RandomReviewId.nextGvReviewId(), tag3
                        .getString()));
            }
        }
        assertEquals(total, tags.size());

        GvCanonicalCollection<GvTag> results = Aggregater.aggregate(tags);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvTag> gvCanonical = results.getItem(i);
            GvTag canonical = gvCanonical.getCanonical();
            GvTagList values = (GvTagList) gvCanonical.toList();
            GvTag reference = references[i];
            assertEquals(reference.getString(), canonical.getString());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvTag value = values.getItem(j);
                assertEquals(reference.getString(), value.getString());
            }
        }
    }

    @SmallTest
    public void testAggregateComment() {
        GvComment comment1 = GvDataMocker.newComment(null);
        GvComment comment2 = GvDataMocker.newComment(null);
        GvComment comment3 = GvDataMocker.newComment(null);
        GvComment[] references = {comment1, comment2, comment3};

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
                comments.add(new GvComment(RandomReviewId.nextGvReviewId(), comment1
                        .getComment()));
            }
            if (size2++ < num2) {
                comments.add(new GvComment(RandomReviewId.nextGvReviewId(), comment2
                        .getComment()));
            }
            if (size3++ < num3) {
                comments.add(new GvComment(RandomReviewId.nextGvReviewId(), comment3
                        .getComment()));
            }
        }
        assertEquals(total, comments.size());

        GvCanonicalCollection<GvComment> results = Aggregater.aggregate(comments);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvComment> gvCanonical = results.getItem(i);
            GvComment canonical = gvCanonical.getCanonical();
            GvCommentList values = (GvCommentList) gvCanonical.toList();
            GvComment reference = references[i];
            assertEquals(reference.getComment(), canonical.getComment());
            assertEquals(referenceNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvComment value = values.getItem(j);
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

        GvDate date1 = new GvDate(cal1.getTime());
        GvDate date2 = new GvDate(cal2.getTime());
        GvDate date3 = new GvDate(cal3.getTime());
        GvDate date4 = new GvDate(cal4.getTime());
        GvDate date5 = new GvDate(cal5.getTime());
        GvDate[] refDates = {date1, date2, date3, date4, date5};

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
                dates.add(new GvDate(RandomReviewId.nextGvReviewId(), date1
                        .getDate()));
            }
            if (size2++ < num2) {
                dates.add(new GvDate(RandomReviewId.nextGvReviewId(), date2
                        .getDate()));
            }
            if (size3++ < num3) {
                dates.add(new GvDate(RandomReviewId.nextGvReviewId(), date3
                        .getDate()));
            }
            if (size4++ < num4) {
                dates.add(new GvDate(RandomReviewId.nextGvReviewId(), date4
                        .getDate()));
            }
            if (size5++ < num5) {
                dates.add(new GvDate(RandomReviewId.nextGvReviewId(), date5
                        .getDate()));
            }
        }
        assertEquals(total, dates.size());

        GvCanonicalCollection<GvDate> results = Aggregater.aggregate(dates);
        assertEquals(refDates.length - 1, results.size());

        for (int i = 1; i < refDates.length; ++i) {
            GvCanonical<GvDate> gvCanonical = results.getItem(i - 1);
            GvDate canonical = gvCanonical.getCanonical();
            GvDateList values = (GvDateList) gvCanonical.toList();
            GvDate reference = refDates[i];
            assertEquals(reference.getDate(), canonical.getDate());
            assertEquals(refNums[i - 1], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvDate value = values.getItem(j);
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
        GvImage image1 = GvDataMocker.newImage(null);
        GvImage image2 = GvDataMocker.newImage(null);
        GvImage image3 = GvDataMocker.newImage(null);
        GvImage image4 = new GvImage(null, image3.getBitmap(),
                image1.getDate(), image2.getCaption(), image3.isCover());
        GvImage[] references = {image1, image2, image3, image4};

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
                images.add(new GvImage(RandomReviewId.nextGvReviewId(), image1
                        .getBitmap(), image1.getDate(), image1.getCaption(), image1.isCover()));
            }
            if (size2++ < num2) {
                images.add(new GvImage(RandomReviewId.nextGvReviewId(), image2
                        .getBitmap(), image2.getDate(), image2.getCaption(), image2.isCover()));
            }
            if (size3++ < num3) {
                images.add(new GvImage(RandomReviewId.nextGvReviewId(), image3
                        .getBitmap(), image3.getDate(), image3.getCaption(), image3.isCover()));
            }
            if (size4++ < num4) {
                images.add(new GvImage(RandomReviewId.nextGvReviewId(), image4
                        .getBitmap(), image4.getDate(), image4.getCaption(), image4.isCover()));
            }
        }
        assertEquals(total, images.size());

        GvCanonicalCollection<GvImage> results = Aggregater.aggregate(images);
        assertEquals(references.length - 1, results.size());

        for (int i = 0; i < references.length - 1; ++i) {
            GvCanonical<GvImage> gvCanonical = results.getItem(i);
            GvImage canonical = gvCanonical.getCanonical();
            GvImageList values = (GvImageList) gvCanonical.toList();
            GvImage reference = references[i];
            assertEquals(reference.getBitmap(), canonical.getBitmap());
            assertEquals(refNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvImage value = values.getItem(j);
                assertEquals(reference.getBitmap(), value.getBitmap());
            }
        }
    }

    @SmallTest
    public void testAggregateLocation() {
        LatLng ll1 = new LatLng(51.517972, -0.063291);
        LatLng ll2 = new LatLng(51.518022, -0.063291);
        String name = "Tayyabs";
        GvLocation location1 = new GvLocation(ll1, name);
        GvLocation location2 = new GvLocation(ll2, name);
        GvLocation location3 = new GvLocation(RandomLatLng
                .nextLatLng(), name);
        GvLocation location4 = new GvLocation(ll1, RandomString
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
                locations.add(new GvLocation(RandomReviewId.nextGvReviewId(),
                        location1.getLatLng(), location1.getName()));
                lls[index++] = location1.getLatLng();
            }
            if (size2++ < num2) {
                locations.add(new GvLocation(RandomReviewId.nextGvReviewId(),
                        location2.getLatLng(), location2.getName()));
                lls[index++] = location2.getLatLng();
            }
            if (size3++ < num3) {
                locations.add(new GvLocation(RandomReviewId.nextGvReviewId(),
                        location3.getLatLng(), location3.getName()));
            }
            if (size4++ < num4) {
                locations.add(new GvLocation(RandomReviewId.nextGvReviewId(),
                        location4.getLatLng(), location4.getName()));
            }
        }
        LatLngMidpoint mid = new LatLngMidpoint(lls);
        LatLng midpoint = mid.getGeoMidpoint();
        GvLocation canon = new GvLocation(midpoint, name);
        GvLocation[] references = {canon, location3, location4};

        assertEquals(total, locations.size());

        GvCanonicalCollection<GvLocation> results = Aggregater.aggregate(locations);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvLocation> gvCanonical = results.getItem(i);
            GvLocation canonical = gvCanonical.getCanonical();
            GvLocationList values = (GvLocationList) gvCanonical.toList();
            GvLocation reference = references[i];
            assertEquals(reference.getLatLng().latitude, canonical.getLatLng().latitude, 0.0000001);
            assertEquals(reference.getLatLng().longitude, canonical.getLatLng().longitude,
                    0.0000001);
            assertEquals(reference.getName(), canonical.getName());
            assertEquals(refNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvLocation value = values.getItem(j);
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
        GvCriterion review1 =
                new GvCriterion(subject, RandomRating.nextRating());
        GvCriterion review2 =
                new GvCriterion(subject, RandomRating.nextRating());
        GvCriterion review3 = GvDataMocker.newChild(null);
        GvCriterion review4 = GvDataMocker.newChild(null);

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
                reviews.add(new GvCriterion(RandomReviewId
                        .nextGvReviewId(), review1.getSubject(), review1.getRating()));
                average += review1.getRating();
            }
            if (size2++ < num2) {
                reviews.add(new GvCriterion(RandomReviewId
                        .nextGvReviewId(), review2.getSubject(), review2.getRating()));
                average += review2.getRating();
            }
            if (size3++ < num3) {
                reviews.add(new GvCriterion(RandomReviewId
                        .nextGvReviewId(), review3.getSubject(), review3.getRating()));
            }
            if (size4++ < num4) {
                reviews.add(new GvCriterion(RandomReviewId
                        .nextGvReviewId(), review4.getSubject(), review4.getRating()));
            }
        }
        average /= num1 + num2;
        GvCriterion canon = new GvCriterion(subject,
                average);
        GvCriterion[] references = {canon, review3, review4};

        assertEquals(total, reviews.size());

        GvCanonicalCollection<GvCriterion> results = Aggregater.aggregate
                (reviews);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvCriterion> gvCanonical = results.getItem(i);
            GvCriterion canonical = gvCanonical.getCanonical();
            GvCriterionList values = (GvCriterionList) gvCanonical.toList();
            GvCriterion reference = references[i];
            assertEquals(reference.getSubject(), canonical.getSubject());
            assertEquals(reference.getRating(), canonical.getRating(), 0.0001);
            assertEquals(refNums[i], values.size());
            average = 0f;
            for (int j = 0; j < values.size(); ++j) {
                GvCriterion value = values.getItem(j);
                assertEquals(reference.getSubject(), value.getSubject());
                average += value.getRating();
            }
            assertEquals(reference.getRating(), average / values.size(), 0.0001);
        }
    }

    @SmallTest
    public void testAggregateFact() {
        String label = RandomString.nextWord();
        GvFact fact1 = new GvFact(label, RandomString.nextWord());
        GvFact fact2 = new GvFact(label, RandomString.nextWord());
        GvFact fact3 = GvDataMocker.newFact(null);
        GvFact fact4 = GvDataMocker.newFact(null);

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
                facts.add(new GvFact(RandomReviewId.nextGvReviewId(), fact1.getLabel(),
                        fact1.getValue()));
            }
            if (size2++ < num2) {
                facts.add(new GvFact(RandomReviewId.nextGvReviewId(), fact2.getLabel(),
                        fact2.getValue()));
            }
            if (size3++ < num3) {
                facts.add(new GvFact(RandomReviewId.nextGvReviewId(), fact3.getLabel(),
                        fact3.getValue()));
            }
            if (size4++ < num4) {
                facts.add(new GvFact(RandomReviewId.nextGvReviewId(), fact4.getLabel(),
                        fact4.getValue()));
            }
        }
        GvFact canon = new GvFact(label, "2 values");
        GvFact[] references = {canon, fact3, fact4};

        assertEquals(total, facts.size());

        GvCanonicalCollection<GvFact> results = Aggregater.aggregate(facts);
        assertEquals(references.length, results.size());

        for (int i = 0; i < references.length; ++i) {
            GvCanonical<GvFact> gvCanonical = results.getItem(i);
            GvFact canonical = gvCanonical.getCanonical();
            GvFactList values = (GvFactList) gvCanonical.toList();
            GvFact reference = references[i];
            assertEquals(reference.getLabel(), canonical.getLabel());
            assertEquals(reference.getValue(), canonical.getValue());
            assertEquals(refNums[i], values.size());
            for (int j = 0; j < values.size(); ++j) {
                GvFact value = values.getItem(j);
                assertEquals(reference.getLabel(), value.getLabel());
            }
        }
    }

    private int randNum() {
        return 10 + RAND.nextInt(20);
    }
}

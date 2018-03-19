/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation.DateComparator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation.ImageCoversThenMostRecent;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageCoversThenMostRecentTest extends ComparatorTest<DataImage> {
    private static final Random RAND = new Random();

    public ImageCoversThenMostRecentTest() {
        super(new ImageCoversThenMostRecent(new DateComparator()));
    }

    @Test
    public void coversFirstTheNDate() {
        Date d1 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();
        Date d2 = new GregorianCalendar(2015, 7, 25, 19, 30).getTime();
        Date d3 = new GregorianCalendar(2015, 8, 25, 19, 30).getTime();

        DateTime dateOld = new DatumDate(RandomReviewId.nextReviewId(), d1.getTime());
        DateTime dateMid = new DatumDate(RandomReviewId.nextReviewId(), d2.getTime());
        DateTime dateNew = new DatumDate(RandomReviewId.nextReviewId(), d3.getTime());

        DataImage imageNewIsCover = new DatumImage(RandomReviewId.nextReviewId(), null, dateNew,
                RandomString.nextSentence(), true);
        DataImage imageMidIsCover = new DatumImage(RandomReviewId.nextReviewId(), null, dateMid,
                RandomString.nextSentence(), true);
        DataImage imageOldIsCover = new DatumImage(RandomReviewId.nextReviewId(), null, dateOld,
                RandomString.nextSentence(), true);
        DataImage imageNewIsNotCover = new DatumImage(RandomReviewId.nextReviewId(), null,
                dateNew, RandomString.nextSentence(), false);
        DataImage imageMidIsNotCover = new DatumImage(RandomReviewId.nextReviewId(), null,
                dateMid, RandomString.nextSentence(), false);
        DataImage imageOldIsNotCover = new DatumImage(RandomReviewId.nextReviewId(), null,
                dateOld, RandomString.nextSentence(), false);

        ComparatorTester<DataImage> tester = newComparatorTester();
        tester.testFirstSecond(imageNewIsCover, imageMidIsCover);
        tester.testFirstSecond(imageMidIsCover, imageOldIsCover);
        tester.testFirstSecond(imageOldIsCover, imageNewIsNotCover);
        tester.testFirstSecond(imageNewIsNotCover, imageMidIsNotCover);
        tester.testFirstSecond(imageMidIsNotCover, imageOldIsNotCover);
    }

    @Test
    public void coversFirst_SameDate() {
        DateTime date = new DatumDate(RandomReviewId.nextReviewId(), new Date().getTime());

        DataImage imageIsCover = new DatumImage(RandomReviewId.nextReviewId(), null, date,
                RandomString.nextSentence(), true);
        DataImage imageIsNotCover = new DatumImage(RandomReviewId.nextReviewId(), null, date,
                RandomString.nextSentence(), false);

        ComparatorTester<DataImage> tester = newComparatorTester();

        tester.testFirstSecond(imageIsCover, imageIsNotCover);
    }

    @Test
    public void mostRecentFirst_SameIsCover() {
        Date d1 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();
        Date d2 = new GregorianCalendar(2015, 7, 25, 19, 30).getTime();

        DateTime dateOld = new DatumDate(RandomReviewId.nextReviewId(), d1.getTime());
        DateTime dateNew = new DatumDate(RandomReviewId.nextReviewId(), d2.getTime());

        boolean isCover = RAND.nextBoolean();
        DataImage imageOld = new DatumImage(RandomReviewId.nextReviewId(), null, dateOld,
                RandomString.nextSentence(), isCover);
        DataImage imageNew = new DatumImage(RandomReviewId.nextReviewId(), null, dateNew,
                RandomString.nextSentence(), isCover);

        ComparatorTester<DataImage> tester = newComparatorTester();
        tester.testFirstSecond(imageNew, imageOld);
    }

    @Test
    public void comparatorEqualitySameIsCoverSameDate() {
        DateTime date = new DatumDate(RandomReviewId.nextReviewId(), new Date().getTime());
        boolean isCover = RAND.nextBoolean();

        DataImage image1 = new DatumImage(RandomReviewId.nextReviewId(), null, date, RandomString
                .nextSentence(), isCover);
        DataImage image2 = new DatumImage(RandomReviewId.nextReviewId(), null, date, RandomString
                .nextSentence(), isCover);

        ComparatorTester<DataImage> tester = newComparatorTester();
        tester.testEquals(image1, image1);
        tester.testEquals(image1, image2);
    }
}

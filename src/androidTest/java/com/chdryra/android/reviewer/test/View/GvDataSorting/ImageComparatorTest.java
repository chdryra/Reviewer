package com.chdryra.android.reviewer.test.View.GvDataSorting;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataSorting.ImageComparators;
import com.chdryra.android.reviewer.test.TestUtils.ComparatorTester;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageComparatorTest extends ComparatorCollectionTest<GvImageList.GvImage> {
    //Constructors
    public ImageComparatorTest() {
        super(ImageComparators.getComparators());
    }

    @SmallTest
    public void testDefaultComparators() {
        Comparator<GvImageList.GvImage> comparator = mComparators.getDefault();

        Date date1 = new GregorianCalendar(2015, 2, 25, 19, 30).getTime();
        Date date2 = new GregorianCalendar(2015, 7, 25, 19, 30).getTime();

        Bitmap bitmap = BitmapMocker.nextBitmap();
        Bitmap bitmap2 = BitmapMocker.nextBitmap();
        LatLng latLng = RandomLatLng.nextLatLng();
        LatLng latLng2 = RandomLatLng.nextLatLng();
        String caption = RandomString.nextSentence();
        String caption2 = RandomString.nextSentence();

        GvImageList.GvImage image1 = new GvImageList.GvImage(bitmap, date1, latLng, caption, false);
        GvImageList.GvImage image12 = new GvImageList.GvImage(bitmap, date1, latLng, caption,
                false);
        GvImageList.GvImage image13 = new GvImageList.GvImage(bitmap2, date1, latLng2, caption2,
                false);
        GvImageList.GvImage image2 = new GvImageList.GvImage(bitmap, date1, latLng, caption, true);
        GvImageList.GvImage image3 = new GvImageList.GvImage(bitmap, date2, latLng, caption, false);

        ComparatorTester<GvImageList.GvImage> tester = new ComparatorTester<>(comparator);
        tester.testEquals(image1, image1);
        tester.testEquals(image1, image12);
        tester.testEquals(image1, image13);
        tester.testFirstSecond(image2, image1);
        tester.testFirstSecond(image2, image3);
        tester.testFirstSecond(image3, image1);
    }
}

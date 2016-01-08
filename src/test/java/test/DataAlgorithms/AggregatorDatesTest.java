package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import test.TestUtils.RandomDataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorDatesTest extends AggregatedDistinctItemsTest<DataDateReview>{
    @NonNull
    @Override
    protected DataAggregator<DataDateReview> newAggregator(FactoryDataAggregator factory) {
        return factory.newDatesAggregator();
    }

    @Override
    @NonNull
    protected DataDateReview newSimilarDatum(ReviewId reviewId, DataDateReview template) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(template.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 1 + getRAND().nextInt(20));
        cal.set(Calendar.MINUTE, 1 + getRAND().nextInt(50));
        Date date = cal.getTime();
        return new DatumDateReview(reviewId, date.getTime());
    }

    @Override
    @NonNull
    protected DataDateReview randomDatum() {
        return RandomDataDate.nextDateReview();
    }

    @Override
    protected DataDateReview getExampleCanonical(ReviewId id, ArrayList<DataDateReview> data) {
        ArrayList<DataDateReview> copy = new ArrayList<>(data);
        Collections.sort(copy, new Comparator<DataDateReview>() {
            @Override
            public int compare(DataDateReview lhs, DataDateReview rhs) {
                if(lhs.getTime() > rhs.getTime()) return -1;
                if(lhs.getTime() < rhs.getTime()) return 1;
                return 0;
            }
        });
        return new DatumDateReview(id, copy.get(0).getTime());
    }


}

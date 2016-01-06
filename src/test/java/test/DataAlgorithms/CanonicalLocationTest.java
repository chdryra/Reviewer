package test.DataAlgorithms;

import com.chdryra.android.mygenerallibrary.LatLngMidpoint;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalStringMaker;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.testutils.RandomLatLng;
import com.google.android.gms.maps.model.LatLng;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalLocationTest extends CanonicalStringMakerTest<DataLocation>{
    public CanonicalLocationTest() {
        this(new CanonicalLocation());
    }

    protected CanonicalLocationTest(CanonicalStringMaker<DataLocation> canonical) {
        super(canonical);
    }

    @Override
    protected void checkValidForMultipleAggregated(DataLocation canonical) {
        String subject = canonical.getName();
        assertThat(subject, is(getModeString() + " + " + String.valueOf(getNumDifferent())));
        assertThat(canonical.getLatLng(), is(getExpectedLatLng()));
    }

    @Override
    protected void checkValidForSingleAggregated(DataLocation canonical) {
        assertThat(canonical.getName(), is(getModeString()));
        assertThat(canonical.getLatLng(), is(getExpectedLatLng()));
    }

    @Override
    protected void checkInvalid(DataLocation canonical) {
        assertThat(canonical.getName(), is(""));
        assertThat(canonical.getLatLng(), is(nullValue()));
    }

    @Override
    protected DataLocation newDatum(String string) {
        return new DatumLocation(RandomReviewId.nextReviewId(), RandomLatLng.nextLatLng(), string);
    }

    protected LatLng getExpectedLatLng() {
        IdableList<DataLocation> data = getData();
        LatLng[] latLngs = new LatLng[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            latLngs[i] = data.getItem(i).getLatLng();
        }

        LatLngMidpoint midpoint = new LatLngMidpoint(latLngs);
        return midpoint.getGeoMidpoint();
    }
}

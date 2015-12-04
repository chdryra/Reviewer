package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import android.content.Intent;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}

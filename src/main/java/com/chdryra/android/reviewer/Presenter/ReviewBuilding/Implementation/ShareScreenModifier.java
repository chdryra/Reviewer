package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFeed;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenModifier implements ReviewViewPerspective.ReviewViewModifier {
    //Overridden
    @Override
    public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = parent.getActivity();
        parent.setBannerNotClickable();
        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
        Button publishButton = (Button) inflater.inflate(R.layout.review_banner_button,
                container, false);
        publishButton.setText(activity.getResources().getString(R.string.button_publish));
        publishButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        publishButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                ApplicationInstance.getInstance(activity).publishReviewBuilder();
                Intent intent = new Intent(activity, ActivityFeed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.startActivity(intent);
            }
        });

        parent.addView(publishButton);
        parent.addView(divider);

        return v;
    }
}

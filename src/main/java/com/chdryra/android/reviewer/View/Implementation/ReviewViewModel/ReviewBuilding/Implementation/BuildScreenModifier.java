package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Activities.ActivityShareReview;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenModifier implements ReviewViewPerspective.ReviewViewModifier {

    private void requestShareIntent(FragmentReviewView parent) {
        Activity activity = parent.getActivity();

        if (parent.getSubject().length() == 0) {
            Toast.makeText(activity, R.string.toast_enter_subject, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(activity, ActivityShareReview.class);
        activity.startActivity(i);
    }

    //Overridden
    @Override
    public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState) {


        parent.setBannerNotClickable();

        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);

        Button shareButton = (Button) inflater.inflate(R.layout.review_banner_button, container,
                false);
        String title = parent.getActivity().getResources().getString(R.string.button_share);
        shareButton.setText(title);
        shareButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        shareButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                requestShareIntent(parent);
            }
        });

        parent.addView(shareButton);
        parent.addView(divider);

        return v;
    }
}

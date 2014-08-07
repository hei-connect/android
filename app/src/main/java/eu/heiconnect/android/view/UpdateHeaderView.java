package eu.heiconnect.android.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import eu.heiconnect.android.R;
import eu.heiconnect.android.utils.DateUtils;
import eu.heiconnect.android.utils.FormatUtils;

public class UpdateHeaderView extends LinearLayout {

    private View cardView;
    private View contentView;
    private TextView syncTextView;
    private ImageView iconImageView;

    // ----------------------------------
    // CONSTRUCTORS
    // ----------------------------------
    public UpdateHeaderView(Context context) {
        super(context);
        initializeView(context);
    }

    public UpdateHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }


    public UpdateHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    // ----------------------------------
    // PUBLIC METHODS
    // ----------------------------------
    public void bindData(Date lastSync) {
        int colorTo;
        if (DateUtils.getDifferenceInHours(lastSync, new Date()) >= 4) {
            colorTo = getResources().getColor(R.color.warning);
            iconImageView.setImageResource(R.drawable.ic_warning_150);
        } else {
            colorTo = getResources().getColor(R.color.success);
            iconImageView.setImageResource(R.drawable.ic_done_150);
        }

        syncTextView.setText(FormatUtils.getUserFriendlyElapsedTimeSinceDate(lastSync, getContext()));

        // Prepare animation of cardView background color
        int colorFrom = Color.TRANSPARENT;
        Drawable background = cardView.getBackground();
        if (background instanceof ColorDrawable) {
            colorFrom = ((ColorDrawable) background).getColor();
        }
        if (colorFrom != colorTo) {
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    cardView.setBackgroundColor((Integer) animator.getAnimatedValue());
                }
            });
            colorAnimation.start();
        }

        // Prepare animation of content alpha
        if (contentView.getAlpha() < 1) {
            contentView.animate().alpha(1);
            iconImageView.animate().alpha(1);
        }
    }


    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.headerview_update, this);
        cardView = findViewById(R.id.viewgroup_update_card);
        contentView = findViewById(R.id.viewgroup_update_content);
        iconImageView = (ImageView) findViewById(R.id.imageview_update_icon);
        syncTextView = (TextView) findViewById(R.id.textview_update_last_sync);
    }
}

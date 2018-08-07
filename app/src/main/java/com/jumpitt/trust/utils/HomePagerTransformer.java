package com.jumpitt.trust.utils;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jumpitt.trust.ui.home.viewpager.HomePagerAdapter;

/**
 * Created by felipe on 08-05-18.
 */

public class HomePagerTransformer implements ViewPager.PageTransformer {
    private float actionBarHeight;

    public HomePagerTransformer(float actionBarHeight) {
        this.actionBarHeight = actionBarHeight;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageTag = (int) page.getTag();

        if (pageTag == HomePagerAdapter.FRAGMENT_EVENTS) {
            if (position <= 0) {
                page.setTranslationY(-actionBarHeight * position);
            }
        }
        if (pageTag == HomePagerAdapter.FRAGMENT_HISTORY) {
            if (position >= 0) {
                page.setTranslationY(-actionBarHeight * position);
            } else {
                page.setTranslationY(actionBarHeight * position);
            }

        }
        if (pageTag == HomePagerAdapter.FRAGMENT_DYNAMIC) {
            if (position >= 0) {
                page.setTranslationY(actionBarHeight * position);
            }
        }

        /*if (pageTag == HomePagerAdapter.FRAGMENT_TRANSACTION) {
            RecyclerView myRecyclerView = page.findViewById(R.id.recycler_view);
            int count = myRecyclerView.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = myRecyclerView.getChildAt(i);
                if (child instanceof ConstraintLayout) {
                    View image = child.findViewById(R.id.imageView3);
                    View text = child.findViewById(R.id.textView7);
                    image.setTranslationX(position * (page.getWidth() * 0.2f));
                    text.setTranslationX(position * (page.getWidth() * 0.4f));
                } else {
                    myRecyclerView.getChildAt(i).setTranslationX(position * (page.getWidth() * ((0.2f) + 0.2f * i)));
                }
            }


        }

        if (pageTag == HomePagerAdapter.FRAGMENT_KEY) {
            if (position <= 0) {
                page.setTranslationY(-position * (actionBarHeight * 1.5f));
                page.findViewById(R.id.recycler_view).scrollBy((int) position, (int) position);

            }
            if (position > 0) {
                page.setTranslationY(0);
            }
            RecyclerView myRecyclerView = page.findViewById(R.id.recycler_view);
            int count = myRecyclerView.getChildCount();
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    View child = myRecyclerView.getChildAt(i);
                    if (child instanceof RecyclerView) {
                        RecyclerView horizontalRV = (RecyclerView) child;
                        horizontalRV.scrollBy((int) ((-position) * 200), 0);
                    }
                }
                myRecyclerView.getChildAt(i).setTranslationX(position * (page.getWidth() * ((0.2f) + 0.2f * i)));
            }


        }

        if (pageTag == HomePagerAdapter.FRAGMENT_DATA) {
            View appbar = page.findViewById(R.id.appbar);
            View card = page.findViewById(R.id.cedula_card);
            View cardProfile = page.findViewById(R.id.card_profile_);
            View cardPref = page.findViewById(R.id.card_settings_);
            View card_help_ = page.findViewById(R.id.card_help_);
            if (position >= 0) {
                appbar.setScaleY(4 - 3 * position);
                card.setTranslationX(position * (page.getWidth() * 0.2f));
                cardProfile.setTranslationX(position * (page.getWidth() * 0.4f));
                cardPref.setTranslationX(position * (page.getWidth() * 0.6f));
                card_help_.setTranslationX(position * (page.getWidth() * 0.8f));

            }
        }*/
    }
}

package com.StepUP.madadgaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.activities.SubCategoryActivity;
import com.StepUP.madadgaar.activities.TechnicalActivity;

public class CategoryAdapter extends PagerAdapter {

    private Context context;
    private int[] layouts = {
            R.layout.layout_category_soft,
            R.layout.layout_category_tecnical
    };

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return false;
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
//        return super.instantiateItem(container, position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(layouts[position], container, false);
        view.setTag(position);

        if (position == 1){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.right_in_bounce);
            view.findViewById(R.id.right_arrow).startAnimation(animation);
        }
        if (position == 0){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_in_bounce);
            view.findViewById(R.id.arrow_left).startAnimation(animation);
        }

        view.findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==1){
                    Intent intent = new Intent(context, TechnicalActivity.class);
                    intent.putExtra("selected_category",R.color.blue_dark);
                    intent.putExtra("category","technical_skills");
                    context.startActivity(intent);

                }
                else {

                    Intent intent = new Intent(context,SubCategoryActivity.class);
                    intent.putExtra("selected_category",R.color.yallow_parent);
                    intent.putExtra("category","soft_skills");
                    context.startActivity(intent);
                }
            }
        });

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((ConstraintLayout) object);
    }
}

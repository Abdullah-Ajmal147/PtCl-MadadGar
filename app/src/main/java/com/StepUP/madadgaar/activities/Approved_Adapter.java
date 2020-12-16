package com.StepUP.madadgaar.activities;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Approved_Adapter extends RecyclerView.Adapter<Approved_Adapter.MyView> {


    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyView extends RecyclerView.ViewHolder{

        public MyView(@NonNull View itemView) {
            super(itemView);
        }
    }
}

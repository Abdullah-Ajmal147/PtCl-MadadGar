package com.stepup.madadgaar.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.stepup.madadgaar.R;

public class SubCategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTopicTitle,txtnumberIncrease,checkbox;
    public ImageView Button_nav;
   public DrawerLayout mdrawerlayout;
   public TextView txthome,aboutUs,ContacUs,Logout;

    public SubCategoryViewHolder(View itemView) {
        super(itemView);
        txtTopicTitle=(TextView)itemView.findViewById(R.id.sub_Category_title);
        txtnumberIncrease=(TextView)itemView.findViewById(R.id.txtNumberIncrease);
        checkbox=(TextView)itemView.findViewById(R.id.checkbox);

        Button_nav=(ImageView)itemView.findViewById(R.id.Button_nav);
        txthome=(TextView)itemView.findViewById(R.id.home);
        aboutUs=(TextView)itemView.findViewById(R.id.about);
        Logout=(TextView)itemView.findViewById(R.id.logout);
        ContacUs=(TextView)itemView.findViewById(R.id.Contact);
        mdrawerlayout=(DrawerLayout)itemView.findViewById(R.id.Drawer);


    }
}

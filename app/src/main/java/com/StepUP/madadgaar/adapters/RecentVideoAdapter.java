package com.StepUP.madadgaar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.StepUP.madadgaar.R;
import com.StepUP.madadgaar.models.YoutubeData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentVideoAdapter extends RecyclerView.Adapter<RecentVideoAdapter.RecentVideoViewHolder> {

    private Context context;
    private List<YoutubeData> youtubeDataList;
    private int selected_video;

    public RecentVideoAdapter(Context context, List<YoutubeData> youtubeDataList, int selected_video) {
        this.context = context;
        this.youtubeDataList = youtubeDataList;
        this.selected_video = selected_video;
    }

    @NonNull
    @Override
    public RecentVideoAdapter.RecentVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RecentVideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentVideoAdapter.RecentVideoViewHolder holder, int position) {

//        if (selected_video == position){
//
//        }else {
//
//            Glide.with(context).load(youtubeDataList.get(position).getThumbnail()).into(holder.thumbnail);
//            holder.title.setText(youtubeDataList.get(position).getTitle());
////        }
//
//        holder.videoContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return youtubeDataList.size();
    }

    public class RecentVideoViewHolder extends RecyclerView.ViewHolder {

        CircleImageView thumbnail;
        TextView title;
        RelativeLayout videoContainer;

        public RecentVideoViewHolder(@NonNull View itemView) {
            super(itemView);

            //thumbnail = itemView.findViewById(R.id.playVideothumb);
            //thumbnail = itemView.findViewById(R.id.playVideothumb);
            title = itemView.findViewById(R.id.thumbText);
//            time = itemView.findViewById(R.id.thumbTime);
//            videoContainer = itemView.findViewById(R.id.videoContainer);

        }
    }
}

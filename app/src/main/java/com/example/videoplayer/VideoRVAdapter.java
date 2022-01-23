package com.example.videoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VideoRVAdapter extends RecyclerView.Adapter<VideoRVAdapter.ViewHolder> {

    private List<VideoRVModel> videoRVModelArrayList;
    private Context context;
    private VideoClickInterface videoClickInterface;

    public VideoRVAdapter(List<VideoRVModel> videoRVModelArrayList, Context context, VideoClickInterface videoClickInterface) {
        this.videoRVModelArrayList = videoRVModelArrayList;
        this.context = context;
        this.videoClickInterface = videoClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        VideoRVModel videoRVModel = videoRVModelArrayList.get(position);
        holder.thumbnailIV.setImageBitmap(videoRVModel.getThumbnail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoClickInterface.onVideoClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoRVModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.idIVThumbnail);
        }
    }

    public interface VideoClickInterface {
        void onVideoClick(int position);
    }
}

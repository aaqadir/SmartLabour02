package com.example.smartlabour01;
import android.content.Context;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistsAdapter  extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {
    private Context mCtx;

    private List<Artist> artistList;



    ArtistsAdapter(Context mCtx, List<Artist> artistList) {

        this.mCtx = mCtx;

        this.artistList = artistList;

    }



    @NonNull

    @Override

    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.labour_card, parent, false);

        return new ArtistViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {

        Artist artist = artistList.get(position);

        holder.postName.setText(artist.Name);
        holder.postContact.setText(artist.Contact);

        Picasso.with(mCtx).load(artist.Image).into(holder.postImage);
       // holder.postImage.loa(mCtx,artist.Image);

        holder.postExperience.setText(artist.Experience);

    }



    @Override

    public int getItemCount() {

        return artistList.size();

    }



    class ArtistViewHolder extends RecyclerView.ViewHolder {



        TextView postName, postContact, postExperience;
        CircleImageView postImage;


        ArtistViewHolder(@NonNull View itemView) {

            super(itemView);



            postName = itemView.findViewById(R.id.labour_card_name);

            postContact = itemView.findViewById(R.id.labour_card_contact);

            postImage = itemView.findViewById(R.id.labour_card_profile_image);

            postExperience = itemView.findViewById(R.id.labour_card_experience);

        }

    }
}

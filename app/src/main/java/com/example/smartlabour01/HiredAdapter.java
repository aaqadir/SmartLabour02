package com.example.smartlabour01;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HiredAdapter  extends RecyclerView.Adapter<HiredAdapter.HiredViewHolder> {
    private Context mCtx;

    private List<Hired> hiredList;



    HiredAdapter(Context mCtx, List<Hired> hiredList) {

        this.mCtx = mCtx;

        this.hiredList = hiredList;

    }



    @NonNull

    @Override

    public HiredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.hired_labour_card, parent, false);

        return new HiredViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull final HiredViewHolder holder, int position) {

        final Hired hired = hiredList.get(position);


        holder.postHiredLabourName.setText("Name : "+hired.Name);

        holder.postHiredLabourContact.setText("Contact : "+hired.Contact);

        holder.postHiredLabourSkills.setText("Skills : "+hired.Skills);

   //     holder.postProjectStartDate.setText(project.ProjectStartDate);
        Picasso.with(mCtx).load(hired.Image).into(holder.postHiredLabourImage);

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,C_Hired_Labour_For_Projects.class);
             //   intent.putExtra("Key",hired.ProjectType);
                mCtx.startActivity(intent);
            }
        });*/
    }



    @Override

    public int getItemCount() {

        return hiredList.size();

    }



    class HiredViewHolder extends RecyclerView.ViewHolder {



        TextView postHiredLabourName, postHiredLabourContact, postHiredLabourSkills;
        CircleImageView postHiredLabourImage;

        HiredViewHolder(@NonNull View itemView) {

            super(itemView);



            postHiredLabourName = itemView.findViewById(R.id.hired_labour_card_name);

            postHiredLabourContact = itemView.findViewById(R.id.hired_labour_card_contact);

            postHiredLabourSkills = itemView.findViewById(R.id.hired_labour_card_skills);

            postHiredLabourImage = itemView.findViewById(R.id.hired_labour_card_profile_image);

        }

    }
}


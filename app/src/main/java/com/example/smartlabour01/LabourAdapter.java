package com.example.smartlabour01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LabourAdapter  extends RecyclerView.Adapter<LabourAdapter.LabourViewHolder> {
    private Context mCtx;

    private List<Labour1> labourList;

    private String skill;



    LabourAdapter(Context mCtx, List<Labour1> LabourList,String Skill) {

        this.mCtx = mCtx;

        this.labourList = LabourList;

        this.skill = Skill;

    }



    @NonNull

    @Override

    public LabourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.labour_card, parent, false);

        return new LabourViewHolder(view);

    }



    @SuppressLint("SetTextI18n")
    @Override

    public void onBindViewHolder(@NonNull final LabourViewHolder holder, int position) {

        final Labour1 labour = labourList.get(position);
        final String post_key = labourList.get(position).toString();
       final String a= holder.itemView.getRootView().toString();
//        final String post_key = mCtx.getString(position);
        holder.post_Name.setText("Name : " +labour.Name);

        holder.post_Contact.setText("Contact : " +labour.Contact);

        holder.post_Experience.setText("Experience : " +labour.Experience +" years");
        Picasso.with(mCtx).load(labour.Image).into(holder.post_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,C_Hire_Labour.class);
                intent.putExtra("Skill",skill);
               intent.putExtra("PostId",labour.Contact);
           //    Toast.makeText(mCtx,labour.Contact,Toast.LENGTH_LONG).show();
                mCtx.startActivity(intent);
               /* Intent intent = new Intent(mCtx,C_Hired_Labour_For_Labours.class);
                intent.putExtra("Key",Labour.LabourType);
                mCtx.startActivity(intent);*/
            }
        });
    }



    @Override

    public int getItemCount() {

        return labourList.size();

    }



    class LabourViewHolder extends RecyclerView.ViewHolder {



        TextView post_Name, post_Contact,post_Experience;
        CircleImageView post_image;

        LabourViewHolder(@NonNull View itemView) {

            super(itemView);

            post_Name =  itemView.findViewById(R.id.labour_card_name);
            post_Contact =  itemView.findViewById(R.id.labour_card_contact);
            post_image =  itemView.findViewById(R.id.labour_card_profile_image);
            post_Experience = itemView.findViewById(R.id.labour_card_experience);

        }

    }
}


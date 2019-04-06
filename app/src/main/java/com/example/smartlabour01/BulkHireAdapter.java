package com.example.smartlabour01;

import android.annotation.SuppressLint;
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

public class BulkHireAdapter  extends RecyclerView.Adapter<BulkHireAdapter.BulkHireViewHolder> {
    private Context mCtx;

    private List<BulkHire> BulkHireList;



    BulkHireAdapter(Context mCtx, List<BulkHire> BulkHireList) {

        this.mCtx = mCtx;

        this.BulkHireList = BulkHireList;

    }



    @NonNull

    @Override

    public BulkHireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.contractor_bulkhire_card, parent, false);

        return new BulkHireViewHolder(view);

    }



    @SuppressLint("SetTextI18n")
    @Override

    public void onBindViewHolder(@NonNull final BulkHireViewHolder holder, int position) {

        final BulkHire BulkHire = BulkHireList.get(position);


        holder.ProjectLocation.setText("Project Location : " +BulkHire.ProjectLocation);

        holder.ProjectStartDate.setText("Project Start Date : "+BulkHire.ProjectStartDate);

        holder.LabourType.setText("Labour Type : "+BulkHire.LabourType);

        holder.LabourCount.setText("Labour Count : "+BulkHire.LabourCount);
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,C_Hired_Labour_For_Projects.class);
                intent.putExtra("ProjectType",BulkHire.ProjectType);
                mCtx.startActivity(intent);
            }
        });*/
    }



    @Override

    public int getItemCount() {

        return BulkHireList.size();

    }



    class BulkHireViewHolder extends RecyclerView.ViewHolder {



        TextView ProjectLocation,ProjectStartDate,LabourType,LabourCount;


        BulkHireViewHolder(@NonNull View itemView) {

            super(itemView);



            LabourCount = itemView.findViewById(R.id.bulk_hire_labourCount);

            ProjectLocation = itemView.findViewById(R.id.bulk_hire_projectLocation);

            ProjectStartDate = itemView.findViewById(R.id.bulk_hire_projectStartDate);

            LabourType = itemView.findViewById(R.id.bulk_hire_labourType);

        }

    }
}


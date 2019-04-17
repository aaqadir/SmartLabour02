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

public class LabourFinishedProjectAdapter  extends RecyclerView.Adapter<LabourFinishedProjectAdapter.LabourFinishedProjectViewHolder> {
    private Context mCtx;

    private List<LabourFinishedProject> projectList;



    LabourFinishedProjectAdapter(Context mCtx, List<LabourFinishedProject> projectList) {

        this.mCtx = mCtx;

        this.projectList = projectList;

    }



    @NonNull

    @Override

    public LabourFinishedProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.labour_finished_project_card, parent, false);

        return new LabourFinishedProjectViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull final LabourFinishedProjectViewHolder holder, int position) {

        final LabourFinishedProject project = projectList.get(position);

     //  String key = holder.getAdapterPosition(project);

        holder.postProjectType.setText(project.ProjectType);

        holder.postProjectLocation.setText(project.Location);

        holder.postProjectStartDate.setText(project.StartDate);

        holder.postProjectEndDate.setText(project.EndDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,L_FinishedWorkHistory.class);
                intent.putExtra("Key",project.Key);
                mCtx.startActivity(intent);
            // Toast.makeText(mCtx,""+project.Key,Toast.LENGTH_LONG).show();
            }

        });
    }



    @Override

    public int getItemCount() {

        return projectList.size();

    }



    class LabourFinishedProjectViewHolder extends RecyclerView.ViewHolder {



        TextView postProjectType, postProjectLocation,postProjectStartDate,postProjectEndDate;


        LabourFinishedProjectViewHolder(@NonNull View itemView) {

            super(itemView);




            postProjectType = itemView.findViewById(R.id.contractorProjectType);

            postProjectLocation = itemView.findViewById(R.id.contractorProjectLocation);

            postProjectStartDate = itemView.findViewById(R.id.contractorProjectStartDate);

            postProjectEndDate = itemView.findViewById(R.id.contractorProjectEndDate);
        }

    }
}


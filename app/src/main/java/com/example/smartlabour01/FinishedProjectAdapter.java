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

public class FinishedProjectAdapter  extends RecyclerView.Adapter<FinishedProjectAdapter.FinishedProjectViewHolder> {
    private Context mCtx;

    private List<FinishedProject> projectList;



    FinishedProjectAdapter(Context mCtx, List<FinishedProject> projectList) {

        this.mCtx = mCtx;

        this.projectList = projectList;

    }



    @NonNull

    @Override

    public FinishedProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.contractor_finished_project_card, parent, false);

        return new FinishedProjectViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull final FinishedProjectViewHolder holder, int position) {

        final FinishedProject project = projectList.get(position);


        holder.postProjectName.setText(project.ProjectName);

        holder.postProjectType.setText(project.ProjectType);

        holder.postProjectLocation.setText(project.ProjectLocation);

        holder.postProjectStartDate.setText(project.ProjectStartDate);

        holder.postProjectEndDate.setText(project.ProjectEndDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,C_HiredLabourForFinishedProjects.class);
                intent.putExtra("ProjectType",project.ProjectType);
                mCtx.startActivity(intent);
            }

        });
    }



    @Override

    public int getItemCount() {

        return projectList.size();

    }



    class FinishedProjectViewHolder extends RecyclerView.ViewHolder {



        TextView postProjectName, postProjectType, postProjectLocation,postProjectStartDate,postProjectEndDate;


        FinishedProjectViewHolder(@NonNull View itemView) {

            super(itemView);



            postProjectName = itemView.findViewById(R.id.contractorProjectName);

            postProjectType = itemView.findViewById(R.id.contractorProjectType);

            postProjectLocation = itemView.findViewById(R.id.contractorProjectLocation);

            postProjectStartDate = itemView.findViewById(R.id.contractorProjectStartDate);

            postProjectEndDate = itemView.findViewById(R.id.contractorProjectEndDate);
        }

    }
}


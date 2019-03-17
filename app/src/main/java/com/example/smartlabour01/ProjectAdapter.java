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

public class ProjectAdapter  extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private Context mCtx;

    private List<Project> projectList;



    ProjectAdapter(Context mCtx, List<Project> projectList) {

        this.mCtx = mCtx;

        this.projectList = projectList;

    }



    @NonNull

    @Override

    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.contractor_project_card, parent, false);

        return new ProjectViewHolder(view);

    }



    @Override

    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {

        Project project = projectList.get(position);

        holder.postProjectName.setText(project.ProjectName);

        holder.postProjectType.setText(project.ProjectType);

        holder.postProjectLocation.setText(project.ProjectLocation);

        holder.postProjectStartDate.setText(project.ProjectStartDate);

    }



    @Override

    public int getItemCount() {

        return projectList.size();

    }



    class ProjectViewHolder extends RecyclerView.ViewHolder {



        TextView postProjectName, postProjectType, postProjectLocation,postProjectStartDate;


        ProjectViewHolder(@NonNull View itemView) {

            super(itemView);



            postProjectName = itemView.findViewById(R.id.contractorProjectName);

            postProjectType = itemView.findViewById(R.id.contractorProjectType);

            postProjectLocation = itemView.findViewById(R.id.contractorProjectLocation);

            postProjectStartDate = itemView.findViewById(R.id.contractorProjectStartDate);

        }

    }
}


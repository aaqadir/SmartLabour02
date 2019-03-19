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

    public void onBindViewHolder(@NonNull final ProjectViewHolder holder, int position) {

        final Project project = projectList.get(position);


        holder.postProjectName.setText(project.ProjectName);

        holder.postProjectType.setText(project.ProjectType);

        holder.postProjectLocation.setText(project.ProjectLocation);

        holder.postProjectStartDate.setText(project.ProjectStartDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,C_Hired_Labour_For_Projects.class);
                intent.putExtra("Key",project.ProjectType);
                mCtx.startActivity(intent);
            }
        });
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


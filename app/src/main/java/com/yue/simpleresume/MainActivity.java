package com.yue.simpleresume;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yue.simpleresume.models.BasicInfo;
import com.yue.simpleresume.models.Education;
import com.yue.simpleresume.models.Experience;
import com.yue.simpleresume.models.Project;
import com.yue.simpleresume.utils.DateUtils;
import com.yue.simpleresume.utils.ModelUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    public static final int REQ_CODE_EDUCATION_EDIT = 100;
    public static final int REQ_CODE_EXPERIENCE_EDIT = 101;
    public static final int REQ_CODE_PROJECT_EDIT = 102;
    private static final int REQ_CODE_INFO_EDIT = 103;

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experience";
    private static final String MODEL_PROJECTS = "projects";
    private static final String  MODEL_INFO = "information";


    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        setupUI();
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        findViewById(R.id.info_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoEditActivity.class);
                intent.putExtra(InfoEditActivity.KEY_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_INFO_EDIT);
            }
        });

        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        findViewById(R.id.add_experience_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

        setupBasicInfo();
        setupEducations();
        setupExperiences();
        setupProjects();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_INFO_EDIT:
                    basicInfo = data.getParcelableExtra(InfoEditActivity.KEY_INFO);
                    updateBasicInfo(basicInfo);
                    break;
                case REQ_CODE_EDUCATION_EDIT:
                    String educationID = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if (educationID != null) {
                        deleteEducation(educationID);
                    } else {
                        Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                        updateEducation(education);
                    }
                    break;
                case REQ_CODE_EXPERIENCE_EDIT:
                    String experienceID = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                    if (experienceID != null) {
                        deleteExperience(experienceID);
                    } else {
                        Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;
                case REQ_CODE_PROJECT_EDIT:
                    String projectID = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if (projectID != null) {
                        deleteProject(projectID);
                    } else {
                        Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
            }
        }
    }



    private void deleteProject(String projectID) {
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, projectID)) {
                projects.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjects();
    }

    private void deleteExperience(String experienceID) {
        for (int i = 0; i < experiences.size(); i++) {
            Experience e = experiences.get(i);
            if (TextUtils.equals(e.id, experienceID)) {
                experiences.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void deleteEducation(String educationID) {
        for (int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, educationID)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void updateBasicInfo(BasicInfo basicInfo) {

        ModelUtils.save(this, MODEL_INFO, basicInfo);
        this.basicInfo = basicInfo;
        setupBasicInfo();
    }

    private void updateProject(Project p) {
        boolean found = false;
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            if (TextUtils.equals(project.id, p.id)) {
                projects.set(i, p);
                found = true;
                break;
            }
        }
        if (!found) {
            projects.add(p);
        }
        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjects();
    }

    private void updateExperience(Experience e) {
        boolean found = false;
        for (int i = 0; i < experiences.size(); i++) {
            Experience experience = experiences.get(i);
            if (TextUtils.equals(experience.id, e.id)) {
                experiences.set(i, e);
                found = true;
                break;
            }
        }

        if (!found) {
            experiences.add(e);
        }
        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void updateEducation(Education e) {
        boolean found = false;
        for (int i = 0; i < educations.size(); i++) {
            Education education = educations.get(i);
            if (TextUtils.equals(education.id, e.id)) {
                educations.set(i, e);
                found = true;
                break;
            }
        }

        if (!found) {
            educations.add(e);
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void setupBasicInfo() {
        ((TextView) findViewById(R.id.info_name)).setText(TextUtils.isEmpty(basicInfo.name)
                                                                    ? "Your Name"
                                                                    : basicInfo.name);
        ((TextView) findViewById(R.id.info_email)).setText(TextUtils.isEmpty(basicInfo.email)
                                                                    ? "Your Email"
                                                                    : basicInfo.email);
    }

    private void setupEducations() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations);
        educationsLayout.removeAllViews();
        for (Education education : educations) {
            View view = getEducationView(education);
            educationsLayout.addView(view);
        }
    }

    private View getEducationView(final Education education) {
        View view = getLayoutInflater().inflate(R.layout.education_item, null);

        String dateString = DateUtils.dateToString(education.startDate) + " ~ "
                + DateUtils.dateToString(education.endDate);
        ((TextView) view.findViewById(R.id.education_heading)).setText(education.school
                + " - " + education.major + " - " + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        view.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        return view;
    }

    private void setupExperiences() {
        LinearLayout experiencesLayout = (LinearLayout) findViewById(R.id.experiences);
        experiencesLayout.removeAllViews();
        for (Experience experience : experiences) {
            View view = getExperienceView(experience);
            experiencesLayout.addView(view);
        }
    }

    private View getExperienceView(final Experience experience) {
        View view = getLayoutInflater().inflate(R.layout.experience_item, null);

        String dateString = DateUtils.dateToString(experience.startDate) + " ~ "
                + DateUtils.dateToString(experience.endDate);
        ((TextView) view.findViewById(R.id.experience_heading)).setText(experience.company
                + " - " + experience.title + " - " + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.experience_details))
                .setText(formatItems(experience.details));

        view.findViewById(R.id.edit_experience_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, experience);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        return view;
    }

    private void setupProjects() {
        LinearLayout projectsLayout = (LinearLayout) findViewById(R.id.projects);
        projectsLayout.removeAllViews();
        for (Project project : projects) {
            View view = getProjectView(project);
            projectsLayout.addView(view);
        }
    }

    private View getProjectView(final Project project) {
        View view = getLayoutInflater().inflate(R.layout.project_item, null);

        String dateString = DateUtils.dateToString(project.startDate) + " ~ "
                + DateUtils.dateToString(project.endDate);
        ((TextView) view.findViewById(R.id.project_heading)).setText(project.name
                + " - "  + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.project_details))
                .setText(formatItems(project.details));

        view.findViewById(R.id.edit_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

        return view;
    }

    public static String formatItems(List<String> items) {

        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String formatEditItems(List<String> items) {

        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private void loadData() {
        BasicInfo savedBasicInfo = ModelUtils.read (this,
                                                    MODEL_INFO,
                                                    new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo() : savedBasicInfo;

        List<Education> savedEducation = ModelUtils.read (this,
                                                         MODEL_EDUCATIONS,
                                                         new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.read (this,
                                                            MODEL_EXPERIENCES,
                                                            new TypeToken<List<Experience>>(){});
        experiences = savedExperience == null ? new ArrayList<Experience>() : savedExperience;

        List<Project> savedProject = ModelUtils.read (this,
                                                      MODEL_PROJECTS,
                                                      new TypeToken<List<Project>>(){});
        projects = savedProject == null ? new ArrayList<Project>() : savedProject;


    }

}

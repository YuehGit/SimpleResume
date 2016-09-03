package com.yue.simpleresume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yue.simpleresume.models.BasicInfo;
import com.yue.simpleresume.models.Education;
import com.yue.simpleresume.models.Experience;
import com.yue.simpleresume.models.Project;
import com.yue.simpleresume.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.add_experience_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivity(intent);
            }
        });

        fakeData();

        setupBasicInfo();
        setupEducations();
        setupExperiences();
        setupProjects();
    }

    private void setupBasicInfo() {
        ((TextView) findViewById(R.id.info_name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.info_email)).setText(basicInfo.email);
    }

    private void setupEducations() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations);
        for (Education education : educations) {
            View view = getEducationView(education);
            educationsLayout.addView(view);
        }
    }

    private View getEducationView(Education education) {
        View view = getLayoutInflater().inflate(R.layout.education_item, null);

        String dateString = DateUtils.dateToString(education.startDate) + " ~ "
                + DateUtils.dateToString(education.endDate);
        ((TextView) view.findViewById(R.id.education_heading)).setText(education.school
                + " - " + education.major + " - " + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        return view;
    }

    private void setupExperiences() {
        LinearLayout experiencesLayout = (LinearLayout) findViewById(R.id.experiences);
        for (Experience experience : experiences) {
            View view = getExperienceView(experience);
            experiencesLayout.addView(view);
        }
    }

    private View getExperienceView(Experience experience) {
        View view = getLayoutInflater().inflate(R.layout.experience_item, null);

        String dateString = DateUtils.dateToString(experience.startDate) + " ~ "
                + DateUtils.dateToString(experience.endDate);
        ((TextView) view.findViewById(R.id.experience_heading)).setText(experience.company
                + " - " + experience.title + " - " + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.experience_details))
                .setText(formatItems(experience.details));

        return view;
    }


    private void setupProjects() {
        LinearLayout projectsLayout = (LinearLayout) findViewById(R.id.projects);
        for (Project project : projects) {
            View view = getProjectView(project);
            projectsLayout.addView(view);
        }
    }

    private View getProjectView(Project project) {
        View view = getLayoutInflater().inflate(R.layout.project_item, null);

        String dateString = DateUtils.dateToString(project.startDate) + " ~ "
                + DateUtils.dateToString(project.endDate);
        ((TextView) view.findViewById(R.id.project_heading)).setText(project.name
                + " - "  + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.project_details))
                .setText(formatItems(project.details));

        return view;
    }

    private void fakeData() {
        basicInfo = new BasicInfo();
        basicInfo.name = "John Doe";
        basicInfo.email = "Doe.John@gmail.com";

        Education education1 = new Education();
        education1.school = "UCSD";
        education1.major = "Electrical Engineering";
        education1.startDate = DateUtils.stringToDate("09/2011");
        education1.endDate = DateUtils.stringToDate("09/2012");

        education1.courses = new ArrayList<>();
        education1.courses.add("Wireless Network");
        education1.courses.add("Communication Theory");
        education1.courses.add("Mobile Security");
        education1.courses.add("Signal Processing");

        Education education2 = new Education();
        education2.school = "UCLA";
        education2.major = "Computer Science";
        education2.startDate = DateUtils.stringToDate("01/2013");
        education2.endDate = DateUtils.stringToDate("09/2014");

        education2.courses = new ArrayList<>();
        education2.courses.add("Database");
        education2.courses.add("Algorithm");
        education2.courses.add("OO Design");
        education2.courses.add("Operating System");

        educations = new ArrayList<>();
        educations.add(education1);
        educations.add(education2);

        Experience experience1 = new Experience();
        experience1.company = "Google";
        experience1.title = "Software Engineer";
        experience1.startDate = DateUtils.stringToDate("11/2014");
        experience1.endDate = DateUtils.stringToDate("06/2015");

        experience1.details = new ArrayList<>();
        experience1.details.add("Built something useing some tech");
        experience1.details.add("Built something useing some tech");
        experience1.details.add("Built something useing some tech");

        Experience experience2 = new Experience();
        experience2.company = "Facebook";
        experience2.title = "Software Engineer";
        experience2.startDate = DateUtils.stringToDate("08/2015");
        experience2.endDate = DateUtils.stringToDate("09/2016");

        experience2.details = new ArrayList<>();
        experience2.details.add("Built something useing some tech");
        experience2.details.add("Built something useing some tech");
        experience2.details.add("Built something useing some tech");

        experiences = new ArrayList<>();
        experiences.add(experience1);
        experiences.add(experience2);

        Project project1 = new Project();
        project1.name = "SimpleResume";
        project1.startDate = DateUtils.stringToDate("12/2014");
        project1.endDate = DateUtils.stringToDate("01/2015");

        project1.details = new ArrayList<>();
        project1.details.add("Complete something using some tech");
        project1.details.add("Complete something using some tech");
        project1.details.add("Complete something using some tech");

        Project project2 = new Project();
        project2.name = "SimpleTodo";
        project2.startDate = DateUtils.stringToDate("06/2015");
        project2.endDate = DateUtils.stringToDate("09/2015");

        project2.details = new ArrayList<>();
        project2.details.add("Complete something using some tech");
        project2.details.add("Complete something using some tech");
        project2.details.add("Complete something using some tech");

        projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
    }

    private String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}

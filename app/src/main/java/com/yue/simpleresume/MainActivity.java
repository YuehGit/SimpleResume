package com.yue.simpleresume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Education education;
    private Experience experience;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fakeData();

        setupBasicInfo();
        setupEducation();
        setupExperience();
        setupProjects();
    }

    private void setupBasicInfo() {
        ((TextView) findViewById(R.id.info_name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.info_email)).setText(basicInfo.email);
    }

    private void setupEducation() {
        String dateString = DateUtils.dateToString(education.startDate) + " ~ "
                + DateUtils.dateToString(education.endDate);
        ((TextView) findViewById(R.id.education_heading)).setText(education.school
                + " - " + education.major + " - " + "(" + dateString + ")");
        ((TextView) findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));
    }

    private void setupExperience() {
        String dateString = DateUtils.dateToString(experience.startDate) + " ~ "
                + DateUtils.dateToString(experience.endDate);
        ((TextView) findViewById(R.id.experience_heading)).setText(experience.company
                + " - " + experience.title + " - " + "(" + dateString + ")");
        ((TextView) findViewById(R.id.experience_details))
                .setText(formatItems(experience.details));

    }

    private void setupProjects() {
        String dateString = DateUtils.dateToString(project.startDate) + " ~ "
                + DateUtils.dateToString(project.endDate);
        ((TextView) findViewById(R.id.projects_heading)).setText(project.name
                + " - "  + "(" + dateString + ")");
        ((TextView) findViewById(R.id.projects_details))
                .setText(formatItems(project.details));
    }

    private void fakeData() {
        basicInfo = new BasicInfo();
        basicInfo.name = "John Doe";
        basicInfo.email = "Doe.John@gmail.com";

        education = new Education();
        education.school = "UCSD";
        education.major = "Electrical Engineering";
        education.startDate = DateUtils.stringToDate("09/2011");
        education.endDate = DateUtils.stringToDate("09/2014");

        education.courses = new ArrayList<>();
        education.courses.add("Wireless Network");
        education.courses.add("Communication Theory");
        education.courses.add("Mobile Security");
        education.courses.add("Signal Processing");

        experience = new Experience();
        experience.company = "Google";
        experience.title = "Software Engineer";
        experience.startDate = DateUtils.stringToDate("04/2015");
        experience.endDate = DateUtils.stringToDate("09/2016");

        experience.details = new ArrayList<>();
        experience.details.add("Built something useing some tech");
        experience.details.add("Built something useing some tech");
        experience.details.add("Built something useing some tech");

        project = new Project();
        project.name = "SimpleResume";
        project.startDate = DateUtils.stringToDate("12/2014");
        project.endDate = DateUtils.stringToDate("01/2015");

        project.details = new ArrayList<>();
        project.details.add("Complete something using some tech");
        project.details.add("Complete something using some tech");
        project.details.add("Complete something using some tech");
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

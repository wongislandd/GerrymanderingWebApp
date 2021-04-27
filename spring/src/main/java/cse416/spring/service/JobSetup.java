package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.models.job.JobSummary;

public class JobSetup {
    int jobId;
    StateName state;
    JobSummary summary;
    String jobFolderPath;

    public JobSetup(int jobId, StateName state, JobSummary summary, String jobFolderPath) {
        this.jobId = jobId;
        this.state = state;
        this.summary = summary;
        this.jobFolderPath = jobFolderPath;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }

    public JobSummary getSummary() {
        return summary;
    }

    public void setSummary(JobSummary summary) {
        this.summary = summary;
    }

    public String getJobFolderPath() {
        return jobFolderPath;
    }

    public void setJobFolderPath(String jobFolderPath) {
        this.jobFolderPath = jobFolderPath;
    }
}

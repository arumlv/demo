import { Component, OnInit } from '@angular/core';
import { JobService } from '../service/job.service';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.css']
})
export class JobListComponent implements OnInit {

  constructor(private jobService: JobService) { }

  jobs: string[];

  ngOnInit(): void {
    this.jobService.getAll().subscribe(jobs => {
      this.jobs = jobs;
    });
  }
}

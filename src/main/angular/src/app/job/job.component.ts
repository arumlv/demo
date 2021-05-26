import { Component, OnInit, Input } from '@angular/core';
import { Job } from '../model/job';
import { JobService } from '../service/job.service';
import { timer } from 'rxjs';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  @Input() name: string;

  job: Job;


  constructor(private jobService: JobService) { }

  ngOnInit(): void {
    this.jobService.get(this.name).subscribe(job => {
      this.job = job;
      console.log(this.name + ' ' + job.status);
      if (job.status === 'STARTED') {
        this.startTime();
      }
    });
  }

  private startTime(): void {
    console.log(this.name + ' start timer...');
    const timerSub = timer(3000, 3000).subscribe(val => {
      this.jobService.get(this.name).subscribe(job => {
        this.job = job;
        if (job.status && job.status !== null && job.status !== 'STARTED') {
          timerSub.unsubscribe();
        }
      });
    }
    );
  }

  public startJob(): void {
    this.job.status = 'STARTED';
    this.jobService.post(this.name).subscribe(result => {
      console.log('starting ' + this.name + '...');
      this.startTime();
    });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Job } from '../model/job';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable()
export class JobService {

  private jobUrl: string;

  constructor(private http: HttpClient) {
    if (environment.production) {
      this.jobUrl = '/api/jobs';
    } else {
      this.jobUrl = 'http://localhost:8080/api/jobs';
    }
  }

  public getAll(): Observable<string[]> {
    return this.http.get<string[]>(this.jobUrl);
  }

  public post(name: string): Observable<any> {
    return this.http.post(this.jobUrl + '/' + name, null);
  }

  public get(name: string): Observable<Job> {
    return this.http.get<Job>(this.jobUrl + '/' + name);
  }
}
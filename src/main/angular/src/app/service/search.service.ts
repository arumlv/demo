import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { SpringRestApi } from '../model/spingrest.d';

@Injectable()
export class SearchService {
  url: string;

  constructor(private http: HttpClient) {

    if (environment.production) {
      this.url = '/';
    } else {
      this.url = 'http://localhost:8080/';
    }
    this.url = this.url + 'api/registers/search/findByParams?projection=registers&size=5';
  }

  public findByParams(params: { [key: string]: string; }): Observable<SpringRestApi> {
    return this.http.get<SpringRestApi>(this.url, {
      params: params
    });
  }
}

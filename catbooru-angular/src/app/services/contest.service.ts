import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contest } from '../models/Contest';

@Injectable({
  providedIn: 'root'
})
export class ContestService {

  url:string = 'http://localhost:8080/api/posts/'

  constructor(private http:HttpClient) { }

  getContests():Observable<Contest[]>{
    return this.http.get<Contest[]>(this.url + 'contests')
  }
}

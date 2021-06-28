import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contest } from '../models/Contest';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class ContestService {

  url:string = 'http://localhost:8080/api/posts/'

  constructor(private http:HttpClient, private userService:UserService) { }

  getContests():Observable<Contest[]>{
    return this.http.get<Contest[]>(this.url + 'contests')
  }

  newContest(name:string, description:string, duration:string){
    const headerDict = {
      'Authorization': 'Bearer '+this.userService.user.token
    }
    const requestOptions = {                                                                                                                                                                             
      headers: new HttpHeaders(headerDict),
      params: new HttpParams().append('days', duration)
    };
   return this.http.post(this.url + 'contest', {'name':name, 'description':description}, requestOptions)
  } 
}

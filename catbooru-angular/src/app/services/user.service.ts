import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http'
import { User } from '../models/User'


@Injectable({
  providedIn: 'root'
})
export class UserService {
  url:string = 'http://localhost:8080/api/users/'
  user:User



  constructor(private http:HttpClient) {this.user = JSON.parse(localStorage.getItem('userdata'));}


  register(username:string, password:string){
    return this.http.post<User>(this.url + 'register', {'username':username, 'password':password});
  }

  login(username:string, password:string):Observable<User>{
    return this.http.post<User>(this.url + 'login', {'username':username, 'password':password});
  }

  logout(){
    this.user = null;
    localStorage.removeItem('userdata');
  }

  /*changeUsernamePassword(username:string, password:string):Observable<User>{
    const headerDict = {
      'Authorization': 'Bearer '+this.user.token
    }
    
    const requestOptions = {                                                                                                                                                                                 
      headers: new HttpHeaders(headerDict), 
    };
    return this.http.put<User>(this.url + this.user.id, {'username':username, 'password':password}, requestOptions);
  }*/

  makeAdmin(username:string, password:string):Observable<User>{
    const headerDict = {
      'Authorization': 'Bearer '+this.user.token
    }
    const requestOptions = {                                                                                                                                                                                 
      headers: new HttpHeaders(headerDict), 
      params: new HttpParams().append('username', username).append('superPassword', password)
    };
    return this.http.put<User>(this.url + "admin", requestOptions, requestOptions);
  }

  delete(){
    const headerDict = {
      'Authorization': 'Bearer '+this.user.token
    }
    const requestOptions = {                                                                                                                                                                                 
      headers: new HttpHeaders(headerDict), 
    };
    return this.http.delete(this.url + this.user.id, requestOptions);
  }
}

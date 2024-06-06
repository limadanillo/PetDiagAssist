import { Injectable  } from '@angular/core';
import {DataUser, UpdateUser, UserResponse, AddUser, UserPag, UpdateUserProfile, User} from './user';
import { Observable } from 'rxjs';


import {HttpService} from '../../shared/http.service'
import {update} from "@angular-devkit/build-angular/src/tools/esbuild/angular/compilation/parallel-worker";
@Injectable()
export class UserService  {

  constructor(private httpService:HttpService)  {

  }

  getCurrentUser(): Observable<DataUser> {
    return this.httpService.get<DataUser>('/users/profile')
  }
  getAllUsers(): Observable<UserPag> {
    return this.httpService.get<UserPag>('/users')
  }

  updateCurrentUser(data:UpdateUserProfile): Observable<DataUser> {
    return this.httpService.patch<DataUser>('/profile',data)
  }

  updateUser(data:UpdateUser, userPublicId: string): Observable<User> {
    return this.httpService.patch<User>('/users/'+ userPublicId ,data)
  }

  createUser(data:AddUser): Observable<UserResponse> {
    return this.httpService.post<UserResponse>('/admin/users',data)
  }
  deleteUser(id:number): Observable<UserResponse> {
    return this.httpService.delete<UserResponse>('/admin/users/'+id)
  }
  logoutUser(): Observable<any> {
    return this.httpService.delete<UserResponse>('/auth/logout')
  }

}

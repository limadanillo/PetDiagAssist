import {HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import {Observable, of, switchMap} from 'rxjs';
import { NbAuthService, NbAuthJWTToken } from '@nebular/auth';
import {NbAuthToken} from "@nebular/auth/services/token/token";
import { inject } from '@angular/core';
import {catchError} from "rxjs/operators";


// Função Interceptor
export const JWTInterceptor = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {

  const authService =  inject(NbAuthService);
  let newRequest = req.clone();

  console.log("req.url",req.url);
  if (!req.url.match(/api\/public/) || !req.url.match(/auths\/login/)) {
    console.log("req.url",req.url);
    return authService.getToken().pipe(
      switchMap((token: NbAuthToken) => {
        if (token.isValid()) {
          console.log("token",token);
          const JWT = `Bearer ${token.getValue()}`;
          newRequest = req.clone({setHeaders: {Authorization: JWT}});
          console.log("newRequest",newRequest);
          return next(newRequest);
        } else {
          return next(req);
        }
      }),
      catchError((error) => {
        return next(req);
      })
    );
  }
  return next(newRequest);
}

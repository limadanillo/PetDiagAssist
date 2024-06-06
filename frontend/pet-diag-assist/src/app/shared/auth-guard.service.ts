import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {NbAuthService,NbAuthToken} from "@nebular/auth";
import { NbToastrService, NbComponentStatus } from '@nebular/theme';
import {tap} from "rxjs";
import {UserService} from '../core/user/user.service';

@Injectable({
  providedIn: 'root'
})

export class AuthGuardService implements CanActivate {


  user = {user_id:0,user_email:'',exp:0};


  constructor(private authService: NbAuthService,
              private router: Router,
              private toastrService: NbToastrService,
              private userService : UserService) {
    console.log('check token');
    //
    this.authService.onTokenChange()
      .subscribe( (token: NbAuthToken) => {
        if (token.isValid()) {
          this.user = token.getPayload(); // receive payload from token and assign it to our `user` variable
          if(Math.floor(Date.now() / 1000) > this.user.exp){
            localStorage.clear();
            this.router.navigateByUrl('/auth/login');
          }
          console.log('check token');
          this.userService.getCurrentUser().subscribe( (result) => {
          },(err) =>{
            if(err.status==401){
              localStorage.clear();
              this.router.navigateByUrl('/auth/login').then(r => this.showFailedToast(`You are not logged in!`) );
            }
          })
        } else {
          localStorage.clear();
          this.router.navigateByUrl('/auth/login').then(r => this.showFailedToast(`You are not logged in!`));
        }
      });
  }

  canActivate() {
    console.log('canActivate');
    return this.authService.isAuthenticated()
      .pipe(
        tap(authenticated => {
          console.log('canActivate==>');
          console.log(authenticated);
          if (!authenticated) {
            this.router.navigate(['auth/login']).then(r => this.showFailedToast(`You are not logged in!`));
          }
        })
      );
  }

  showFailedToast(message: string, status: NbComponentStatus = 'danger') {
    this.toastrService.show(message, 'Error', { status });
  }
}

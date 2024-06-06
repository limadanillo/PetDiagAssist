import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
  HttpRequest,
  provideHttpClient,
  withInterceptors
} from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import {
  NbThemeModule, NbLayoutModule, NbSidebarModule, NbMenuModule, NbCardModule,
  NbInputModule, NbButtonModule, NbIconModule, NbAlertModule, NbFormFieldModule,
  NbUserModule, NbActionsModule, NbIconLibraries,
  DEFAULT_THEME, COSMIC_THEME, CORPORATE_THEME, NbToastrService, NbToastrModule
} from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';

import { routes } from './app.routes';
import { AuthModule } from "./auth/auth.module";
import { baseUrl, dashboard, login } from "../environments/environment";
import {NB_AUTH_TOKEN_INTERCEPTOR_FILTER, NbAuthJWTToken, NbAuthModule, NbPasswordAuthStrategy} from "@nebular/auth";
import {ErrorService} from "./shared/errors.service";
import {AuthGuardService} from "./shared/auth-guard.service";
import {HttpService} from "./shared/http.service";
import {UserService} from "./core/user/user.service";
import {PagesModule} from "./pages/pages.modules";
import {ServerInterceptor} from "./shared/server.interceptor";
import {HttpXSRFInterceptor} from "./auth/xsrf.interceptor";
import {APP_BASE_HREF} from "@angular/common";
import {JWTInterceptor} from "./shared/interceptor";
import {VeterinarianService} from "./core/veterinarian/veterinarian.service";

function filterInterceptorRequest(req: HttpRequest<any>): boolean {
  return ['/api/auths/login','/api/auths/logout']
    .some(url => req.url.includes(url));
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([JWTInterceptor])),
    provideAnimations(),
    FormsModule,
    importProvidersFrom(
      NbThemeModule.forRoot({ name: 'corporate' }, [CORPORATE_THEME, COSMIC_THEME]),
      NbLayoutModule,
      NbSidebarModule.forRoot(),
      NbMenuModule.forRoot(),
      NbCardModule,
      NbInputModule,
      NbButtonModule,
      NbIconModule,
      NbAlertModule,
      NbFormFieldModule,
      NbUserModule,
      NbActionsModule,
      NbEvaIconsModule,
      ReactiveFormsModule,
      AuthModule,
      PagesModule,
      NbToastrModule,
      NbAuthModule.forRoot({
        strategies: [
          NbPasswordAuthStrategy.setup({
            name: 'email',
            token: {
              class: NbAuthJWTToken,
              key: "data.access_token",
            },
            baseEndpoint: `${baseUrl}`,
            login: {
              endpoint: `${login}`,
              method: 'post',
              redirect: {
                success: `${dashboard}`,
                failure: null // stay on the same page
              }
            }
          })
        ],
        forms: {},
      })
    ),
    ErrorService,
    AuthGuardService,
    NbAuthJWTToken,
    HttpService,
    //own service
    UserService,
    VeterinarianService,
    HttpClientModule,
    { provide: APP_BASE_HREF, useValue: '/' }
  ],

};

import { NgModule, TemplateRef} from '@angular/core';
import {NbContextMenuModule , NbSelectModule,NbButtonModule,NbActionsModule,NbLayoutModule,NbAccordionModule, NbThemeModule, NbUserModule,NbToastrModule,NbSidebarModule, NbCardModule, NbIconModule, NbInputModule, NbTreeGridModule,NbListModule,NbMenuModule,} from '@nebular/theme';
import { PagesComponent } from './pages.component';
import { PagesRoutingModule } from './pages-routing.module';



import { UserService } from '../core/user/user.service';
import {NbFormFieldModule,NbBadgeModule,NbSpinnerModule,NbTabsetModule,NbDialogService,NbDialogModule,NbButtonGroupModule,NbCheckboxModule} from '@nebular/theme'
import { ReactiveFormsModule } from '@angular/forms';
import { ThemeModule } from '../UI/ui.module';
import {APP_BASE_HREF, CommonModule} from "@angular/common";
import {LazyLoadModule} from "../shared/lazyLoad/lazyLoadModule";
import {UserListComponent} from "./user-list/user-list.component";
import {MessageService} from "../shared/message.service";
import {HttpClientModule, HttpRequest} from "@angular/common/http";
import {HttpService} from "../shared/http.service";
import {AuthGuardService} from "../shared/auth-guard.service";
import {NB_AUTH_TOKEN_INTERCEPTOR_FILTER, NbAuthJWTToken} from "@nebular/auth";
import {ExamService} from "../core/exam/exam.service";
import {AnimalService} from "../core/animal/animal.service";
import {ImageService} from "../core/image/image.service";
import {ExamComponent} from "./exam/exam.component";
import {AnimalsComponent} from "./animal/animals.component";

function filterInterceptorRequest(req: HttpRequest<any>): boolean {
  return ['/api/auths/login','/api/auths/logout']
    .some(url => req.url.includes(url));
}

@NgModule({
  imports: [
    ThemeModule,
    NbMenuModule.forRoot(),
    PagesRoutingModule,
    NbLayoutModule,
    NbThemeModule,
    NbUserModule,
    NbToastrModule,
    NbSidebarModule,
    NbCardModule,
    NbIconModule,
    NbInputModule,
    NbButtonModule,
    NbSelectModule,
    NbActionsModule,
    NbTreeGridModule,
    NbListModule,
    NbContextMenuModule,
    NbAccordionModule,
    NbFormFieldModule,
    NbSpinnerModule,
    ReactiveFormsModule,
    NbTabsetModule,
    NbDialogModule.forRoot({}),
    CommonModule,
    NbButtonGroupModule,
    NbCardModule,
    NbBadgeModule,
    NbCheckboxModule,
    LazyLoadModule,
    NbToastrModule.forRoot({}),
    ExamComponent,
    AnimalsComponent

  ],
  declarations: [

    PagesComponent,


  ],
  providers:[
    NbDialogService,
    MessageService,
    AuthGuardService,
    NbAuthJWTToken,
    HttpService,
    //own service
    UserService,
    ExamService,
    AnimalService,
    ImageService,
    HttpClientModule,
    { provide: NB_AUTH_TOKEN_INTERCEPTOR_FILTER, useValue: filterInterceptorRequest },
    { provide: APP_BASE_HREF, useValue: '/' }
  ]
})
export class PagesModule {
}

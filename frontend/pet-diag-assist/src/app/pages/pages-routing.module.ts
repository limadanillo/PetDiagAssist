import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {UserListComponent} from "./user-list/user-list.component";
import {AuthGuardService} from "../shared/auth-guard.service";
import {UserComponent} from "./user/user.component";
import {ExamComponent} from "./exam/exam.component";
import {AnimalsComponent} from "./animal/animals.component";
//
// import { UserComponent } from './user/user.component';
//
// import { ProgramsComponent } from './programs/programs.component';
// import { ServersComponent } from './servers/servers.component';
// import { NucleiComponent } from './nuclei/nuclei.component';
// import { ScansComponent } from './scans/scans.component';
// import { VulnerabilitiesComponent } from './vulnerabilities/vulnerabilities.component';
// import {LeaksComponent } from './leaks/leaks.component';
// import {DomainsComponent } from './domains/domains.component';
// import {SubdomainsComponent } from './subdomains/subdomains.component';
// import {EnginesComponent } from './engines/engines.component';
// import {UrlsComponent } from './urls/urls.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [

    {
      path: 'dashboard',
      component: DashboardComponent,
    },
    {
      path: 'user',
      component: UserComponent,
    },
    {
      path: 'users',
      component: UserListComponent,
    },
    {
      path: 'exams',
      component: ExamComponent,
    },
    {
      path: 'animals',
      component: AnimalsComponent,
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      redirectTo: 'dashboard',
    },
  ],
}];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}

import {Component, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {UserService} from "../../core/user/user.service";
import {NbCardModule, NbLayoutModule, NbSidebarModule} from "@nebular/theme";
import {ThemeModule} from "../../UI/ui.module";



@Component({
  selector: 'ngx-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    NbLayoutModule,
    NbCardModule,
    ThemeModule,
    NbSidebarModule
  ],
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  data= {};
  user = {
    name : String,

  }


  constructor(private userService:UserService,private httpClient: HttpClient) {

  }

  ngOnInit(): void {

  }

}

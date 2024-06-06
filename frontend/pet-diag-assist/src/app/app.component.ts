import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NbIconLibraries, NbThemeService} from "@nebular/theme";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [
    RouterOutlet
  ],
  standalone: true,
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'pet-diag-assist';
  constructor(
    private iconLibraries: NbIconLibraries
  ) {
    this.iconLibraries.registerFontPack('eva', { packClass: 'eva', iconClassPrefix: 'eva' });
    this.iconLibraries.setDefaultPack('eva');
  }
}

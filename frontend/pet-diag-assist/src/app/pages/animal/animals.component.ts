import {Component, OnInit,TemplateRef,ViewChild} from '@angular/core';

import { Router } from '@angular/router';
import { MessageService  } from '../../shared/message.service';
import {
  NbDialogService,
  NbLayoutModule,
  NbSpinnerModule,
  NbCardModule,
  NbButtonModule,
  NbIconModule,
  NbSelectModule,
  NbBadgeModule, NbInputModule, NbSidebarModule

} from '@nebular/theme';
import {AnimalService} from "../../core/animal/animal.service";
import {ThemeModule} from "../../UI/ui.module";
import {Animal, AnimalResponse} from "../../core/animal/animal";

@Component({
  templateUrl: './animals.component.html',
  standalone: true,
  imports: [
    NbLayoutModule,
    ThemeModule,
    NbSpinnerModule,
    NbCardModule,
    NbButtonModule,
    NbIconModule,
    NbSelectModule,
    NbBadgeModule,
    NbInputModule,
    NbSidebarModule
  ],
  styleUrls: ['./animals.component.scss']
})
export class AnimalsComponent implements OnInit {

  loadingAnimal=true
  searchAnimal=''


  confirmDialogModal:any;
  @ViewChild('dialogConfirm', { read: TemplateRef }) dialogConfirm:any;

  domainsList:Animal[]=<any>[]


  limit=10
  page=0
  total_pages=1

  constructor(private router:Router,
              private dialogService: NbDialogService,
              private animalService: AnimalService,
              private messageService: MessageService) {
  this.getAnimals()
  }

  getAnimals() {
    this.loadingAnimal=true
    this.animalService.getAllAnimals(this.page, this.limit).subscribe( (result)=> {
      this.loadingAnimal=false
      this.domainsList=result.content
      this.total_pages=result.totalPages
    },(err)=>{
      this.loadingAnimal=false
      this.messageService.showToast(err.message,'danger')
    })
  }
  deleteDomainModale(id:string){

      this.confirmDialogModal = this.dialogService.open(this.dialogConfirm, { context: id });

  }
  changeLimit(event:any){
    this.limit=event
    if(event=='-1') this.page=0
    this.getAnimals()
  }
  goToPage(page:number){
    this.page=page
    this.getAnimals()
  }

  searchDomainInit(domainSearch:string){
    this.searchAnimal=domainSearch
    this.getAnimals()
  }

  deleteDomain(id:string){
    this.confirmDialogModal.close()
    this.loadingAnimal=true
    // this.animalService.deleteDomain(parseInt(id)).subscribe( (result)=>{
    //   this.loadingAnimal=false
    //   this.messageService.showToast(result.message,'success')
    //   this.getAnimals()
    // },(err)=>{
    //   this.loadingAnimal=false
    //   this.messageService.showToast(err.message,'danger')
    // })
  }
  goToSubdomain(name:any){
    this.router.navigate(['pages','subdomains',name]);
  }
  ngOnInit(): void {


  }
}

import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserService} from "../../core/user/user.service";
import {
  NbButtonModule,
  NbCardModule,
  NbDialogService, NbFormFieldModule,
  NbIconModule, NbInputModule,
  NbLayoutModule, NbSidebarModule,
  NbSpinnerModule
} from "@nebular/theme";
import {NgForOf} from "@angular/common";
import {ThemeModule} from "../../UI/ui.module";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MessageService} from "../../shared/message.service";
import {Router} from "@angular/router";
import {DataUser, DataUsers, UpdateUser, UserPag} from "../../core/user/user";

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    NbCardModule,
    NbButtonModule,
    NgForOf,
    NbLayoutModule,
    ThemeModule,
    NbIconModule,
    NbSpinnerModule,
    NbFormFieldModule,
    ReactiveFormsModule,
    NbInputModule,
    NbSidebarModule
  ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent  implements OnInit {
  loadingModalUpdate = false
  loadingModalCreate = false;
  dialogueRefUpdate:any;
  dialogueRefCreate:any;
  loading = true;
  updateUserForm: FormGroup = <FormGroup> {};
  createUserForm: FormGroup = <FormGroup> {};
  users: UserPag = <UserPag> {}

  constructor(private userService: UserService,
              private messageService: MessageService,
              private formBuilder : FormBuilder,
              private dialogService: NbDialogService,
              private router:Router) {
    this.updateUserForm = this.formBuilder.group({
        publicId: '',
        name: '' ,
        email: '',
        role: '',
        isActive: ''
    });
    this.createUserForm = this.formBuilder.group({
      name: '',
      url: '',
      token: ''
    });
    this.getUsers();
  }

  getUsers() {
    this.loading = true;
    this.userService.getAllUsers().subscribe(data => {
      console.log(data);
      this.users = data;
    });
    this.loading = false;
  }

  ngOnInit() {
  }

  createUser(event:any){
    event.preventDefault()
    this.loadingModalCreate = true
    let data = this.createUserForm.value
    this.messageService.showToast("Criado com sucesso!",'success')
    // this.meshsService.createMeshs(data).subscribe( (result) => {
    //   this.loadingModalCreate = false
    //   this.messageService.showToast(result.message,'success')
    //   this.closeCreate()
    //   this.getMesh()
    // },(err) => {
    //   this.loadingModalCreate = false
    //   this.messageService.showToast(err.message,'danger')
    // })
    this.loadingModalCreate = false
  }
  updateUser(event:any) {
    event.preventDefault()
    this.loadingModalUpdate = true
    let data = this.updateUserForm.value
    console.log("Data")
    console.log(data);
    console.log("updateUserForm")
    console.log(this.updateUserForm.value);
    let user = {
      publicId: data.publicId,
      name: data.nameUpdate,
      email: data.emailUpdate,
      role: data.roleUpdate,
      isActive: data.statusUpdate
    }
    this.userService.updateUser(user, data.publicId).subscribe( (result) => {
      this.loadingModalUpdate = false
      this.messageService.showToast("Atualizado com sucesso",'success')
      this.closeUpdate()
      this.getUsers()
    },(err) => {
      this.loadingModalUpdate = false
      this.messageService.showToast(err.message,'danger')
      this.closeUpdate()
    })
    this.loadingModalUpdate = false
  }
  deleteUser(id:String){
    this.loading=true
    this.messageService.showToast("Deletado com sucesso!",'success')
    // this.meshsService.deleteMeshs(id).subscribe( (result) => {
    //   this.loading=false
    //   this.getMesh()
    // },(err) => {
    //   this.loading=false
    //   this.messageService.showToast(err.message,'danger')
    // })
  }

  userUpdateModal(dialog: TemplateRef<any>,id:String){
    this.users?.content.forEach(element => {
      if(element.publicId === id ){
        console.log(element)
        this.updateUserForm = this.formBuilder.group({
          publicId: element.publicId,
          nameUpdate: element.name,
          emailUpdate: element.email,
          roleUpdate: element.role,
          statusUpdate: element.isActive
        });
      }
    });
    this.dialogueRefUpdate = this.dialogService.open(dialog);
  }
  userCreateModal(dialog: TemplateRef<any>) {
    this.dialogueRefCreate = this.dialogService.open(dialog);
  }
  closeCreate() {
    this.dialogueRefCreate.close();
  }
  closeUpdate() {
    this.dialogueRefUpdate.close();
  }
}

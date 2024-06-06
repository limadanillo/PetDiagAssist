import {Component, OnInit} from '@angular/core';
import {UserService} from '../../core/user/user.service';
import {User} from '../../core/user/user';
import {FormGroup, FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { MessageService  } from '../../shared/message.service';
import {
  NbToastrService,
  NbComponentStatus,
  NbLayoutModule,
  NbSpinnerModule,
  NbCardModule,
  NbFormFieldModule,
  NbInputModule,
  NbButtonModule,
  NbSidebarModule, NbDialogService, NbDialogRef, NbIconModule

} from '@nebular/theme';
import {ThemeModule} from "../../UI/ui.module";
import {BrazilianStates} from "../../core/states/brazilian.states.enum";
import {VeterinaryDialogComponent} from "../../UI/components/dialog/veterinary-dialog";
import {VeterinarianRequest, VeterinarianResponse} from "../../core/veterinarian/veterinarian";
import {VeterinarianService} from "../../core/veterinarian/veterinarian.service";



@Component({

  templateUrl: './user.component.html',
  standalone: true,
  imports: [
    NbLayoutModule,
    ThemeModule,
    NbSpinnerModule,
    NbCardModule,
    NbFormFieldModule,
    ReactiveFormsModule,
    NbInputModule,
    NbButtonModule,
    NbSidebarModule,
    NbIconModule
  ],
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  showPassword = true;
  user: {
    role: string;
    name: string;
    created_at: string;
    publicId: string;
    isActive: boolean;
    email: string;
    updatedAt: string
  } = {
    publicId: '',
    name: '',
    email: '',
    role: '',
    isActive: true,
    created_at: '',
    updatedAt: ''
};
  veterinarianRequest!: VeterinarianRequest;
  veternarianResponse!: VeterinarianResponse;
  id : any ='';
  updateUserSpinner = false;
  userForm: FormGroup = <FormGroup> {};
  loading = true;
  brazilianStates = Object.values(BrazilianStates);
  // Referência para o diálogo aberto, para controle
  veterinaryDialogRef: NbDialogRef<any> | undefined;
  vetUpdate = false;
  vetCreate = false;

  constructor(private userService : UserService,
              private veterinarianService: VeterinarianService,
              private fbuilder: FormBuilder,
              private dialogService: NbDialogService,
              private messageService: MessageService) {

    this.userForm = this.fbuilder.group({
        id: '',
        email: '',
        password: '',
        role: '',
        created_at: ''
    });

      this.userService.getCurrentUser().subscribe( (result) => {
        this.user = result.data
        this.userForm = this.fbuilder.group({
          id: this.user.publicId,
          email: this.user.email,
          password: '',
          role: this.user.role,
          created_at: this.user.created_at
      });
        this.veterinarianService.getVeterinarianByUserId(this.user.publicId).subscribe( (result) => {
          this.veternarianResponse = result
          this.vetCreate = false;
          this.vetUpdate = true;2
          this.loading=false;
        },(err) =>{
          this.messageService.showToast(err.error,'danger')
          this.loading=false;
        })
      this.loading=false
    },(err) =>{
      this.loading=false
      this.messageService.showToast(err.error,'danger')
    })
  }

  ngOnInit(): void {

  }

  updateUser(event:any){
    this.loading=true
    event.preventDefault()

    let data = this.userForm.value
    if(data.password == '' ) delete data.password
    this.userService.updateCurrentUser(data).subscribe( (result) => {
      this.user = result.data
      this.messageService.showToast('User has been updated','success')
      this.loading=false

    },(err) =>{
      this.loading=false
      this.messageService.showToast(err.error,'danger')
    })

  }

  openVeterinaryDialog(): void {
    this.veterinaryDialogRef = this.dialogService.open(VeterinaryDialogComponent, {
      context: {
        title: 'Add Veterinary Info',
        initialData : this.veternarianResponse
      }
    });

    this.veterinaryDialogRef.onClose.subscribe(data => {
      if (data) {
        console.log('Veterinary data:', data);
        // Processa os dados retornados
        this.veternarianResponse.councilType = data.councilType;
        this.veternarianResponse.councilState = data.councilState;
        this.veternarianResponse.councilNumber = data.councilNumber;
        this.saveVetInfo(data);
      }
    });
  }


  saveVetInfo(data: any): void {
    // Save veterinary data
  }

  getVetInfo(): string {
    // Get veterinary data
    return this.veternarianResponse!.councilNumber + ' - ' + this.veternarianResponse!.councilType + ' - ' + this.veternarianResponse!.councilState;
  }

  getInputType() {
    if (this.showPassword) {
      return 'text';
    }
    return 'password';
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
  }
}

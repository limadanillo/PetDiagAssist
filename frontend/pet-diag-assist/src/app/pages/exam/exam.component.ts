import {Component, OnInit,TemplateRef} from '@angular/core';
import {FormGroup, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { ReplaySubject,Observable } from "rxjs";
import { MessageService  } from '../../shared/message.service';
import {
  NbButtonModule,
  NbCardModule,
  NbComponentStatus,
  NbDialogService, NbFormFieldModule, NbIconModule, NbInputModule, NbLayoutModule, NbSidebarModule, NbSpinnerModule

} from '@nebular/theme';
import {ExamService} from "../../core/exam/exam.service";
import {Exam, ExamResponse} from "../../core/exam/exame";
import {ImageService} from "../../core/image/image.service";
import {ThemeModule} from "../../UI/ui.module";



@Component({
  selector: 'ngx-exam',
  templateUrl: './exam.component.html',
  standalone: true,
  imports: [
    NbLayoutModule,
    ThemeModule,
    NbCardModule,
    NbSpinnerModule,
    NbButtonModule,
    NbIconModule,
    NbFormFieldModule,
    ReactiveFormsModule,
    NbInputModule,
    NbSidebarModule
  ],
  styleUrls: ['./exam.component.scss']
})
export class ExamComponent implements OnInit {

  uploadTemplateForm: FormGroup = <FormGroup> {};
  loading = false;
  loadingModal = false
  examList:Exam[]=<any>[]
  uploadModal:any;
  fileTemp:string=''
  file!: File;
  constructor(private examService : ExamService,
    private imageService: ImageService,
    private messageService: MessageService,
              private dialogService: NbDialogService,
              private fbuilder: FormBuilder) {
    this.uploadTemplateForm = this.fbuilder.group({
      examType: '',
      animalId: '' ,
      expirationDate: '',
      imageReferenceId: '',
      imageType: '',
      name: '',
      value:''
  });
  this.getAllExam(0, 10)

  }

  ngOnInit(): void {


  }



  openUploadModal(dialog: TemplateRef<any>){
      this.uploadModal = this.dialogService.open(dialog, { context: '' });
  }
  closeUploadModal(){
    this.uploadModal.close()
  }
  onFileSelected(event:any) {
    this.file = event.target.files[0];
    this.convertFile(event.target.files[0]).subscribe( (base64:any) => {
      this.fileTemp = base64;
    });
  }

  convertFile(file : File) : Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = (event:any) => result.next(btoa((event.target.result).toString()));
    return result;
  }


  uploadTemplate(event:any){
    event.preventDefault()
    this.loadingModal = true
    let data = this.uploadTemplateForm.value

    data = {"template":{"name":data.name,"value":this.fileTemp}}

    this.examService.createExam(data).subscribe((result)=> {
      this.loadingModal=false
      this.closeUploadModal()
      this.imageService.uploadImage( this.file, result.publicId).subscribe((result)=> {
        this.messageService.showToast(result.message,'success');
      },(err)=> {
        this.messageService.showToast(err.message,'danger')
      });
      this.getAllExam(0, 10)

    },(err)=> {
      this.loadingModal=false
      this.messageService.showToast(err.message,'danger')
    })
  }
  deleteTemplate(name:string){
    this.loading=true
    // this.examService.deleteTemplate(name).subscribe((result)=> {
    //     this.loading=false
    //     this.messageService.showToast(result.message,'success')
    //     this.getAllExam(0,10)
    // },(err)=> {
    //   this.loading=false
    //   this.messageService.showToast(err.message,'danger')
    // })
  }

  getAllExam(page: number, size: number){
    this.loading=true
    this.examService.getAllExams(page, size).subscribe( (result) => {
      this.loading=false
      this.examList=result.content;
    },(err)=> {
      this.loading=false
      this.messageService.showToast(err.message,'danger')
    })

  }
}

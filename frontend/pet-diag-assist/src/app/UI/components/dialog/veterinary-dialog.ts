import {
  NbButtonModule,
  NbCardModule,
  NbDialogRef,
  NbFormFieldModule, NbInputModule,
  NbSelectWithAutocompleteModule
} from "@nebular/theme";
import {BrazilianStates} from "../../../core/states/brazilian.states.enum";
import {Component, Input, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {CouncilType} from "../../../core/council/council.type";

@Component({
  selector: 'app-veterinary-dialog',
  templateUrl: './veterinary-dialog.component.html',
  standalone: true,
  imports: [
    NbCardModule,
    NbButtonModule,
    NbFormFieldModule,
    NbSelectWithAutocompleteModule,
    ReactiveFormsModule,
    NbInputModule,
    NgForOf
  ],
  styleUrls: ['./veterinary.dialog.scss']
})
export class VeterinaryDialogComponent implements OnInit{
  @Input() title: string | undefined;
  @Input() initialData?: any;
  @Input() vetForm!: FormGroup;
  brazilianStates = Object.values(BrazilianStates);
  councilTypes = Object.values(CouncilType);
  brazilianStateSeleted = '';
  councilTypeSeleted = '';

  constructor(  private formBuilder: FormBuilder,private dialogRef: NbDialogRef<VeterinaryDialogComponent>,
              ) {}

  ngOnInit(): void {
    this.vetForm = this.formBuilder.group({
      councilNumber: [this.initialData?.councilNumber || '', Validators.required],
      councilState: [this.initialData?.councilState || '', Validators.required],
      councilType: [this.initialData?.councilType || '', Validators.required]
    });
    this.brazilianStateSeleted = this.initialData?.councilState;
    this.councilTypeSeleted = this.initialData?.councilType;
  }

  onSave() {
    console.log(this.vetForm.value);
    if (this.vetForm.valid) {
      this.dialogRef.close(this.vetForm.value);
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}

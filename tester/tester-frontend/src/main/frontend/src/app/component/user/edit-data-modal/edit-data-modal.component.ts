import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgbModal, ModalDismissReasons, NgbModalRef, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-data-modal',
  templateUrl: './edit-data-modal.component.html',
  styleUrls: ['../../../app.component.css']
})
export class EditDataModalComponent implements OnInit{
  myform: FormGroup;
  data: FormControl;
  dataType: FormControl;
  types: string[] = ['Igen', 'Nem'];
  @Input() defaultValue: string; 
  @Input() title: string;
  @Input() trueAnswer: boolean;
  @Output() output: EventEmitter<string[]> = new EventEmitter();
  
  constructor(private activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.createFormControls();
    this.createForm();
  }

  createFormControls() { 
    this.data = new FormControl(this.defaultValue || '', [
      Validators.required,
      this.noWhitespaceValidator
    ]);
    let defaultType = this.trueAnswer !== null ? (this.trueAnswer ? "Igen" : "Nem") : null;
    this.dataType = new FormControl(defaultType);
  }

  createForm() { 
    this.myform = new FormGroup({
      data: this.data,
      dataType: this.dataType
    });
  }

  onSubmit() {
    if (this.myform.valid) {
      this.output.emit([this.data.value.trim(), this.dataType.value]);
      this.activeModal.close('Close click')
    } else {
      console.log("Form is not valid!")
    }
  }

  noWhitespaceValidator(control: FormControl) {
    let isWhitespace = (control.value || '').trim().length === 0;
    let isValid = !isWhitespace;
    return isValid ? null : { 'whitespace': true }
  }
}

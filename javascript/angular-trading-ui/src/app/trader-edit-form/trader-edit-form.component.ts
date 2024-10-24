import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Trader } from '../trader';

@Component({
  selector: 'app-trader-edit-form',
  templateUrl: './trader-edit-form.component.html',
  styleUrls: ['./trader-edit-form.component.css']
})
export class TraderEditFormComponent {
  


  constructor (
    public dialogRef: MatDialogRef<TraderEditFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Trader
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}

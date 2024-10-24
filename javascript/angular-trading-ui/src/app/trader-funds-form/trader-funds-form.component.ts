import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-trader-funds-form',
  templateUrl: './trader-funds-form.component.html',
  styleUrls: ['./trader-funds-form.component.css']
})
export class TraderFundsFormComponent {

  constructor (
    public dialogRef: MatDialogRef<TraderFundsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}

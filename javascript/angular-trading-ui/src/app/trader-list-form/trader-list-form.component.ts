import { Component } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Trader } from '../trader';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-trader-list-form',
  templateUrl: './trader-list-form.component.html',
  styleUrls: ['./trader-list-form.component.css']
})
export class TraderListFormComponent {

  constructor(
    public dialogRef: MatDialogRef<TraderListFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Trader) {}

    onNoClick(): void {
      this.dialogRef.close();
    }
}

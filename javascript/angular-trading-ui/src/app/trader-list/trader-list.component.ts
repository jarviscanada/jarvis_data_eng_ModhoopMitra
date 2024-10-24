import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader'
import { MatDialog } from '@angular/material/dialog';
import { TraderListFormComponent } from '../trader-list-form/trader-list-form.component';

@Component({
  selector: 'app-trader-list',
  templateUrl: './trader-list.component.html',
  styleUrls: ['./trader-list.component.css']
})
export class TraderListComponent implements OnInit {

  traders: Trader[] = [];
  columns: string[] = [];

  constructor(private traderListService: TraderListService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.traderListService.getDataSource().subscribe(data => {
      this.traders = data;
    })
    this.columns = this.traderListService.getColumns();
    
  }

  deleteTrader(id: number): void {
    this.traderListService.deleteTrader(id).subscribe({
      next: response => {
        this.traders = this.traders.filter(t => t.id !== id)
        console.log('Delete success', response)
      },
      error: error => {
        console.log('Delete failed', error)
      }
    });
  }

  openDialog() {
    const dialogRef = this.dialog.open(TraderListFormComponent, {
      width: '40vw',
      data: {
        key: '',
        id: undefined,
        firstName: '',
        lastName: '',
        dob: '',
        country: '',
        email: ''
      }
    })

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.traderListService.addTrader(result).subscribe({
          next: response => {
            this.traders.push(response)
            this.traders = [...this.traders]
            console.log('Add success', response)
          },
          error: error => {
            console.log('Add failed', error)
          }
        })
      }
    })
  }
}

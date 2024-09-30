import { Component, OnChanges, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { TraderFundsFormComponent } from '../trader-funds-form/trader-funds-form.component';
import { TraderEditFormComponent } from '../trader-edit-form/trader-edit-form.component';

@Component({
  selector: 'app-trader-account',
  templateUrl: './trader-account.component.html',
  styleUrls: ['./trader-account.component.css']
})
export class TraderAccountComponent implements OnInit {

  trader: Trader = {
    key: '',
    id: -1,
    firstName: '',
    lastName: '',
    dob: '',
    country: '',
    email: '',
    amount: 0,
    actions: ''
  };

  amount: number = 0;

  constructor(
    private traderListService: TraderListService, 
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      console.log(params['id'])
      this.traderListService.getTrader(params['id']).subscribe(data => {
        this.trader = data;
        console.log(data)
      })
    })
    console.log(this.trader)
  }

  openDialogDeposit(): void {
    const dialogRef = this.dialog.open(TraderFundsFormComponent, {
      width: '40vw',
      data: this.amount
    })
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.traderListService.addFunds(this.trader.id, result).subscribe(
          {
            next: response => {
              this.trader.amount = response.amount
              console.log('Deposit success', response)
            },
            error: error => {
              console.log('Deposit failed', error)
            }    
          }
        )
      }
    })
  }

  openDialogWithdraw(): void {
    const dialogRef = this.dialog.open(TraderFundsFormComponent, {
      width: '40vw',
      data: this.amount
    })
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.traderListService.subtractFunds(this.trader.id, result).subscribe({
          next: response => {
            this.trader.amount = response.amount
            console.log('Withdraw success', response)
          },
          error: error => {
            console.log('Withdraw failed', error)
          }
        })
      }
    })
  }

  openDialogEdit(): void {
    const dialogRef = this.dialog.open(TraderEditFormComponent, {
      width: '40vw',
      data: {
        firstName: this.trader.firstName,
        lastName: this.trader.lastName,
        email: this.trader.email,
      }
    })

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.traderListService.editTrader(this.trader.id, result).subscribe({
          next: response => {
            this.trader = response
            console.log('Update success', response)
          },
          error: error => {
            console.error('Update failed', error)
          }
        });
      }
    })
  }

}

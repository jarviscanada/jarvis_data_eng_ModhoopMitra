import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader';
import { ActivatedRoute } from '@angular/router';

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

  constructor(private traderListService: TraderListService, private route: ActivatedRoute) {}

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

}

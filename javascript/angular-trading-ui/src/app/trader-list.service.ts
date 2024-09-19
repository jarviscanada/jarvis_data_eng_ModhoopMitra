import { Injectable } from '@angular/core';
import { Trader } from './trader';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TraderListService {

  traderList:Trader[] = [
    {
      "key": "1",
      "id": 1,
      "firstName": "Mike",
      "lastName": "Spencer",
      "dob": "1990-01-01",
      "country": "Canada",
      "email": "mike@test.com",
      "amount": 0,
      "actions": "<button (click)='deleteTrader'>Delete Trader</button>"
    },
    {
        "key": "2",
        "id": 2,
        "firstName": "Hellen",
        "lastName": "Miller",
        "dob": "1990-01-01",
        "country": "Austria",
        "email": "hellen@test.com",
        "actions": "<button (click)='deleteTrader'>Delete Trader</button>",
        "amount": 0
    },
  ]


  constructor() { }

  getDataSource(): Observable<Trader[]> {
    return of(this.traderList)
  }

  getColumns(): string[] {
    return ['First Name', 'Last Name', 'Email', 'DateOfBirth', 'Country', 'Actions']
  }

  getTrader(id:number): Observable<Trader> {
    const trader = this.traderList.filter(t => t.id == id)[0];
    return of(trader);
  }

  deleteTrader(id: number): void {
    //const index:number = this.traderList.findIndex(trader => trader.id === id)
    //console.log(index)
    //if (index != -1) {
      this.traderList = this.traderList.filter(t => t.id !== id);
      //this.traderList.filter(index, 1);
      //this.traderList = [...this.traderList];
      //console.log(this.traderList);
    //}
  }

  addTrader(trader:Trader): Promise<any> {
    const id:number = Math.floor(10000 + Math.random() * 90000);
    trader.id = id;
    trader.key = id.toString();
    this.traderList.push(trader);
    this.traderList = [...this.traderList]
    return Promise.resolve();
  }

}

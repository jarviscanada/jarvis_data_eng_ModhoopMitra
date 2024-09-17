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

}

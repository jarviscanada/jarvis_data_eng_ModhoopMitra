import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Trader } from './trader';
import { Observable, of, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TraderListService {

  traderList: Trader[] = []

  private url: string = 'http://localhost:3004/';


  constructor(private httpClient: HttpClient) { }

  getDataSource(): Observable<Trader[]> {
    return this.httpClient.get<Trader[]>(this.url + 'traders')
  }

  getColumns(): string[] {
    return ['First Name', 'Last Name', 'Email', 'DateOfBirth', 'Country', 'Actions']
  }

  getTrader(id: number): Observable<Trader> {
    return this.httpClient.get<Trader>(this.url + 'traders/' + id);
  }

  editTrader(id: number, data: any): Observable<any> {
    return this.httpClient.patch(this.url + 'traders/' + id, {
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
    })
    // this.traderList.filter(t => t.id === id)[0].firstName = data.firstName
    // this.traderList.filter(t => t.id === id)[0].lastName = data.lastName
    // this.traderList.filter(t => t.id === id)[0].email = data.email
  }

  deleteTrader(id: number): Observable<any> {
    return this.httpClient.delete(this.url + 'traders/' + id)
    //this.traderList = this.traderList.filter(t => t.id !== id);
  }

  addTrader(trader: Trader): Observable<any> {
    const id:number = Math.floor(10000 + Math.random() * 90000);
    trader.id = id;
    trader.key = id.toString();
    return this.httpClient.post(this.url + 'traders', trader)
  }

  addFunds(id: number, amount: number): Observable<any> {
    //this.traderList.filter(t => t.id === id)[0].amount += amount;
    return this.getTrader(id).pipe(
      switchMap(trader => {
        const newAmount = trader.amount + amount
        return this.httpClient.patch(this.url + 'traders/' + id, {
          amount: newAmount
        })
      })
    )
  }

  subtractFunds(id: number, amount: number): Observable<any> {
    //this.traderList.filter(t => t.id === id)[0].amount -= amount;
    return this.getTrader(id).pipe(
      switchMap(trader => {
        const newAmount = trader.amount - amount
        return this.httpClient.patch(this.url + 'traders/' + id, {
          amount: newAmount
        })
      })
    )
  }

}

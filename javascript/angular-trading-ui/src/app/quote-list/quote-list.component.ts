import { Component, OnInit } from '@angular/core';
import { QuoteListService } from '../quote-list.service';
import { Quote } from '../quote';

@Component({
  selector: 'app-quote-list',
  templateUrl: './quote-list.component.html',
  styleUrls: ['./quote-list.component.css']
})
export class QuoteListComponent implements OnInit {

  quotes: Quote[] = []
  columns: string[] = []

  constructor(private quoteListService: QuoteListService) {}

  ngOnInit(): void {
   this.quoteListService.getDataSource().subscribe(data => {
    this.quotes = data;
   })
   this.columns = this.quoteListService.getColumns(); 
  }

}

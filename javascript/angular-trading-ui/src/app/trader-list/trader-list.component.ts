import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader';

@Component({
  selector: 'app-trader-list',
  templateUrl: './trader-list.component.html',
  styleUrls: ['./trader-list.component.css']
})
export class TraderListComponent implements OnInit {

  traders: Trader[] = [];
  columns: string[] = [];

  constructor(private traderListService: TraderListService) {}

  ngOnInit(): void {
    this.traderListService.getDataSource().subscribe(data => {
      this.traders = data;
    })

    this.columns = this.traderListService.getColumns();
  }
}

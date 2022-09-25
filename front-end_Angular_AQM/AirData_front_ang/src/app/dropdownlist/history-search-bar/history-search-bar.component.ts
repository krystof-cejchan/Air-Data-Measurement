import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-history-search-bar',
  templateUrl: './history-search-bar.component.html',
  styleUrls: ['./history-search-bar.component.css']
})
export class HistorySearchBarComponent implements OnInit {

  minDate: Date;
  maxDate: Date;

  constructor() {
    var date = new Date();
    this.minDate = new Date(2022, 8, 23);
    this.maxDate = date;//new Date(date.getFullYear().toPrecision(1), date.getMonth(), date.getDay() - 1);
    console.log(this.minDate); console.log(this.maxDate);

  }

  ngOnInit(): void {

  }

}

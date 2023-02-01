import { DOCUMENT, formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from '@angular/router';
import { AirData } from 'src/app/airdata';
import { openSnackBar } from 'src/app/errors/custom in-page errors/snack-bar/server_error/custom-error-snackbar';
import { LatestDataService } from './latest-data.service';
import { MatSnackBar } from "@angular/material/snack-bar";
import { SubSink } from "subsink";

@Component({
  selector: 'app-latest-data',
  templateUrl: './latest-data.component.html',
  styleUrls: ['./latest-data.component.scss']
})
export class LatestDataComponent implements OnInit, IComponent, OnDestroy {

  public htmlToAdd = '';

  private subs = new SubSink()
  public airdatas: AirData[] = [];
  private formattedAirDatas: AirData[] = [];
  public selectedIndex: number = -1;
  public showLoading: boolean = true;

  constructor(@Inject(DOCUMENT) document: Document,
    private route: ActivatedRoute,
    private router: Router,
    private latestDataService: LatestDataService,
    private snackBar: MatSnackBar) {
  }
  ngOnDestroy(): void {
    this.subs.unsubscribe()
  }
  getTitle(): string {
    return "Nejnovější data";
  }

  ngOnInit(): void {
    this.getAirDatas();
  }

  /**
   * tries to get AirData from the back-end
   */
  public getAirDatas(): void {
    this.subs.add(this.latestDataService.getLatestData().subscribe({
      next: (response: AirData[]) => {
        this.formattedAirDatas = response;
        try {
          this.formattedAirDatas.forEach(airdata => {
            let formattedAitDate = airdata;
            formattedAitDate.receivedDataDateTime = this.formatDate(new Date(airdata.receivedDataDateTime));
            this.airdatas.push(formattedAitDate);
          })
        } catch (error) {
          this.airdatas = response;
        }
      },
      error: () => {
        openSnackBar(this.snackBar)
      },
      complete: () => this.showLoading = false
    }));

  }

  /**
   * uses provided id and hash from data obtained from database to redirect a user to a page where details of the record found 
   * by provided airdataInfo will be displayed, if nothing is found with such id and hash,
   * the redirection will be executed anyway but 
   * error message will be displayed in the component where the user would be redirected to
   * @param airdataInfo 
   */
  public showDetails(airdataInfo: { id: number, hash: string }) {
    this.router.navigate([`/data-detaily/${airdataInfo.id}/${airdataInfo.hash}`], { relativeTo: this.route });
  }

  /**
   * @param date Date taken from back-end
   * @returns formatted date as string
   */
  private formatDate(date: Date): string {
    return formatDate(date, 'dd-MM • HH:mm:ss', "en-US");
  }

  /**
   * scrolls to the element only if the element is found with provided id
   * @param id id of the element
   */
  scrollToElement(id: string): void {
    document.getElementById(id)!.scrollIntoView({
      behavior: "smooth",
      block: "start",
      inline: "nearest"
    });
  }

  public setSelectedIndex(_index: number) {
    this.selectedIndex = _index;
  }
}

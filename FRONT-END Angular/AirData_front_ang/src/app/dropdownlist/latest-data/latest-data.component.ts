import { DOCUMENT, formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AirData } from 'src/app/airdata';
import { openSnackBar } from 'src/app/errors/custom in-page errors/snack-bar/custom-error-snackbar';
import { LatestDataService } from './latest-data.service';
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-latest-data',
  templateUrl: './latest-data.component.html',
  styleUrls: ['./latest-data.component.scss']
})
export class LatestDataComponent implements OnInit, IComponent {

  public htmlToAdd = '';


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
    this.latestDataService.getLatestData().subscribe(
      (response: AirData[]) => {
        //console.log(response);

        this.formattedAirDatas = response;
        // console.log(response)
        try {
          this.formattedAirDatas.forEach(airdata => {
            let formattedAitDate = airdata;
            //formatting date to more humanly readable date
            formattedAitDate.receivedDataDateTime = this.formatDate(new Date(airdata.receivedDataDateTime));
            //background picture set to folder "assets/imgs/faculties/" where images are stored ... not needed anymore as location table in db has been updated to contain bcg pic URL
            //formattedAitDate.bcgPictureUrl = "assets/imgs/faculties/" + airdata.locationId.name_short + "_cover.jpg";
            this.airdatas.push(formattedAitDate);
          })
          this.showLoading = false;
        } catch (error) {
          this.airdatas = response;
          this.showLoading = false;
        }
      },
      () => {
        this.showLoading = false;
        openSnackBar(this.snackBar)
      }
    );

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

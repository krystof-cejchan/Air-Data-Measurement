import { HttpClientModule, HttpClient } from "@angular/common/http";
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AirDataService } from './air-data.service';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ErrorsComponent } from './errors/errors.component';
import { AboutProjectComponent } from './about-project/about-project.component';
import { HowItWorksComponent } from './how-it-works/how-it-works.component';
import { LatestDataComponent } from './dropdownlist/latest-data/latest-data.component';
import { GraphsComponent } from './dropdownlist/graphs/graphs.component';
import { LeaderboardComponent } from './dropdownlist/leaderboard/leaderboard.component';
import { HistorySearchBarComponent } from './dropdownlist/history-search-bar/history-search-bar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgApexchartsModule } from 'ng-apexcharts';
import { AirDataDetailsComponent } from './air-data-details/air-data-details.component';
import { MainPageComponent } from './main-page/main-page.component';
import { APP_INITIALIZER } from '@angular/core';
import { environment } from "src/environments/environment";


export function initializeApp(http: HttpClient) {
  return (): Promise<any> => {
    const initUrl = environment.APP_INITIALIZER_URL;
    var locations: string[] = ["a", "b"];
    /*http.get<string[]>(`${initUrl}/location`).subscribe(async (response: string[]) => {
      locations = response;
      var counter = 0;
      const msToWait = 400, msMaxToWait = 5000;
      while (locations.length < 1 && counter < (msMaxToWait / msToWait)) {
        await new Promise(f => setTimeout(f, msToWait));
        counter++;
      }
    });*/

    //same goes for leaderboardtype

    environment.Locations = locations;
    console.log(environment.Locations)
    return Promise.resolve();
  }
}

@NgModule({
  declarations: [
    AppComponent,
    ErrorsComponent,
    AboutProjectComponent,
    HowItWorksComponent,
    LatestDataComponent,
    GraphsComponent,
    LeaderboardComponent,
    HistorySearchBarComponent,
    AirDataDetailsComponent,
    MainPageComponent,
  ],
  imports: [
    BrowserModule, HttpClientModule, AppRoutingModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatInputModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatTooltipModule,
    NgApexchartsModule,
    AppRoutingModule
  ],
  providers: [AirDataService, { provide: APP_INITIALIZER, useFactory: initializeApp, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(http: HttpClient) { }
}

import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AirDataService } from './air-data.service';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ErrorsComponent } from './errors/errors.component';
import { AboutProjectComponent } from './about-project/about-project.component';
import { HowItWorksComponent } from './how-it-works/how-it-works.component';
import { LatestDataComponent } from './dropdownlist/latest-data/latest-data.component';
import { HistoryComponent } from './dropdownlist/history/history.component';
import { GraphsComponent } from './dropdownlist/graphs/graphs.component';
import { LeaderboardComponent } from './dropdownlist/leaderboard/leaderboard.component';


@NgModule({
  declarations: [
    AppComponent,
    ErrorsComponent,
    AboutProjectComponent,
    HowItWorksComponent,
    LatestDataComponent,
    HistoryComponent,
    GraphsComponent,
    LeaderboardComponent
  ],
  imports: [
    BrowserModule, HttpClientModule, AppRoutingModule
  ],
  providers: [AirDataService],
  bootstrap: [AppComponent]
})
export class AppModule { }

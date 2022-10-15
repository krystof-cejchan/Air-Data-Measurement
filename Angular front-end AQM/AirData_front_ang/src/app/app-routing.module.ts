import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ErrorsComponent } from './errors/errors.component';
import { AboutProjectComponent } from './about-project/about-project.component';
import { HowItWorksComponent } from './how-it-works/how-it-works.component';
import { LatestDataComponent } from './dropdownlist/latest-data/latest-data.component';
import { HistorySearchBarComponent } from './dropdownlist/history-search-bar/history-search-bar.component';
import { GraphsComponent } from './dropdownlist/graphs/graphs.component';
import { LeaderboardComponent } from './dropdownlist/leaderboard/leaderboard.component';
import { AirDataDetailsComponent } from "./air-data-details/air-data-details.component";
import { MainPageComponent } from "./main-page/main-page.component";


const routes: Routes = [
  {
    path: '', component: MainPageComponent
  },

  {
    path: 'funkce/:spec', pathMatch: 'full', component: LatestDataComponent
  },
  {
    path: 'nejnovejsi-data', pathMatch: 'full', component: LatestDataComponent
  },
  {
    path: 'nekdejsi-data', component: HistorySearchBarComponent
  },
  {
    path: 'nekdejsi-data/:date', component: HistorySearchBarComponent
    // children: [{ path: 'prumer', component: HistoryComponent }]
  },
  {
    path: 'grafy', pathMatch: 'full', component: GraphsComponent
  },
  {
    path: 'zebricky', pathMatch: 'full', component: LeaderboardComponent
  },
  {
    path: 'projekt', pathMatch: 'full', component: AboutProjectComponent
  },
  {
    path: 'jaktofunguje', pathMatch: 'full', component: HowItWorksComponent
  },
  {
    path: 'data-detaily/:id/:hash', pathMatch: 'full', component: AirDataDetailsComponent
  },
  {
    path: '**', component: ErrorsComponent
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule { }


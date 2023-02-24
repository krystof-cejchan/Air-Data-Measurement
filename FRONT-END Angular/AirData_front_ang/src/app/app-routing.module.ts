import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, RouterStateSnapshot, TitleStrategy } from '@angular/router';
import { ErrorsComponent } from './errors/errors.component';
import { AboutProjectComponent } from './about-project/about-project.component';
import { LatestDataComponent } from './dropdownlist/latest-data/latest-data.component';
import { HistorySearchBarComponent } from './dropdownlist/history-search-bar/history-search-bar.component';
import { GraphsComponent } from './dropdownlist/graphs/graphs.component';
import { LeaderboardComponent } from './dropdownlist/leaderboard/leaderboard.component';
import { AirDataDetailsComponent } from "./air-data-details/air-data-details.component";
import { MainPageComponent } from "./main-page/main-page.component";
import { DevComponent } from "./dev/dev/dev.component";
import { Title } from '@angular/platform-browser';
import { ForecastComponent } from "./forecast/forecast.component";
import { ConfirmationComponent } from "./newsletter subscription/confirmation/confirmation.component";
import { CancellationComponent } from "./newsletter subscription/cancellation/cancellation.component";
import { ManagerComponent } from "./newsletter subscription/manager/manager.component";


const routes: Routes = [
  {
    path: '', title: 'Úvodní strana', component: MainPageComponent
  },
  {
    path: 'nejnovejsi-data', pathMatch: 'full', title: 'Nejnovější data', component: LatestDataComponent
  },
  {
    path: 'nekdejsi-data', title: 'Historie', component: HistorySearchBarComponent
  },
  {
    path: 'nekdejsi-data/:start/:end', title: 'Historie', component: HistorySearchBarComponent
    // children: [{ path: 'prumer', component: HistoryComponent }]
  },
  {
    path: 'grafy', pathMatch: 'full', title: 'Grafy', component: GraphsComponent
  },
  {
    path: 'zebricky', pathMatch: 'full', title: 'Žebříčky', component: LeaderboardComponent
  },
  {
    path: 'projekt', pathMatch: 'full', title: 'Projekt', component: AboutProjectComponent
  },
  {
    path: 'data-detaily/:id/:hash', pathMatch: 'full', title: 'Detaily', component: AirDataDetailsComponent
  },
  {
    path: 'dev', pathMatch: 'full', title: 'Pro vývojáře', component: DevComponent
  },
  {
    path: 'predpoved', pathMatch: 'full', title: 'Předpověď počasí', component: ForecastComponent
  },
  {
    path: 'predpoved/:day/:time', pathMatch: 'full', title: 'Předpověď počasí', component: ForecastComponent
  },
  {
    path: 'predplatne/spravce', pathMatch: 'full', title: 'Správa odběru', component: ManagerComponent
  },
  {
    path: 'predplatne/potvrzeni/:id/:hash', pathMatch: 'full', title: 'Potvrzení odběru', component: ConfirmationComponent
  },
  {
    path: 'predplatne/zruseni/:id/:hash', pathMatch: 'full', title: 'Zrušení odběru', component: CancellationComponent
  },
  {
    path: '**', title: 'Vyskytla se chyba', component: ErrorsComponent
  }
];

@Injectable({ providedIn: 'root' })
export class CustomTitleStrategy extends TitleStrategy {
  constructor(private readonly title: Title) {
    super();
  }

  override updateTitle(routerState: RouterStateSnapshot) {
    const title = this.buildTitle(routerState);
    const suffix = " [UPočasí]"
    if (title !== undefined)
      this.title.setTitle(title + suffix);
    else
      this.title.setTitle(suffix);
  }
}

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: [{ provide: TitleStrategy, useClass: CustomTitleStrategy },]
})
export class AppRoutingModule { }


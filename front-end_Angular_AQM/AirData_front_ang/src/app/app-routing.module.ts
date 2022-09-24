import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ErrorsComponent } from './errors/errors.component';
import { AboutProjectComponent } from './about-project/about-project.component';
import { HowItWorksComponent } from './how-it-works/how-it-works.component';
import { LatestDataComponent } from './dropdownlist/latest-data/latest-data.component';


const routes: Routes = [
  {
    path: '', component: LatestDataComponent
  },

  {
    path: 'funkce/:spec', pathMatch: 'full', component: LatestDataComponent
  },
  {
    path: 'projekt', pathMatch: 'full', component: AboutProjectComponent
  },
  {
    path: 'jaktofunguje', pathMatch: 'full', component: HowItWorksComponent
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


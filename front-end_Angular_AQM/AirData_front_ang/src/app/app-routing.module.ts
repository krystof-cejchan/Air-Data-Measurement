import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component'
import { ApiDataComponent } from './api-data/api-data.component'


const routes: Routes = [
  {
    path: '', pathMatch: 'full', component: AppComponent
  },

  //{ path: '**', component: ErrorsComponent },
  { path: 'api', pathMatch: 'full', component: ApiDataComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule { }


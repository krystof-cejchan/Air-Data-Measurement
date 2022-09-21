import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component'
import { ApiDataComponent } from './api-data/api-data.component'
import { ErrorsComponent } from './errors/errors.component'


const routes: Routes = [
  { path: '**', component: ErrorsComponent },

  { path: '', component: AppComponent },


  { path: 'api', component: ApiDataComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule { }


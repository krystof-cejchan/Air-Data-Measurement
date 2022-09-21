import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AirDataService } from './air-data.service';

import { AppComponent } from './app.component';
import { ApiDataComponent } from './api-data/api-data.component';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    ApiDataComponent,
    ApiDataComponent
  ],
  imports: [
    BrowserModule, HttpClientModule, AppRoutingModule
  ],
  providers: [AirDataService],
  bootstrap: [AppComponent]
})
export class AppModule { }

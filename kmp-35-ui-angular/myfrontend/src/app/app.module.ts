import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";

import {MymaterialModule} from "./module/mymaterial/mymaterial.module";
import {AppComponent} from './app.component';
import {DashboardComponent} from './component/dashboard/dashboard.component';


const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'dashboard', component: DashboardComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    [RouterModule.forRoot(routes)],
    MymaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

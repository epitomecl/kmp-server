import './vendor.ts';

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {Ng2Webstorage} from "ngx-webstorage";

import {MymonoCoreModule} from "./core";
import {MymonoSharedModule} from "./shared";
import {ActiveMenuDirective, AppComponent, NavbarComponent} from './layouts';

import {MyDashboardComponent} from './my-dashboard/my-dashboard.component';
import {MyTableComponent} from './my-table/my-table.component';
import {MymonoAppRoutingModule} from './app-routing.module';
import {PageRibbonComponent} from "./layouts/profiles/page-ribbon.component";
import {MyMatModule} from "./my-mat/my-mat.module";

@NgModule({
  imports: [
    BrowserModule,
    MymonoAppRoutingModule,
    Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
    MymonoSharedModule,
    MymonoCoreModule,
    MyMatModule
  ],
  declarations: [
    AppComponent,
    NavbarComponent,
    PageRibbonComponent,
    ActiveMenuDirective,

    MyDashboardComponent,
    MyTableComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

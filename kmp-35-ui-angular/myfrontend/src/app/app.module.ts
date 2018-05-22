import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';
import {
  MatButtonModule,
  MatCardModule,
  MatGridListModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
  MatPaginatorModule,
  MatSidenavModule,
  MatSortModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {Ng2Webstorage} from "ngx-webstorage";

import {MymonoCoreModule} from "./core";
import {MymonoSharedModule} from "./shared";
import {ActiveMenuDirective, AppComponent, NavbarComponent} from './layouts';

import {MyDashboardComponent} from './my-dashboard/my-dashboard.component';
import {MyTableComponent} from './my-table/my-table.component';
import {MymonoAppRoutingModule} from './app-routing.module';

@NgModule({
  imports: [
    BrowserModule,
    MymonoAppRoutingModule,
    Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
    MymonoSharedModule,
    MymonoCoreModule,

    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],
  declarations: [
    AppComponent,
    NavbarComponent,
    MyDashboardComponent,
    MyTableComponent,

    ActiveMenuDirective
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

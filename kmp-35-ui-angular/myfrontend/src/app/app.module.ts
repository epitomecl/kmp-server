import './vendor.ts';

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClient} from "@angular/common/http";
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
import {MissingTranslationHandler, TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {JhiConfigService, missingTranslationHandler, translatePartialLoader} from "ng-jhipster";
import {Ng2Webstorage} from "ngx-webstorage";

import {MymonoCoreModule} from "./core";
import {MymonoSharedModule} from "./shared";
import {ActiveMenuDirective, AppComponent, NavbarComponent} from './layouts';

import {MyDashboardComponent} from './my-dashboard/my-dashboard.component';
import {MyTableComponent} from './my-table/my-table.component';
import {MymonoAppRoutingModule} from './app-routing.module';
import {PageRibbonComponent} from "./layouts/profiles/page-ribbon.component";

@NgModule({
  imports: [
    BrowserModule,
    MymonoAppRoutingModule,
    Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
    MymonoSharedModule,
    MymonoCoreModule,

    // from D:\mnt\work\proj_jh\ng-jhipster\index.ts
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: translatePartialLoader,
        deps: [HttpClient]
      },
      missingTranslationHandler: {
        provide: MissingTranslationHandler,
        useFactory: missingTranslationHandler,
        deps: [JhiConfigService]
      }
    }),

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

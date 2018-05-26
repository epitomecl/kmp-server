import './vendor.ts';

import {BrowserModule} from '@angular/platform-browser';
import {Injector, NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS} from "@angular/common/http";

import {LocalStorageService, Ng2Webstorage, SessionStorageService} from "ngx-webstorage";
import {JhiEventManager} from "ng-jhipster";

import {MymonoCoreModule} from "./core";
import {MymonoSharedModule} from "./shared";
import {MyMatModule} from "./my-mat/my-mat.module";

import {AuthInterceptor} from "./blocks/interceptor/auth.interceptor";
import {AuthExpiredInterceptor} from "./blocks/interceptor/auth-expired.interceptor";
import {ErrorHandlerInterceptor} from "./blocks/interceptor/errorhandler.interceptor";
import {NotificationInterceptor} from "./blocks/interceptor/notification.interceptor";

import {MymonoAppRoutingModule} from './app-routing.module';
import {ActiveMenuDirective, AppComponent, NavbarComponent, PageRibbonComponent} from './layouts';
import {MymonoHomeModule} from "./home";

import {MyDashboardComponent} from './my-dashboard/my-dashboard.component';
import {MyTableComponent} from './my-table/my-table.component';

@NgModule({
  imports: [
    BrowserModule,
    MyMatModule,
    MymonoAppRoutingModule,
    Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
    MymonoSharedModule,
    MymonoCoreModule,
    MymonoHomeModule
  ],
  declarations: [
    AppComponent,
    NavbarComponent,
    PageRibbonComponent,
    ActiveMenuDirective,

    MyDashboardComponent,
    MyTableComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
      deps: [LocalStorageService, SessionStorageService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true,
      deps: [Injector]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true,
      deps: [JhiEventManager]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true,
      deps: [Injector]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

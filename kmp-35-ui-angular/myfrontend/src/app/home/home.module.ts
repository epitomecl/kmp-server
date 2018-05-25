import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MymonoSharedModule} from 'src/app/shared';
import {HOME_ROUTE} from "./home.route";
import {HomeComponent} from "./home.component";

@NgModule({
  imports: [MymonoSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MymonoHomeModule {
}

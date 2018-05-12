import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {DEBUG_INFO_ENABLED} from "./app.constants";
import {navbarRoute} from "./layouts/navbar/navbar.route";
import {MyTableComponent} from "./my-table/my-table.component";
import {MyDashboardComponent} from "./my-dashboard/my-dashboard.component";

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        navbarRoute,
        {path: 'dashboard', component: MyDashboardComponent},
        {path: 'table', component: MyTableComponent},
      ],
      {useHash: true, enableTracing: DEBUG_INFO_ENABLED}
    )
  ],
  exports: [RouterModule]
})
export class KmpAppRoutingModule {
}

import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {navbarRoute} from './layouts';
import {DEBUG_INFO_ENABLED} from 'src/app/app.constants';
import {MyTableComponent} from "./my-table/my-table.component";
import {MyDashboardComponent} from "./my-dashboard/my-dashboard.component";

const LAYOUT_ROUTES = [navbarRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        ...LAYOUT_ROUTES,
        {path: 'dashboard', component: MyDashboardComponent},
        {path: 'table', component: MyTableComponent}
      ],
      {useHash: true, enableTracing: DEBUG_INFO_ENABLED}
    )
  ],
  exports: [RouterModule]
})
export class MymonoAppRoutingModule {
}

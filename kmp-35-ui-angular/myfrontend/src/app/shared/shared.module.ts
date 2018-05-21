import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {NgbDateAdapter} from '@ng-bootstrap/ng-bootstrap';

import {NgbDateMomentAdapter} from './util/datepicker-adapter';
import {HasAnyAuthorityDirective} from "./auth/has-any-authority.directive";
import {JhiLoginModalComponent} from "./login/login.component";
import {MymonoSharedCommonModule} from "./shared-common.module";
import {MymonoSharedLibsModule} from "./shared-libs.module";

@NgModule({
  imports: [MymonoSharedLibsModule, MymonoSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  providers: [{provide: NgbDateAdapter, useClass: NgbDateMomentAdapter}],
  entryComponents: [JhiLoginModalComponent],
  exports: [MymonoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MymonoSharedModule {
}

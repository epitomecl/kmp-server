import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {NgbDateAdapter} from '@ng-bootstrap/ng-bootstrap';

import {NgbDateMomentAdapter} from './util/datepicker-adapter';
import {HasAnyAuthorityDirective, JhiLoginModalComponent, MymonoSharedCommonModule, MymonoSharedLibsModule} from './';

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

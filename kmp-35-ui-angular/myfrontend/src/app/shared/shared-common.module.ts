import {NgModule} from '@angular/core';

import {FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, MymonoSharedLibsModule} from './';

@NgModule({
  imports: [MymonoSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
  exports: [MymonoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MymonoSharedCommonModule {
}

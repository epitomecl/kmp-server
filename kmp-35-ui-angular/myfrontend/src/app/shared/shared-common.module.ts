import {NgModule} from '@angular/core';

import {FindLanguageFromKeyPipe} from "./language/find-language-from-key.pipe";
import {JhiAlertComponent} from "./alert/alert.component";
import {JhiAlertErrorComponent} from "./alert/alert-error.component";
import {MymonoSharedLibsModule} from "./shared-libs.module";


@NgModule({
  imports: [MymonoSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
  exports: [MymonoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MymonoSharedCommonModule {
}

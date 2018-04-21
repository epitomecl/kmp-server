import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Configuration } from './configuration';

import { ApiControllerService } from './api/apiController.service';
import { BasicErrorControllerService } from './api/basicErrorController.service';
import { ExplorerControllerService } from './api/explorerController.service';
import { ServiceWalletControllerService } from './api/serviceWalletController.service';
import { WalletControllerService } from './api/walletController.service';

@NgModule({
  imports:      [ CommonModule, HttpClientModule ],
  declarations: [],
  exports:      [],
  providers: [
    ApiControllerService,
    BasicErrorControllerService,
    ExplorerControllerService,
    ServiceWalletControllerService,
    WalletControllerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        }
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import your base AppModule only.');
        }
    }
}

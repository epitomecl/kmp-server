import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KmpjhBlogModule } from './blog/blog.module';
import { KmpjhEntryModule } from './entry/entry.module';
import { KmpjhTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        KmpjhBlogModule,
        KmpjhEntryModule,
        KmpjhTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KmpjhEntityModule {}

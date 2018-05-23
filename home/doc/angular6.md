## Version 6 of Angular Now Available
 - https://blog.angular.io/version-6-of-angular-now-available-cc56b0efa7a4

## set up and create new project
 - https://angular.io/guide/quickstart#devenv
```
$ rmdir /s myfrontend
$ npm install -g @angular/cli
$ ng new myfrontend --style=scss
$ cd myfrontend
$ npm install
$ ng serve
```

## angular.json
```json
"outputPath": "../target/classes/META-INF/resources"
```

## refactoring src directory layout
 - move src/app/app.compoment -> src/app/layouts/main/
 - move src/style.scss -> src/content/scss/
 - create src/content/scss/vendor.scss
 - modify angular.json
```json
"styles": [
              "src/content/scss/vendor.scss",
              "src/content/scss/styles.scss"
            ],
```

## angular material (Install Schematics)
 - https://material.angular.io/guide/schematics
```
$ ng add @angular/material
```

## ng-bootstrap (Install Schematics)
 - https://ng-bootstrap.github.io/#/getting-started
```
$ ng add @ng-bootstrap/schematics
```

## moment
```
$ npm install --save moment
```

## ng-jhipster
 - https://github.com/jhipster/ng-jhipster
```
$ cd <ng-jhipster diriectory>
$ yarn install
$ yarn run build
$ yarn ngc
$ yarn pack

$ cd <project directory>
$ git checkout -- yarn.lock
$ yarn cache clean ng-jhipster
$ rm -rf `yarn cache dir`/.tmp
$ yarn add path/to/ng-jhipster/ng-jhipster-vX.Y.Z.tgz
```

## copy directories from jhipster project mymono
 - src/app/shared
 - src/app/core 

## ngx-webstorage, ngx-cookie, ngx-translate, ...
 - https://www.npmjs.com/package/rxjs-compat
 - https://www.npmjs.com/package/ngx-webstorage
 - https://www.npmjs.com/package/ngx-cookie
 - [ngx-translate/core](https://www.npmjs.com/package/@ngx-translate/core)
 - https://www.npmjs.com/package/ngx-infinite-scroll
```
$ npm install --save rxjs-compat
$ npm install --save ngx-webstorage
$ npm install --save ngx-cookie
$ npm install --save @ngx-translate/core
$ npm install --save ngx-infinite-scroll
```

## sockjs-client, webstomp-client (not work!!)
 - https://www.npmjs.com/package/sockjs-client
 - https://www.npmjs.com/package/webstomp-client
```
$ npm install --save sockjs-client
$ npm install --save webstomp-client
```

## fortawesome
 - [fortawesome](https://www.npmjs.com/package/@fortawesome/angular-fontawesome)
```
$ yarn add @fortawesome/fontawesome-svg-core
$ yarn add @fortawesome/free-solid-svg-icons
$ yarn add @fortawesome/angular-fontawesome
```

## ng generate
```
$ ng generate @angular/material:material-nav --name=my-nav
$ ng generate @angular/material:material-dashboard --name=my-dashboard
$ ng generate @angular/material:material-table --name=my-table
```

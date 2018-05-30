// These constants are injected via webpack environment variables.
// You can add more variables in webpack.common.js or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack config files, you need to re run webpack to update the application

import {environment} from "../environments/environment";

export const VERSION = 0.1;
export const DEBUG_INFO_ENABLED: boolean = true;
export const SERVER_API_URL = (environment.production) ?
  'https://' + location.hostname + ':8443/' :
  location.protocol + '//' + location.hostname + ':8080/';
export const BUILD_TIMESTAMP = 0;

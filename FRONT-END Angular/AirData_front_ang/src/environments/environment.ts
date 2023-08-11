// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  baseUrl: 'http://localhost:5000',
  baseAirDataUrl: 'http://localhost:5000/airdata',
  baseUrlAPI: 'http://localhost:5000/airdata/api',
  leaderboard: 'http://localhost:5000/airdata/leaderboard',
  forecastUrl: 'http://localhost:5000/airdata/forecast',
  notificationUrl: 'http://localhost:5000/airdata/notifications',
  locationUrl: 'http://localhost:5000/airdata/locations',
  domain: 'localhost'
  /* baseUrl: 'https://airq.krystofcejchan.online',
   baseAirDataUrl: 'https://airq.krystofcejchan.online/airdata',
   baseUrlAPI: 'https://airq.krystofcejchan.online/airdata/api',
   leaderboard: 'https://airq.krystofcejchan.online/airdata/leaderboard'*/
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.

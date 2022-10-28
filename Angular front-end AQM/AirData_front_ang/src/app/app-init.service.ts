import { Injectable } from '@angular/core';

@Injectable()
export class AppInitService {

  constructor() {
  }

  Init() {

    return new Promise<void>((resolve, reject) => {
      console.log("AppInitService.init() called");
      setTimeout(() => {
        console.log('AppInitService Finished');
        resolve();
      }, 6000);

    });
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class LocalStoreService {

  key = "jetotereza"
  appInitUrl = environment.APP_INITIALIZER_URL;
 /* private getFileData() {
    return this.http.get('/assets/secret/crypto-js_code.txt')
      .pipe(
        switchMap((response: any) => this.http.get(response.pathToFile, {
          responseType: 'text'
        }))
      );
  }*/

  constructor(private http: HttpClient) {
   /* this.getFileData()
      .subscribe(response => {
        this.key = response;
        console.log(response);
      });*/
  }

  public saveData(key: string, value: string) {
    localStorage.setItem(key, this.encrypt(value));
  }

  public getData(key: string) {
    let data = localStorage.getItem(key) || "";
    return this.decrypt(data);
  }
  public removeData(key: string) {
    localStorage.removeItem(key);
  }

  public clearData() {
    localStorage.clear();
  }

  private encrypt(txt: string): string {
    return CryptoJS.AES.encrypt(txt, this.key).toString();
  }

  private decrypt(txtToDecrypt: string) {
    return CryptoJS.AES.decrypt(txtToDecrypt, this.key).toString(CryptoJS.enc.Utf8);
  }

  public getAllLocations(): Observable<string[]> {
    return this.http.get<string[]>(`${this.appInitUrl}/location`);
  }

  public getAllLeaderboard(): Observable<string[]> {
    return this.http.get<string[]>(`${this.appInitUrl}/leaderboard`);
  }
}

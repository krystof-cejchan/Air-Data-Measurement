import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class LocalStoreService {
  //a key for encrypting and decrypting data
  key = "jetotereza"

  appInitUrl = ""//environment.APP_INITIALIZER_URL;

  constructor(private http: HttpClient) {

  }
  /**
   * saves data
   * works like a map(hashmap)
   * @param key key
   * @param value value
   */
  public saveData(key: string, value: string) {
    localStorage.setItem(key, this.encrypt(value));
  }
  /**
   * gets data
   * @param key of the data
   * @returns data associated with the key from param
   */
  public getData(key: string) {
    let data = localStorage.getItem(key) || "";
    return this.decrypt(data);
  }
  /**
   * removes data
   * @param key to be deleted with its data
   */
  public removeData(key: string) {
    localStorage.removeItem(key);
  }
  /**
   * clears local storage
   */
  public clearData() {
    localStorage.clear();
  }
  /**
   * encrypts text
   * @param txt to be encrypted
   * @returns encrypted text
   */
  private encrypt(txt: string): string {
    return CryptoJS.AES.encrypt(txt, this.key).toString();
  }
  /**
   * decrypts encrypted text
   * @param txtToDecrypt text to be decrypted
   * @returns decrypted text
   */
  private decrypt(txtToDecrypt: string) {
    return CryptoJS.AES.decrypt(txtToDecrypt, this.key).toString(CryptoJS.enc.Utf8);
  }
  /**
   * Lcation Enum from the back-end
   * @returns string[] 
   */
  public getAllLocations(): Observable<string[]> {
    return this.http.get<string[]>(`${this.appInitUrl}/location`);
  }
  /**
   * LeadeboardType Enum from the back-end
   * @returns string[] 
   */
  public getAllLeaderboard(): Observable<string[]> {
    return this.http.get<string[]>(`${this.appInitUrl}/leaderboard`);
  }
}

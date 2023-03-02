import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import moment from "moment";
import { NotificationReceiver } from './newsletter/NotificationReceiver';

@Injectable({
  providedIn: 'root'
})
export class NewsletterSubscriptionService {

  private notificationUrl = environment.notificationUrl;

  constructor(private http: HttpClient) { }

  /**
   * confirms a subscriber if their record exists in the database
   * @param id user's id
   * @param hash user's randomly generated hash code
   * @returns true|false (irrelevant)
   */
  public confirmSubscriber(id: number, hash: string): Observable<Boolean> {
    return this.http.patch<Boolean>(`${this.notificationUrl}/confirm`, {}, { headers: { 'id': id.toString(), 'hash': hash } });
  }

  /**
   * deletes a subscriber from db
   * @param id user's id
   * @param hash user's hash code
   * @returns true|false (irrelevant)
   */
  public deleteSubscriber(id: number, hash: string): Observable<Boolean> {
    return this.http.delete<Boolean>(`${this.notificationUrl}/delete`,
      { headers: { 'id': id.toString(), 'hash': hash, 'password': (id * hash.length).toString() } });
  }

  /*
    public addNewNotifier(email: string): Observable<NotificationReceiver> {
      // const headers = new HttpHeaders().set('email', email);
      return this.http.post<NotificationReceiver>(`${this.notificationUrl}/add`, {}, { headers: { 'email': email } });
    }*/
}

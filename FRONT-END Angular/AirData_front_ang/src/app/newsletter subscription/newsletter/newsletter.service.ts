import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { NotificationReceiver } from './NotificationReceiver';

@Injectable({
  providedIn: 'root'
})
export class NewsletterService {

  private notificationUrl = environment.notificationUrl;

  constructor(private http: HttpClient) { }

  public addNewNotifier(email: string): Observable<NotificationReceiver> {
    // const headers = new HttpHeaders().set('email', email);
    return this.http.post<NotificationReceiver>(`${this.notificationUrl}/add`, {}, { headers: { 'email': email } });
  }
}

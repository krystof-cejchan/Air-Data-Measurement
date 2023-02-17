import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import moment from "moment";

@Injectable({
  providedIn: 'root'
})
export class NewsletterSubscriptionService {

  private notificationUrl = environment.notificationUrl;

  constructor(private http: HttpClient) { }

  public confirmSubscriber(id: number, hash: string): Observable<Boolean> {
    return this.http.patch<Boolean>(`${this.notificationUrl}/confirm`, {}, { headers: { 'id': id.toString(), 'hash': hash } });
  }

  public deleteSubscriber(id: number, hash: string): Observable<Boolean> {   
    return this.http.delete<Boolean>(`${this.notificationUrl}/delete`,
      { headers: { 'id': id.toString(), 'hash': hash, 'password': (id * hash.length).toString() } });
  }
}

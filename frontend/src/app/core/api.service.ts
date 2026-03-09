import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PropertyDto } from '../shared/models/property.model';
import { DashboardDto } from '../shared/models/dashboard.model';
import { UserPreference } from '../shared/models/user-preference.model';
import { MobilityInsightsDto } from '../shared/models/mobility.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  analyzeUrl(url: string): Observable<PropertyDto> {
    return this.http.post<PropertyDto>(`${this.baseUrl}/properties/analyze-url`, { url });
  }

  getProperties(): Observable<PropertyDto[]> {
    return this.http.get<PropertyDto[]>(`${this.baseUrl}/properties`);
  }

  getProperty(id: number): Observable<PropertyDto> {
    return this.http.get<PropertyDto>(`${this.baseUrl}/properties/${id}`);
  }

  reanalyzeProperty(id: number): Observable<PropertyDto> {
    return this.http.post<PropertyDto>(`${this.baseUrl}/properties/${id}/reanalyze`, {});
  }

  getMobilityInsights(propertyId: number, destination?: string): Observable<MobilityInsightsDto> {
    return this.http.post<MobilityInsightsDto>(`${this.baseUrl}/properties/${propertyId}/mobility`, {
      destination: destination || null
    });
  }

  deleteProperty(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/properties/${id}`);
  }

  getDashboard(): Observable<DashboardDto> {
    return this.http.get<DashboardDto>(`${this.baseUrl}/dashboard`);
  }

  getPreferences(): Observable<UserPreference> {
    return this.http.get<UserPreference>(`${this.baseUrl}/preferences`);
  }

  updatePreferences(preferences: UserPreference): Observable<UserPreference> {
    return this.http.put<UserPreference>(`${this.baseUrl}/preferences`, preferences);
  }
}

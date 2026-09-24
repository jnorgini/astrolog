import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { PlanetPositionResponse } from '../models/planet-position.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AstrologService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl;
  private readonly locationsUrl = environment.locationsUrl;

  public getPlanets(
    day: number,
    month: number,
    year: number,
    hour: number,
    minute: number,
    location: string,
  ): Observable<PlanetPositionResponse[]> {
    return this.http.get<PlanetPositionResponse[]>(this.apiUrl, {
      params: { day, month, year, hour, minute, location },
    });
  }

  public searchLocation(query: string): Observable<string[]> {
    if (!query || query.trim().length < 3) {
      return of([]);
    }
    return this.http.get<string[]>(this.locationsUrl, {
      params: { query },
    });
  }
}

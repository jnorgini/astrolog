import { Component, inject, signal, OnDestroy } from '@angular/core';
import { AstrologService } from '../../services/AstrologService';
import { PlanetPositionResponse } from '../../models/planet-position.model';
import { PLANET_SYMBOLS } from '../../models/astrolog-symbols.constants';
import { Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-astrolog-home',
  imports: [],
  templateUrl: './astrolog-home.html',
  styleUrl: './astrolog-home.css',
})
export class AstrologHome implements OnDestroy {
  private readonly astrologService = inject(AstrologService);
  private readonly today = new Date();

  public readonly planetIcons = PLANET_SYMBOLS;

  public readonly day = signal<number>(this.today.getDate());
  public readonly month = signal<number>(this.today.getMonth() + 1);
  public readonly year = signal<number>(this.today.getFullYear());
  public readonly hour = signal<number>(this.today.getHours());
  public readonly minute = signal<number>(this.today.getMinutes());
  public readonly location = signal<string>('');
  public readonly unknownTime = signal<boolean>(false);
  public readonly hasHiddenHouses = signal<boolean>(false);

  public readonly planetsResult = signal<PlanetPositionResponse[]>([]);
  public readonly isLoading = signal<boolean>(false);
  public readonly errorMessage = signal<string | null>(null);

  public readonly suggestions = signal<string[]>([]);
  public readonly showDropdown = signal<boolean>(false);

  private readonly locationSearch$ = new Subject<string>();
  private searchSubscription: Subscription;

  constructor() {
    this.searchSubscription = this.locationSearch$
      .pipe(
        debounceTime(400),
        distinctUntilChanged(),
        switchMap((query) => this.astrologService.searchLocation(query)),
      )
      .subscribe({
        next: (results) => {
          this.suggestions.set(results);
          this.showDropdown.set(results.length > 0);
        },
        error: () => this.suggestions.set([]),
      });
  }

  ngOnDestroy(): void {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  public onLocationInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.location.set(value);
    this.locationSearch$.next(value);
  }

  public selectLocation(selected: string): void {
    this.location.set(selected);
    this.showDropdown.set(false);
  }

  public hideDropdownDelayed(): void {
    setTimeout(() => this.showDropdown.set(false), 250);
  }

  public toggleUnknownTime(): void {
    this.unknownTime.set(!this.unknownTime());

    if (this.unknownTime()) {
      this.hour.set(12);
      this.minute.set(0);
      this.location.set('');
      this.suggestions.set([]);
      this.showDropdown.set(false);
    } else {
      this.hour.set(this.today.getHours());
      this.minute.set(this.today.getMinutes());
    }
  }

  public fetchAstrologyMap(): void {
    if (!this.unknownTime() && !this.location().trim()) {
      this.errorMessage.set('Por favor, digite uma localização.');
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.hasHiddenHouses.set(this.unknownTime());

    const locationValue = this.unknownTime() ? 'Desconhecido' : this.location();

    this.astrologService
      .getPlanets(this.day(), this.month(), this.year(), this.hour(), this.minute(), locationValue)
      .subscribe({
        next: (response) => {
          this.planetsResult.set(response);
          this.isLoading.set(false);
        },
        error: (err) => {
          console.error(err);
          this.errorMessage.set('Não foi possível conectar ao servidor Spring Boot.');
          this.isLoading.set(false);
        },
      });
  }
}

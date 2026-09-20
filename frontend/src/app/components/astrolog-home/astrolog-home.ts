import { Component, inject, signal } from '@angular/core';
import { AstrologService } from '../../services/AstrologService';
import { PlanetPositionResponse } from '../../models/planet-position.model';

@Component({
  selector: 'app-astrolog-home',
  imports: [],
  templateUrl: './astrolog-home.html',
  styleUrl: './astrolog-home.css',
})
export class AstrologHome {
  private readonly astrologService = inject(AstrologService);
  private readonly today = new Date();

  public readonly day = signal<number>(this.today.getDate());
  public readonly month = signal<number>(this.today.getMonth() + 1);
  public readonly year = signal<number>(this.today.getFullYear());
  public readonly hour = signal<number>(this.today.getHours());
  public readonly minute = signal<number>(this.today.getMinutes());

  // Novo Signal para controlar se o usuário sabe ou não o horário
  public readonly unknownTime = signal<boolean>(false);
  // Controla visualmente se devemos sumir com a tabela de casas na resposta
  public readonly hasHiddenHouses = signal<boolean>(false);

  public readonly planetsResult = signal<PlanetPositionResponse[]>([]);
  public readonly isLoading = signal<boolean>(false);
  public readonly errorMessage = signal<string | null>(null);

  public toggleUnknownTime(): void {
    this.unknownTime.set(!this.unknownTime());
    if (this.unknownTime()) {
      this.hour.set(12);
      this.minute.set(0);
    } else {
      this.hour.set(this.today.getHours());
      this.minute.set(this.today.getMinutes());
    }
  }

  public fetchAstrologyMap(): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.hasHiddenHouses.set(this.unknownTime());

    this.astrologService
      .getPlanets(this.day(), this.month(), this.year(), this.hour(), this.minute())
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

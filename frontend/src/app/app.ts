import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('astrolog-frontend');
  
  // Captura o ano atual dinamicamente para o Footer
  protected readonly currentYear = new Date().getFullYear();
}

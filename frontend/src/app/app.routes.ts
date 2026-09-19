import { Routes } from '@angular/router';
// CORREÇÃO: Adicionado o ".component" que o Angular CLI gera por padrão
import { AstrologHome } from './components/astrolog-home/astrolog-home';

export const routes: Routes = [
  { path: '', component: AstrologHome }, // Abre a Home de cara ao acessar localhost:4200
  { path: '**', redirectTo: '' } // Redireciona qualquer link inexistente de volta para a Home
];

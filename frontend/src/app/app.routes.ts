import { Routes } from '@angular/router';
import { AstrologHome } from './components/astrolog-home/astrolog-home';

export const routes: Routes = [
  { path: '', component: AstrologHome }, 
  { path: '**', redirectTo: '' } 
];

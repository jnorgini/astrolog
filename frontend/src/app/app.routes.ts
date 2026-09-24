import { Routes } from '@angular/router';
import { AstrologHome } from './components/astrolog-home/astrolog-home';
import { About } from './components/about/about';

export const routes: Routes = [
  { path: '', component: AstrologHome },
  { path: 'about', component: About },
  { path: '**', redirectTo: '' },
];

import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent) },
  { path: 'analyze', loadComponent: () => import('./features/analyze-url/analyze-url.component').then(m => m.AnalyzeUrlComponent) },
  { path: 'properties', loadComponent: () => import('./features/property-list/property-list.component').then(m => m.PropertyListComponent) },
  { path: 'properties/:id', loadComponent: () => import('./features/property-detail/property-detail.component').then(m => m.PropertyDetailComponent) },
  { path: 'preferences', loadComponent: () => import('./features/preferences/preferences.component').then(m => m.PreferencesComponent) },
  { path: '**', redirectTo: '/dashboard' }
];
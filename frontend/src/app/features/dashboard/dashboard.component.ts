import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ApiService } from '../../core/api.service';
import { DashboardDto } from '../../shared/models/dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  template: `
    <div class="dashboard-container">
      <h1>Dashboard</h1>
      <div class="cards">
        <mat-card>
          <mat-card-header>
            <mat-card-title>Total Viviendas</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <p>{{ dashboard?.totalProperties || 0 }}</p>
          </mat-card-content>
        </mat-card>
        <mat-card>
          <mat-card-header>
            <mat-card-title>Precio Promedio</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <p>{{ dashboard?.averagePrice || 0 }} €</p>
          </mat-card-content>
        </mat-card>
        <mat-card>
          <mat-card-header>
            <mat-card-title>Mejor Puntuada</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <p>{{ dashboard?.topScoredPropertyTitle || 'N/A' }}</p>
          </mat-card-content>
        </mat-card>
        <mat-card>
          <mat-card-header>
            <mat-card-title>Última URL Agregada</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <p>{{ dashboard?.lastAddedUrl || 'N/A' }}</p>
          </mat-card-content>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container { padding: 20px; }
    .cards { display: flex; gap: 20px; flex-wrap: wrap; }
    mat-card { min-width: 200px; }
  `]
})
export class DashboardComponent implements OnInit {

  dashboard: DashboardDto | null = null;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getDashboard().subscribe(data => this.dashboard = data);
  }
}
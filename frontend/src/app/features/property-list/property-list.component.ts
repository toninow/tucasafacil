import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { ApiService } from '../../core/api.service';
import { PropertyDto } from '../../shared/models/property.model';

@Component({
  selector: 'app-property-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule],
  template: `
    <div class="list-container">
      <h1>Viviendas</h1>
      <table mat-table [dataSource]="properties" class="mat-elevation-z8">
        <ng-container matColumnDef="title">
          <th mat-header-cell *matHeaderCellDef>Título</th>
          <td mat-cell *matCellDef="let property">{{ property.title }}</td>
        </ng-container>
        <ng-container matColumnDef="price">
          <th mat-header-cell *matHeaderCellDef>Precio</th>
          <td mat-cell *matCellDef="let property">{{ property.price }} €</td>
        </ng-container>
        <ng-container matColumnDef="score">
          <th mat-header-cell *matHeaderCellDef>Score</th>
          <td mat-cell *matCellDef="let property">{{ property.score }}</td>
        </ng-container>
        <ng-container matColumnDef="actions">
          <th mat-header-cell *matHeaderCellDef>Acciones</th>
          <td mat-cell *matCellDef="let property">
            <button mat-button (click)="viewDetail(property.id)">Ver</button>
            <button mat-button color="warn" (click)="deleteProperty(property.id)">Eliminar</button>
          </td>
        </ng-container>
        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>
    </div>
  `,
  styles: [`
    .list-container { padding: 20px; }
    table { width: 100%; }
  `]
})
export class PropertyListComponent implements OnInit {

  properties: PropertyDto[] = [];
  displayedColumns: string[] = ['title', 'price', 'score', 'actions'];

  constructor(private apiService: ApiService, private router: Router) { }

  ngOnInit(): void {
    this.apiService.getProperties().subscribe(data => this.properties = data);
  }

  viewDetail(id: number): void {
    this.router.navigate(['/properties', id]);
  }

  deleteProperty(id: number): void {
    const confirmed = confirm('¿Seguro que quieres eliminar esta propiedad analizada?');
    if (!confirmed) return;

    this.apiService.deleteProperty(id).subscribe({
      next: () => {
        this.properties = this.properties.filter(p => p.id !== id);
      },
      error: () => {
        alert('No se pudo eliminar la propiedad.');
      }
    });
  }
}

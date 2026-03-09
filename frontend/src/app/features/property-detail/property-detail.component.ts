import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../../core/api.service';
import { PropertyDto } from '../../shared/models/property.model';

@Component({
  selector: 'app-property-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  template: `
    <div class="detail-container" *ngIf="property">
      <h1>{{ property.title }}</h1>
      <mat-card>
        <mat-card-content>
          <p><strong>Precio:</strong> {{ property.price }} €</p>
          <p><strong>Dirección:</strong> {{ property.address }}</p>
          <p><strong>Metros:</strong> {{ property.squareMeters }}</p>
          <p><strong>Habitaciones:</strong> {{ property.bedrooms }}</p>
          <p><strong>Baños:</strong> {{ property.bathrooms }}</p>
          <p><strong>Score:</strong> {{ property.score }}</p>
          <p><strong>Descripción:</strong> {{ property.description }}</p>
        </mat-card-content>
      </mat-card>
      <button mat-raised-button (click)="reanalyze()">Reanalizar</button>
    </div>
  `,
  styles: [`
    .detail-container { padding: 20px; max-width: 800px; margin: auto; }
  `]
})
export class PropertyDetailComponent implements OnInit {

  property: PropertyDto | null = null;

  constructor(private route: ActivatedRoute, private apiService: ApiService) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.apiService.getProperty(id).subscribe(data => this.property = data);
  }

  reanalyze(): void {
    if (this.property) {
      this.apiService.reanalyzeProperty(this.property.id).subscribe(data => this.property = data);
    }
  }
}
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../core/api.service';
import { PropertyDto } from '../../shared/models/property.model';

@Component({
  selector: 'app-analyze-url',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule, FormsModule],
  template: `
    <div class="analyze-container">
      <h1>Analizar URL</h1>
      <mat-card>
        <mat-card-content>
          <form (ngSubmit)="onSubmit()">
            <mat-form-field appearance="fill">
              <mat-label>URL del anuncio</mat-label>
              <input matInput [(ngModel)]="url" name="url" required>
            </mat-form-field>
            <button mat-raised-button color="primary" type="submit" [disabled]="!url">Analizar</button>
          </form>
        </mat-card-content>
      </mat-card>
      <div *ngIf="result">
        <h2>Resultado</h2>
        <p>Título: {{ result.title }}</p>
        <p>Precio: {{ result.price }} €</p>
        <p>Score: {{ result.score }}</p>
      </div>
      <div *ngIf="error" class="error">{{ error }}</div>
    </div>
  `,
  styles: [`
    .analyze-container { padding: 20px; max-width: 600px; margin: auto; }
    .error { color: red; }
  `]
})
export class AnalyzeUrlComponent {

  url: string = '';
  result: PropertyDto | null = null;
  error: string = '';

  constructor(private apiService: ApiService) { }

  onSubmit(): void {
    this.error = '';
    this.result = null;
    this.apiService.analyzeUrl(this.url).subscribe({
      next: (data) => this.result = data,
      error: (err) => this.error = err.error || 'Error al analizar URL'
    });
  }
}
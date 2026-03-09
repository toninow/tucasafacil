import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../core/api.service';
import { UserPreference } from '../../shared/models/user-preference.model';

@Component({
  selector: 'app-preferences',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule, MatCheckboxModule, FormsModule],
  template: `
    <div class="preferences-container">
      <h1>Preferencias</h1>
      <mat-card *ngIf="preferences">
        <mat-card-content>
          <form (ngSubmit)="onSubmit()">
            <mat-form-field appearance="fill">
              <mat-label>Presupuesto Máximo</mat-label>
              <input matInput type="number" [(ngModel)]="preferences.maxBudget" name="maxBudget">
            </mat-form-field>
            <mat-checkbox [(ngModel)]="preferences.preferPrivateOwner" name="preferPrivateOwner">Preferir particular</mat-checkbox>
            <mat-checkbox [(ngModel)]="preferences.requireGoodTransport" name="requireGoodTransport">Requiere buen transporte</mat-checkbox>
            <mat-checkbox [(ngModel)]="preferences.acceptsGuarantor" name="acceptsGuarantor">Acepta aval</mat-checkbox>
            <mat-checkbox [(ngModel)]="preferences.acceptsInsurance" name="acceptsInsurance">Acepta seguro</mat-checkbox>
            <button mat-raised-button color="primary" type="submit">Guardar</button>
          </form>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .preferences-container { padding: 20px; max-width: 600px; margin: auto; }
  `]
})
export class PreferencesComponent implements OnInit {

  preferences: UserPreference | null = null;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getPreferences().subscribe(data => this.preferences = data);
  }

  onSubmit(): void {
    if (this.preferences) {
      this.apiService.updatePreferences(this.preferences).subscribe(data => this.preferences = data);
    }
  }
}
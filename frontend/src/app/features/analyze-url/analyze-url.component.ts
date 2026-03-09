import { Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ApiService } from '../../core/api.service';
import { PropertyDto, PropertyRequirementDto, ZoneAnalysisDto } from '../../shared/models/property.model';
import { MobilityInsightsDto, MobilityTargetDto, TransportRouteDto } from '../../shared/models/mobility.model';

declare global {
  interface Window {
    L: any;
  }
}

interface TableRow {
  campo: string;
  valor: string;
}

@Component({
  selector: 'app-analyze-url',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule
  ],
  template: `
    <div class="analyze-container">
      <h1>Analizar URL</h1>

      <mat-card>
        <mat-card-content>
          <form class="analyze-form" (ngSubmit)="onSubmit()">
            <mat-form-field appearance="fill">
              <mat-label>URL del anuncio</mat-label>
              <input matInput [(ngModel)]="url" name="url" required />
            </mat-form-field>
            <button mat-raised-button color="primary" type="submit" [disabled]="!url || loading">
              {{ loading ? 'Analizando...' : 'Analizar' }}
            </button>
          </form>
        </mat-card-content>
      </mat-card>

      <div *ngIf="error" class="error">{{ error }}</div>

      <section *ngIf="result" class="results-section">
        <h2>Resultado</h2>
        <p class="result-title">{{ result.title || 'Sin titulo' }}</p>

        <div class="media-grid">
          <mat-card class="media-card" *ngIf="imageUrls.length > 0">
            <mat-card-header>
              <mat-card-title>Imagenes</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <div class="slider">
                <button mat-stroked-button type="button" (click)="previousImage()" [disabled]="imageUrls.length <= 1">
                  Anterior
                </button>

                <img
                  class="slider-image"
                  [src]="imageUrls[currentImageIndex]"
                  [alt]="'Imagen ' + (currentImageIndex + 1)"
                />

                <button mat-stroked-button type="button" (click)="nextImage()" [disabled]="imageUrls.length <= 1">
                  Siguiente
                </button>
              </div>
              <p class="slider-counter">{{ currentImageIndex + 1 }} / {{ imageUrls.length }}</p>
            </mat-card-content>
          </mat-card>

          <mat-card class="media-card" *ngIf="videoEmbedUrl || videoFileUrl">
            <mat-card-header>
              <mat-card-title>Video</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <iframe
                *ngIf="videoEmbedUrl"
                class="video-frame"
                [src]="videoEmbedUrl"
                title="Video del anuncio"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                referrerpolicy="strict-origin-when-cross-origin"
                allowfullscreen>
              </iframe>

              <video *ngIf="videoFileUrl" class="video-frame" controls [src]="videoFileUrl"></video>
            </mat-card-content>
          </mat-card>
        </div>

        <mat-card class="mobility-card">
          <mat-card-header>
            <mat-card-title>Mapa y movilidad real</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <div class="mobility-controls">
              <mat-form-field appearance="fill">
                <mat-label>Destino personalizado (direccion)</mat-label>
                <input
                  matInput
                  [(ngModel)]="destination"
                  name="destination"
                  placeholder="Ej: Puerta del Sol, Madrid"
                />
              </mat-form-field>
              <button mat-raised-button color="primary" type="button" (click)="refreshMobility()" [disabled]="mobilityLoading">
                {{ mobilityLoading ? 'Calculando...' : 'Calcular distancias' }}
              </button>
            </div>

            <p class="mobility-note" *ngIf="mobility?.property">
              Punto detectado: {{ mobility?.property?.name || 'Sin nombre' }}
            </p>
            <div *ngIf="mobilityError" class="error">{{ mobilityError }}</div>

            <div id="mobilityMap" class="map-box"></div>

            <div class="target-grid" *ngIf="mobility?.targets?.length">
              <mat-card class="target-card" *ngFor="let target of mobility?.targets">
                <mat-card-header>
                  <mat-card-title>{{ target.name }}</mat-card-title>
                </mat-card-header>
                <mat-card-content>
                  <table class="data-table">
                    <tr *ngFor="let route of target.routes">
                      <th>{{ transportLabel(route.mode) }}</th>
                      <td>{{ formatRoute(route) }}</td>
                    </tr>
                  </table>
                </mat-card-content>
              </mat-card>
            </div>
          </mat-card-content>
        </mat-card>

        <div class="table-grid">
          <mat-card class="table-card">
            <mat-card-header>
              <mat-card-title>Datos clave</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <table class="data-table">
                <tr *ngFor="let row of keyRows">
                  <th>{{ row.campo }}</th>
                  <td>{{ row.valor }}</td>
                </tr>
              </table>
            </mat-card-content>
          </mat-card>

          <mat-card class="table-card">
            <mat-card-header>
              <mat-card-title>Caracteristicas</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <table class="data-table">
                <tr *ngFor="let row of propertyRows">
                  <th>{{ row.campo }}</th>
                  <td>{{ row.valor }}</td>
                </tr>
              </table>
            </mat-card-content>
          </mat-card>

          <mat-card class="table-card">
            <mat-card-header>
              <mat-card-title>Requisitos</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <table class="data-table">
                <tr *ngFor="let row of requirementRows">
                  <th>{{ row.campo }}</th>
                  <td>{{ row.valor }}</td>
                </tr>
              </table>
            </mat-card-content>
          </mat-card>

          <mat-card class="table-card">
            <mat-card-header>
              <mat-card-title>Zona</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <table class="data-table">
                <tr *ngFor="let row of zoneRows">
                  <th>{{ row.campo }}</th>
                  <td>{{ row.valor }}</td>
                </tr>
              </table>
            </mat-card-content>
          </mat-card>
        </div>
      </section>
    </div>
  `,
  styles: [`
    .analyze-container {
      max-width: 1050px;
      margin: 0 auto;
      padding: 24px;
    }

    .analyze-form {
      display: flex;
      gap: 12px;
      align-items: center;
      flex-wrap: wrap;
    }

    .analyze-form mat-form-field {
      flex: 1 1 480px;
    }

    .results-section {
      margin-top: 24px;
    }

    .result-title {
      font-size: 26px;
      font-weight: 700;
      margin-top: 8px;
      margin-bottom: 16px;
    }

    .media-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
      gap: 16px;
      margin-bottom: 16px;
    }

    .media-card {
      height: 100%;
    }

    .slider {
      display: grid;
      grid-template-columns: auto 1fr auto;
      gap: 10px;
      align-items: center;
    }

    .slider-image {
      width: 100%;
      height: 320px;
      object-fit: cover;
      border-radius: 10px;
      border: 1px solid #d9d9d9;
      background: #f5f5f5;
    }

    .slider-counter {
      margin-top: 8px;
      text-align: center;
      color: #666;
    }

    .video-frame {
      width: 100%;
      min-height: 320px;
      border: 1px solid #d9d9d9;
      border-radius: 10px;
      background: #000;
    }

    .mobility-card {
      margin-bottom: 16px;
    }

    .mobility-controls {
      display: flex;
      align-items: center;
      gap: 12px;
      flex-wrap: wrap;
    }

    .mobility-controls mat-form-field {
      flex: 1 1 420px;
    }

    .mobility-note {
      margin-top: 8px;
      color: #455a64;
      font-weight: 600;
    }

    .map-box {
      width: 100%;
      height: 420px;
      border: 1px solid #d9d9d9;
      border-radius: 10px;
      margin-top: 10px;
      margin-bottom: 14px;
      overflow: hidden;
      background: #f8f8f8;
    }

    .target-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
      gap: 12px;
    }

    .target-card {
      height: 100%;
    }

    .table-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
      gap: 16px;
    }

    .table-card {
      height: 100%;
    }

    .data-table {
      width: 100%;
      border-collapse: collapse;
    }

    .data-table th {
      text-align: left;
      width: 46%;
      padding: 8px 6px;
      border-bottom: 1px solid #ececec;
      color: #223;
      font-weight: 600;
      vertical-align: top;
    }

    .data-table td {
      padding: 8px 6px;
      border-bottom: 1px solid #ececec;
      color: #333;
      vertical-align: top;
      word-break: break-word;
    }

    .error {
      margin-top: 14px;
      color: #b00020;
      font-weight: 600;
    }

    @media (max-width: 640px) {
      .analyze-container {
        padding: 16px;
      }

      .slider {
        grid-template-columns: 1fr;
      }

      .slider-image,
      .video-frame {
        min-height: 220px;
        height: 220px;
      }

      .map-box {
        height: 300px;
      }
    }
  `]
})
export class AnalyzeUrlComponent implements OnDestroy {
  url = '';
  destination = '';
  result: PropertyDto | null = null;
  error = '';
  loading = false;

  imageUrls: string[] = [];
  currentImageIndex = 0;
  videoEmbedUrl: SafeResourceUrl | null = null;
  videoFileUrl: string | null = null;

  keyRows: TableRow[] = [];
  propertyRows: TableRow[] = [];
  requirementRows: TableRow[] = [];
  zoneRows: TableRow[] = [];

  mobility: MobilityInsightsDto | null = null;
  mobilityLoading = false;
  mobilityError = '';

  private leafletPromise: Promise<void> | null = null;
  private map: any = null;
  private markersLayer: any = null;

  constructor(
    private apiService: ApiService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnDestroy(): void {
    this.destroyMap();
  }

  onSubmit(): void {
    this.error = '';
    this.result = null;
    this.loading = true;
    this.resetComputedView();

    this.apiService.analyzeUrl(this.url).subscribe({
      next: (data) => {
        this.result = data;
        this.buildMediaView(data);
        this.buildTables(data);
        this.loading = false;
        this.refreshMobility();
      },
      error: (err) => {
        this.error = err?.error || 'Error al analizar URL';
        this.loading = false;
      }
    });
  }

  refreshMobility(): void {
    if (!this.result?.id) return;

    this.mobilityLoading = true;
    this.mobilityError = '';
    this.apiService.getMobilityInsights(this.result.id, this.destination).subscribe({
      next: (data) => {
        this.mobility = data;
        this.mobilityLoading = false;
        this.renderMap();
      },
      error: (err) => {
        this.mobilityError = err?.error || 'No se pudieron calcular rutas reales';
        this.mobilityLoading = false;
      }
    });
  }

  previousImage(): void {
    if (this.imageUrls.length <= 1) return;
    this.currentImageIndex = (this.currentImageIndex - 1 + this.imageUrls.length) % this.imageUrls.length;
  }

  nextImage(): void {
    if (this.imageUrls.length <= 1) return;
    this.currentImageIndex = (this.currentImageIndex + 1) % this.imageUrls.length;
  }

  transportLabel(mode: string): string {
    if (mode === 'walking') return 'Caminando';
    if (mode === 'cycling') return 'Bicicleta';
    if (mode === 'driving') return 'Coche';
    return mode;
  }

  formatRoute(route: TransportRouteDto): string {
    return `${route.distanceKm} km | ${route.durationMinutes} min`;
  }

  private resetComputedView(): void {
    this.imageUrls = [];
    this.currentImageIndex = 0;
    this.videoEmbedUrl = null;
    this.videoFileUrl = null;
    this.keyRows = [];
    this.propertyRows = [];
    this.requirementRows = [];
    this.zoneRows = [];
    this.mobility = null;
    this.mobilityError = '';
    this.destroyMap();
  }

  private async renderMap(): Promise<void> {
    if (!this.mobility?.property?.latitude || !this.mobility?.property?.longitude) return;

    await this.ensureLeafletLoaded();

    setTimeout(() => {
      const L = window.L;
      const mapHost = document.getElementById('mobilityMap');
      if (!mapHost) return;

      const propertyLatLng = [this.mobility!.property.latitude, this.mobility!.property.longitude];

      if (!this.map) {
        this.map = L.map('mobilityMap', { zoomControl: true }).setView(propertyLatLng, 13);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '&copy; OpenStreetMap contributors'
        }).addTo(this.map);
      }

      this.map.invalidateSize();
      this.map.setView(propertyLatLng, 13);

      if (this.markersLayer) {
        this.markersLayer.clearLayers();
      } else {
        this.markersLayer = L.layerGroup().addTo(this.map);
      }

      const boundsPoints: Array<[number, number]> = [];

      L.circleMarker(propertyLatLng, {
        radius: 9,
        color: '#2e7d32',
        fillColor: '#4caf50',
        fillOpacity: 0.9
      }).bindPopup('Piso').addTo(this.markersLayer);
      boundsPoints.push(propertyLatLng as [number, number]);

      for (const target of this.mobility?.targets || []) {
        if (!target.latitude || !target.longitude) continue;
        const point: [number, number] = [target.latitude, target.longitude];
        const color = target.type === 'station' ? '#1565c0' : '#c2185b';

        L.circleMarker(point, {
          radius: 8,
          color,
          fillColor: color,
          fillOpacity: 0.85
        }).bindPopup(target.name).addTo(this.markersLayer);

        boundsPoints.push(point);
      }

      if (boundsPoints.length > 1) {
        const bounds = L.latLngBounds(boundsPoints);
        this.map.fitBounds(bounds.pad(0.18));
      }
    });
  }

  private ensureLeafletLoaded(): Promise<void> {
    if (window.L) return Promise.resolve();
    if (this.leafletPromise) return this.leafletPromise;

    this.injectLeafletCss();

    this.leafletPromise = new Promise<void>((resolve, reject) => {
      const existingScript = document.getElementById('leaflet-js') as HTMLScriptElement | null;
      if (existingScript) {
        existingScript.addEventListener('load', () => resolve());
        existingScript.addEventListener('error', () => reject(new Error('No se pudo cargar Leaflet')));
        return;
      }

      const script = document.createElement('script');
      script.id = 'leaflet-js';
      script.src = 'https://unpkg.com/leaflet@1.9.4/dist/leaflet.js';
      script.async = true;
      script.onload = () => resolve();
      script.onerror = () => reject(new Error('No se pudo cargar Leaflet'));
      document.head.appendChild(script);
    });

    return this.leafletPromise;
  }

  private injectLeafletCss(): void {
    if (document.getElementById('leaflet-css')) return;
    const link = document.createElement('link');
    link.id = 'leaflet-css';
    link.rel = 'stylesheet';
    link.href = 'https://unpkg.com/leaflet@1.9.4/dist/leaflet.css';
    document.head.appendChild(link);
  }

  private destroyMap(): void {
    if (this.markersLayer) {
      this.markersLayer.clearLayers();
      this.markersLayer = null;
    }
    if (this.map) {
      this.map.remove();
      this.map = null;
    }
  }

  private buildMediaView(property: PropertyDto): void {
    const dtoImages = (property.images ?? [])
      .map((img) => img?.imageUrl || '')
      .filter((url) => url.length > 0);

    const textUrls = this.extractUrls(property.description || '');
    const textImages = textUrls.filter((url) => this.isImageUrl(url));

    this.imageUrls = Array.from(new Set([...dtoImages, ...textImages]));
    this.currentImageIndex = 0;

    const videoUrl = this.findVideoUrl(textUrls);
    if (!videoUrl) return;

    if (this.isDirectVideoUrl(videoUrl)) {
      this.videoFileUrl = videoUrl;
      return;
    }

    const embedUrl = this.toEmbedVideoUrl(videoUrl);
    if (embedUrl) {
      this.videoEmbedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
    }
  }

  private buildTables(property: PropertyDto): void {
    const req: PropertyRequirementDto | undefined = property.requirements?.[0];
    const zone: ZoneAnalysisDto | undefined = property.zoneAnalyses?.[0];
    const pricePerM2 = this.calcPricePerM2(property.price, property.squareMeters);

    this.keyRows = [
      { campo: 'Precio', valor: this.formatCurrency(property.price, property.currency) },
      { campo: 'Metros', valor: this.formatNumber(property.squareMeters, ' m2') },
      { campo: 'Precio por m2', valor: pricePerM2 !== null ? `${pricePerM2.toFixed(2)} EUR/m2` : 'N/D' },
      { campo: 'Score total', valor: this.formatNumber(property.score) },
      { campo: 'Score vivienda', valor: this.formatNumber(property.housingScore) },
      { campo: 'Score zona', valor: this.formatNumber(property.areaScore) },
      { campo: 'Score requisitos', valor: this.formatNumber(property.requirementScore) },
      { campo: 'Tipo anuncio', valor: this.valueOrNd(property.listingType) },
      { campo: 'Anunciante', valor: this.valueOrNd(property.advertiserType) }
    ];

    this.propertyRows = [
      { campo: 'Direccion', valor: this.joinAddress(property) },
      { campo: 'Habitaciones', valor: this.formatNumber(property.bedrooms) },
      { campo: 'Banos', valor: this.formatNumber(property.bathrooms) },
      { campo: 'Planta', valor: this.formatNumber(property.floor) },
      { campo: 'Ascensor', valor: this.formatBoolean(property.hasElevator) },
      { campo: 'Amueblado', valor: this.formatBoolean(property.furnished) },
      { campo: 'Terraza', valor: this.formatBoolean(property.hasTerrace) },
      { campo: 'Balcon', valor: this.formatBoolean(property.hasBalcony) },
      { campo: 'Garaje', valor: this.formatBoolean(property.hasGarage) },
      { campo: 'Trastero', valor: this.formatBoolean(property.hasStorageRoom) },
      { campo: 'Gastos incluidos', valor: this.formatBoolean(property.expensesIncluded) }
    ];

    this.requirementRows = [
      { campo: 'Contrato indefinido', valor: this.formatBoolean(req?.requiresPermanentContract) },
      { campo: 'Nominas requeridas', valor: this.formatNumber(req?.requiredPayslips) },
      { campo: 'Fianza (meses)', valor: this.formatNumber(req?.depositMonths) },
      { campo: 'Adelanto (meses)', valor: this.formatNumber(req?.advanceMonths) },
      { campo: 'Aval requerido', valor: this.formatBoolean(req?.requiresGuarantor) },
      { campo: 'Seguro impago', valor: this.formatBoolean(req?.requiresRentDefaultInsurance) },
      { campo: 'Mascotas permitidas', valor: this.formatBoolean(req?.petsAllowed) },
      { campo: 'Ingreso minimo estimado', valor: this.formatCurrency(req?.estimatedMinimumIncome, property.currency) },
      { campo: 'Perfil preferido', valor: this.valueOrNd(req?.preferredProfile) }
    ];

    this.zoneRows = [
      { campo: 'Metro (min)', valor: this.formatNumber(zone?.nearestMetroDistanceMinutes) },
      { campo: 'Bus (min)', valor: this.formatNumber(zone?.nearestBusDistanceMinutes) },
      { campo: 'Supermercados 500m', valor: this.formatNumber(zone?.supermarketCount500m) },
      { campo: 'Farmacias 500m', valor: this.formatNumber(zone?.pharmacyCount500m) },
      { campo: 'Gimnasios 1000m', valor: this.formatNumber(zone?.gymCount1000m) },
      { campo: 'Parques 1000m', valor: this.formatNumber(zone?.parkCount1000m) },
      { campo: 'Restaurantes 1000m', valor: this.formatNumber(zone?.restaurantCount1000m) },
      { campo: 'Score transporte', valor: this.formatNumber(zone?.transportScore) },
      { campo: 'Score servicios', valor: this.formatNumber(zone?.serviceScore) },
      { campo: 'Score ocio', valor: this.formatNumber(zone?.entertainmentScore) }
    ];
  }

  private extractUrls(text: string): string[] {
    const matches = text.match(/https?:\/\/[^\s<>"')]+/gi) || [];
    return matches.map((url) => url.replace(/[.,;]+$/, ''));
  }

  private isImageUrl(url: string): boolean {
    return /\.(jpg|jpeg|png|webp|gif|avif)(\?.*)?$/i.test(url);
  }

  private isDirectVideoUrl(url: string): boolean {
    return /\.(mp4|webm|ogg|mov)(\?.*)?$/i.test(url);
  }

  private findVideoUrl(urls: string[]): string | null {
    for (const url of urls) {
      if (this.isDirectVideoUrl(url)) return url;
      if (this.toEmbedVideoUrl(url)) return url;
    }
    return null;
  }

  private toEmbedVideoUrl(url: string): string | null {
    const youtube = url.match(/(?:youtube\.com\/watch\?v=|youtu\.be\/)([A-Za-z0-9_-]{6,})/i);
    if (youtube?.[1]) return `https://www.youtube.com/embed/${youtube[1]}`;

    const vimeo = url.match(/vimeo\.com\/(\d+)/i);
    if (vimeo?.[1]) return `https://player.vimeo.com/video/${vimeo[1]}`;

    return null;
  }

  private calcPricePerM2(price?: number, squareMeters?: number): number | null {
    if (!price || !squareMeters || squareMeters <= 0) return null;
    return price / squareMeters;
  }

  private joinAddress(property: PropertyDto): string {
    const parts = [
      property.address,
      property.district,
      property.neighborhood,
      property.city,
      property.province,
      property.postalCode
    ].filter((p) => !!p);

    return parts.length > 0 ? parts.join(', ') : 'N/D';
  }

  private formatCurrency(value?: number, currency = 'EUR'): string {
    if (value === null || value === undefined) return 'N/D';
    return `${value} ${currency || 'EUR'}`;
  }

  private formatNumber(value?: number, suffix = ''): string {
    if (value === null || value === undefined) return 'N/D';
    return `${value}${suffix}`;
  }

  private formatBoolean(value?: boolean): string {
    if (value === null || value === undefined) return 'N/D';
    return value ? 'Si' : 'No';
  }

  private valueOrNd(value?: string): string {
    return value && value.trim() ? value : 'N/D';
  }
}

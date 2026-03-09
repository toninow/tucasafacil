import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ApiService } from '../../core/api.service';
import { PropertyDto, PropertyRequirementDto, ZoneAnalysisDto } from '../../shared/models/property.model';

interface TableRow {
  campo: string;
  valor: string;
}

@Component({
  selector: 'app-property-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  template: `
    <div class="detail-container" *ngIf="property">
      <div class="header-row">
        <h1>{{ property.title || 'Propiedad' }}</h1>
        <button mat-raised-button color="primary" (click)="reanalyze()">Reanalizar</button>
      </div>

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
              <img class="slider-image" [src]="imageUrls[currentImageIndex]" [alt]="'Imagen ' + (currentImageIndex + 1)" />
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
    </div>
  `,
  styles: [`
    .detail-container {
      max-width: 1050px;
      margin: 0 auto;
      padding: 24px;
    }

    .header-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 12px;
      margin-bottom: 16px;
      flex-wrap: wrap;
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

    @media (max-width: 640px) {
      .detail-container {
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
    }
  `]
})
export class PropertyDetailComponent implements OnInit {
  property: PropertyDto | null = null;

  imageUrls: string[] = [];
  currentImageIndex = 0;
  videoEmbedUrl: SafeResourceUrl | null = null;
  videoFileUrl: string | null = null;

  keyRows: TableRow[] = [];
  propertyRows: TableRow[] = [];
  requirementRows: TableRow[] = [];
  zoneRows: TableRow[] = [];

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.loadProperty();
  }

  reanalyze(): void {
    if (!this.property) return;
    this.apiService.reanalyzeProperty(this.property.id).subscribe((data) => this.applyProperty(data));
  }

  previousImage(): void {
    if (this.imageUrls.length <= 1) return;
    this.currentImageIndex = (this.currentImageIndex - 1 + this.imageUrls.length) % this.imageUrls.length;
  }

  nextImage(): void {
    if (this.imageUrls.length <= 1) return;
    this.currentImageIndex = (this.currentImageIndex + 1) % this.imageUrls.length;
  }

  private loadProperty(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) return;
    this.apiService.getProperty(id).subscribe((data) => this.applyProperty(data));
  }

  private applyProperty(property: PropertyDto): void {
    this.property = property;
    this.buildMediaView(property);
    this.buildTables(property);
  }

  private buildMediaView(property: PropertyDto): void {
    const dtoImages = (property.images ?? [])
      .map((img) => img?.imageUrl || '')
      .filter((url) => url.length > 0);

    const textUrls = this.extractUrls(property.description || '');
    const textImages = textUrls.filter((url) => this.isImageUrl(url));

    this.imageUrls = Array.from(new Set([...dtoImages, ...textImages]));
    this.currentImageIndex = 0;
    this.videoEmbedUrl = null;
    this.videoFileUrl = null;

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

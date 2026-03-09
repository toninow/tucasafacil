export interface GeoPointDto {
  name?: string;
  latitude?: number;
  longitude?: number;
}

export interface TransportRouteDto {
  mode: string;
  distanceKm: number;
  durationMinutes: number;
}

export interface MobilityTargetDto {
  name: string;
  type: string;
  latitude: number;
  longitude: number;
  routes: TransportRouteDto[];
}

export interface MobilityInsightsDto {
  property: GeoPointDto;
  targets: MobilityTargetDto[];
}

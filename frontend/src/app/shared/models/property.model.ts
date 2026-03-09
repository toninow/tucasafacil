export interface PropertyDto {
  id: number;
  sourcePortal: string;
  sourceUrl: string;
  title: string;
  description: string;
  price: number;
  currency: string;
  address: string;
  district: string;
  neighborhood: string;
  city: string;
  province: string;
  postalCode: string;
  latitude: number;
  longitude: number;
  squareMeters: number;
  bedrooms: number;
  bathrooms: number;
  floor: number;
  hasElevator: boolean;
  furnished: boolean;
  hasTerrace: boolean;
  hasBalcony: boolean;
  hasGarage: boolean;
  hasStorageRoom: boolean;
  expensesIncluded: boolean;
  listingType: string;
  advertiserType: string;
  score: number;
  housingScore: number;
  areaScore: number;
  requirementScore: number;
  extractedAt: string;
  createdAt: string;
  updatedAt: string;
  requirements: PropertyRequirementDto[];
  zoneAnalyses: ZoneAnalysisDto[];
  images: PropertyImageDto[];
}

export interface PropertyRequirementDto {
  requiresPermanentContract: boolean;
  requiredPayslips: number;
  depositMonths: number;
  advanceMonths: number;
  requiresGuarantor: boolean;
  requiresRentDefaultInsurance: boolean;
  estimatedMinimumIncome: number;
  petsAllowed: boolean;
  preferredProfile: string;
  maxOccupants: number;
  notes: string;
}

export interface ZoneAnalysisDto {
  nearestMetroDistanceMinutes: number;
  nearestBusDistanceMinutes: number;
  supermarketCount500m: number;
  pharmacyCount500m: number;
  gymCount1000m: number;
  parkCount1000m: number;
  restaurantCount1000m: number;
  healthCenterCount2000m: number;
  shoppingCenterCount3000m: number;
  entertainmentScore: number;
  transportScore: number;
  serviceScore: number;
  zoneSummary: string;
}

export interface PropertyImageDto {
  imageUrl: string;
  sortOrder: number;
}
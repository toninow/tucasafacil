export interface UserPreference {
  id: number;
  maxBudget: number;
  preferredZones: string;
  minBedrooms: number;
  maxDepositMonths: number;
  preferPrivateOwner: boolean;
  requireGoodTransport: boolean;
  requireSupermarketNearby: boolean;
  requireGymNearby: boolean;
  valueEntertainment: boolean;
  acceptsGuarantor: boolean;
  acceptsInsurance: boolean;
  referenceLatitude: number;
  referenceLongitude: number;
  scoringWeightsJson: string;
}
export interface Fruit {
  id?: number;
  quantity: number;
  origin: string;
  importDate: Date | string | undefined;
}

export interface FruitParams {
  quantity: number;
  origin: string;
  initialImportDate: Date | string | undefined;
  finalImportDate: Date | string | undefined;
}

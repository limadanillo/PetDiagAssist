export interface AnimalRequest {
  name : string;
  type: string;
  breed: string;
  birthDate: string;
  weight: number;
}

export interface AnimalResponse {
  publicId: string;
  name : string;
  type: string;
  breed: string;
  birthDate: string;
  weight: number;
}


export interface Animal {
  publicId: string;
  name: string;
  type: string;
  breed: string;
  weight: number;
  birthDate: string;
}

export interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

export interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  offset: number;
  unpaged: boolean;
  paged: boolean;
}

export interface AnimalResponsePag {
  content: Animal[];
  pageable: Pageable;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: Sort;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

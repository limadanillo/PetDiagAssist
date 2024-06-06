export interface ExamRequest {
  examType: string;
  animalId: string ;
  expirationDate: string ;
  imageReferenceId: string ;
  imageType: string ;
}

export interface ExamResponse {
  publicId: string;
  examType: string ;
  expirationDate: string ;
  imageReferenceId: string ;
  imageType: string ;
  animalId: string ;
  creatorUserId: string ;
}

export interface Exam {
  publicId: string;
  examType: string;
  animalId: string;
  expirationDate: string;
  imageReferenceId: string;
  imageType: string;
  creatorUserId: string;
  nameAnimal: string;
  nameUser: string;
}

export interface Pageable {
  pageNumber: number;
  pageSize: number;
}

export interface ExamResponsePag {
  content: Exam[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}

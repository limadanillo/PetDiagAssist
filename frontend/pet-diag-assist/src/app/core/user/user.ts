
export interface User {
    user: Date;
    publicId: string ;
    name: string ;
    email: string ;
    role: string;
    created_at: string;
 }
 export interface UpdateUserProfile {
    publicId: string;
    name: string ;
    email: string;
    password: string;
    role: string;
    created_at: string;
 }

export interface UpdateUser {
  publicId: string;
  name: string ;
  email: string;
  role: string;
  isActive: string;
}

 export interface DataUser {
    data: User
 }

 export interface DataUsers {
   data: User[]
}

export interface AddUser {
   "user":{
      email: string ;
      name: string ;
      password: string;
      password_confirm: string;
   }
}

export interface UserResponse {
   message: string;
   data:string
}

// Interface para representar um usuário individual
export interface User {
  publicId: string;
  name: string;
  email: string;
  role: string;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
  veterinary: null;
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

export interface UserPag {
  content: User[];
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

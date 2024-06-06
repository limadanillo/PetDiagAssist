import {Injectable} from "@angular/core";
import {HttpService} from "../../shared/http.service";
import {Observable} from "rxjs";
import {AnimalRequest, AnimalResponse, AnimalResponsePag} from "./animal";


@Injectable()
export class AnimalService {
  constructor(private httpService: HttpService) {
  }

  createAnimal(data: AnimalRequest): Observable<AnimalResponse> {
    return this.httpService.post<AnimalResponse>('/animals', data)
  }

  getAllAnimals(page: number, size: number): Observable<AnimalResponsePag> {
    return this.httpService.get<AnimalResponsePag>('/animals?page='+page+'&size='+size)
  }

  getAnimalById(id: String): Observable<AnimalResponse> {
    return this.httpService.get<AnimalResponse>('/animals/' + id)
  }

  updateAnimal(data: AnimalRequest, id: string): Observable<AnimalResponse> {
    return this.httpService.patch<AnimalResponse>('api/animals'+ id, data)
  }

  deleteAnimalById(id: String): Observable<void> {
    return this.httpService.delete<void>('api/animals/' + id)
  }

}

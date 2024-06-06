import {Injectable} from "@angular/core";
import {HttpService} from "../../shared/http.service";
import {Observable} from "rxjs";


@Injectable()
export class ImageService {
  constructor(private httpService: HttpService) {
  }
  uploadImage(data: File, idExam: string): Observable<any> {
    return this.httpService.post<any>('api/images/upload/exam/'+ idExam, data)
  }

  getImage(idExam: string): Observable<any> {
    return this.httpService.get<any>('api/images/download/exam/'+ idExam)
  }

  deleteImage(idExam: string): Observable<any> {
    return this.httpService.delete<any>('api/images/exam/'+ idExam)
  }
}

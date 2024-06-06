import {Injectable} from "@angular/core";
import {HttpService} from "../../shared/http.service";
import {Observable} from "rxjs";
import {VeterinarianRequest, VeterinarianResponse} from "./veterinarian";


@Injectable()
export class VeterinarianService {
  constructor(private httpService:HttpService)  {
  }

  getVeterinarianByUserId(userId: String): Observable<VeterinarianResponse> {
    return this.httpService.get<VeterinarianResponse>('/veterinarians/user/'+userId)
  }
  createVeterinarian(data:VeterinarianRequest): Observable<VeterinarianResponse> {
    return this.httpService.post<VeterinarianResponse>('/veterinarians',data)
  }
  updateVeterinarian(data:VeterinarianRequest): Observable<VeterinarianResponse> {
    return this.httpService.patch<VeterinarianResponse>('/veterinarians',data)
  }
  deleteVeterinarianByUserId(userId:String): Observable<VeterinarianResponse> {
    return this.httpService.delete<VeterinarianResponse>('/veterinarians/'+userId)
  }
}

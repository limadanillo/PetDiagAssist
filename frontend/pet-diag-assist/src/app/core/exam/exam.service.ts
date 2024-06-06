import {Injectable} from "@angular/core";
import {HttpService} from "../../shared/http.service";
import {Observable} from "rxjs";
import {ExamRequest, ExamResponse, ExamResponsePag} from "./exame";


@Injectable()
export class ExamService {
  constructor(private httpService: HttpService) {
  }

  createExam(data: ExamRequest): Observable<ExamResponse> {
    return this.httpService.post<ExamResponse>('/exams', data)
  }

  getAllExams(page: number, size: number): Observable<ExamResponsePag> {
    return this.httpService.get<ExamResponsePag>('/exams?page='+page+'&size='+size)  }

  getExamByUserId(userId: String): Observable<ExamResponse[]> {
    return this.httpService.get<ExamResponse[]>('/exams/user/' + userId)
  }

  getExamById(examId: String): Observable<ExamResponse> {
    return this.httpService.get<ExamResponse>('/exams/' + examId)
  }
}

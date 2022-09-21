import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { data1 } from './data1';
@Injectable()
export class ConfigService {
  constructor(private http: HttpClient){}

  listdata: data1[] | undefined;

  getconnect(name:string): Observable<any>{
      console.log("Checkpoint 1 ")
      return  this.http.get('https://homework8-99111234.wl.r.appspot.com/search/'+name);
  }

  getartist(id:string): Observable<any>{
    console.log("Checkpoint 2 ")
    return  this.http.get('https://homework8-99111234.wl.r.appspot.com/artists/'+id);
}

    getartwork(id:string): Observable<any>{
    console.log("Checkpoint 3 ")
    return  this.http.get('https://homework8-99111234.wl.r.appspot.com/artworks/'+id);
}

    getGenes(id:string): Observable<any>{
    console.log("Checkpoint 4 ")
    return  this.http.get('https://homework8-99111234.wl.r.appspot.com/genes/'+id);
}

}
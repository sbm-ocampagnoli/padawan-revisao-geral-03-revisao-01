import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Fruit } from '../interface/fruit';

@Injectable({
  providedIn: 'root',
})
export class FruitService {
  private url = environment.apiUrl + '/fruits';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Fruit[]> {
    return this.http.get<Fruit[]>(this.url);
  }
  add(newFruit: Fruit): Observable<Fruit> {
    return this.http.post<Fruit>(`${this.url}`, newFruit);
  }

  update(fruit: Fruit): Observable<Fruit> {
    return this.http.put<Fruit>(`${this.url}/${fruit.id}`, fruit);
  }

  delete(fruit: Fruit): Observable<any> {
    return this.http.delete<any>(`${this.url}/${fruit.id}`);
  }

  filterComposed(fruit: Fruit): Observable<Fruit[]> {
    let params = new HttpParams();

    if (fruit.importDate) {
      let dateConverted = this.convertToDateTime(fruit.importDate as Date);
      params = params.set('importDate', dateConverted);
    } else {
      params = params.set('importDate', '');
    }

    params = params.set('origin', fruit.origin);
    params = params.set('quantity', fruit.quantity);

    return this.http.get<Fruit[]>(`${this.url}/filter`, { params }).pipe(
      map((fruits) =>
        fruits.map((fruit) => {
          return {
            ...fruit,
            importDate: fruit.importDate
              ? this.convertDateToBrazilFormat(new Date(fruit.importDate))
              : undefined,
          };
        })
      )
    );
  }

  convertDateToBrazilFormat(date: Date): string {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    return `${day}/${month}/${year} | ${hours}:${minutes}:${seconds}`;
  }

  convertToDateTime(data: Date): string {
    let dateString = data.toString();
    return dateString.slice(0, 19);
  }
}

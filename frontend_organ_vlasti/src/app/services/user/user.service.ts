import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Prijava } from 'src/app/models/prijava';
import { Registracija } from 'src/app/models/registracija';
import { OSNOVA } from 'src/app/constants/namespaces';
import { Profil } from 'src/app/models/profil';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_AUTH = `${environment.baseUrl}/${environment.apiAuth}`;

  private prijavaToXml(prijava: Prijava): string{
    return `
      <prijava>
        <mejl>${prijava.mejl}</mejl>
        <lozinka>${prijava.lozinka}</lozinka>
      </prijava>
    `;
  }

  private registracijaToXml(registracija: Registracija): string{
    return `
      <Korisnik xmlns="${OSNOVA}">
        <mejl>${registracija.mejl}</mejl>
        <lozinka>${registracija.lozinka}</lozinka>
        <Osoba>
          <ime>${registracija.ime}</ime>
          <prezime>${registracija.prezime}</prezime>
        </Osoba>
        <Adresa>
            <mesto>${registracija.mesto}</mesto>
            <ulica>${registracija.ulica}</ulica>
            <broj>${registracija.broj}</broj>
        </Adresa>
      </Korisnik>
    `;
  }

  private xmlToProfil(xml: string): Profil{
    const parser = new DOMParser();
    const profil = parser.parseFromString(xml, 'text/xml');
    return {
      token: profil.getElementsByTagName('token')[0].textContent,
      uloga: profil.getElementsByTagName('uloga')[0].textContent,
      mejl: profil.getElementsByTagName('mejl')[0].textContent,
      ime: profil.getElementsByTagName('ime')[0].textContent,
      prezime: profil.getElementsByTagName('prezime')[0].textContent
    };
  }

  login(prijava: Prijava): Observable<Profil>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_AUTH}/login`, this.prijavaToXml(prijava), options).pipe(
      map((xml: string) => this.xmlToProfil(xml))
    );
  }

  register(registracija: Registracija): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_AUTH}/register`, this.registracijaToXml(registracija), options);
  }

}

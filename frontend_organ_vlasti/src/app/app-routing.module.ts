import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';
import { RegisterFormComponent } from './components/auth/register-form/register-form.component';
import { ObavestenjeFormComponent } from './components/odluka/obavestenje-form/obavestenje-form.component';
import { OdlukaListComponent } from './components/odluka/odluka-list/odluka-list.component';
import { ZahtevFormComponent } from './components/zahtev/zahtev-form/zahtev-form.component';
import { ZahtevListComponent } from './components/zahtev/zahtev-list/zahtev-list.component';
import {
  LOGIN_PATH,
  REGISTER_PATH,
  ZAHTEV_LIST,
  ZAHTEV_FORM,
  OBAVESTENJE_FORM,
  ODLUKA_LIST,
  HTML_PATH,
  PDF_PATH,
  ODBIJANJE_FORM,
  ODGOVOR_FORM,
  ZALBA_LIST,
  RESENJE_LIST,
  IZVESTAJ_LIST,
  ODGOVOR_LIST
} from './constants/router';
import { GradjaninGuard } from './guard/gradjanin/gradjanin.guard';
import { SluzbenikGuard } from './guard/sluzbenik/sluzbenik.guard';
import { OdbijanjeFormComponent } from './components/odluka/odbijanje-form/odbijanje-form.component';
import { OdgovorFormComponent } from './components/odgovor/odgovor-form/odgovor-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ResenjeListComponent } from './components/resenje/resenje-list/resenje-list.component';
import { IzvestajListComponent } from './components/izvestaj/izvestaj-list/izvestaj-list.component';
import { OdgovorListComponent } from './components/odgovor/odgovor-list/odgovor-list.component';
import { AuthGuard } from './guard/auth/auth.guard';

const routes: Routes = [
  {
    path: LOGIN_PATH,
    component: LoginFormComponent
  },
  {
    path: REGISTER_PATH,
    component: RegisterFormComponent
  },
  {
    path: ZAHTEV_FORM,
    component: ZahtevFormComponent,
    canActivate: [GradjaninGuard]
  },
  {
    path: ZAHTEV_LIST,
    component: ZahtevListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: OBAVESTENJE_FORM,
    component: ObavestenjeFormComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: ODBIJANJE_FORM,
    component: OdbijanjeFormComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: ODLUKA_LIST,
    component: OdlukaListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: ZALBA_LIST,
    component: ZalbaListComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: ODGOVOR_FORM,
    component: OdgovorFormComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: ODGOVOR_LIST,
    component: OdgovorListComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: RESENJE_LIST,
    component: ResenjeListComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: IZVESTAJ_LIST,
    component: IzvestajListComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: HTML_PATH,
    component: HtmlViewerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: PDF_PATH,
    component: PdfViewerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: LOGIN_PATH
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

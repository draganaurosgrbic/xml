import { Location } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS,
  SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Odgovor } from 'src/app/models/odgovor';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-odgovor-form',
  templateUrl: './odgovor-form.component.html',
  styleUrls: ['./odgovor-form.component.sass']
})
export class OdgovorFormComponent implements AfterViewInit {

  constructor(
    private odgovorService: OdgovorService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  savePending = false;

  get prazniDetalji(): boolean{
    const parser = new DOMParser();
    const Detalji = parser.parseFromString(Xonomy.harvest(), 'text/xml').getElementsByTagName('Detalji').item(0);
    return Detalji.textContent.trim() === '';
  }

  save(): void{
    if (this.prazniDetalji){
      this.snackBar.open(EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      return;
    }
    const odgovor: Odgovor = {detalji: Xonomy.harvest()};
    this.savePending = true;
    this.odgovorService.add(this.route.snapshot.params.brojZalbe, odgovor).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Odgovor poslat!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.location.back();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    document.querySelectorAll('.xonomy').forEach(el => el.innerHTML = null);
    const detaljiXml = '<Detalji></Detalji>';
    const odgovorEditor = document.getElementById('odgovorEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, odgovorEditor, detaljiSpecifikacija);
  }

}

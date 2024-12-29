import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { saveMetadata } from 'src/app/services/utils';

@Component({
  selector: 'app-resenje-list',
  templateUrl: './resenje-list.component.html',
  styleUrls: ['./resenje-list.component.sass']
})
export class ResenjeListComponent implements AfterViewInit {

  constructor(
    private resenjeService: ResenjeService,
    private authService: AuthService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  @ViewChild(MatSort) sort: MatSort;
  columns: string[] = ['datum', 'status', 'dokumenti', 'metapodaci'];

  resenja: MatTableDataSource<ResenjeDTO> = new MatTableDataSource<ResenjeDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  metadata(broj: number, format: string): void{
    this.resenjeService.metadata(broj, format).subscribe(
      (response: string) => saveMetadata(response, format)
    );
  }

  pretraga(xml: string, tip: string): void{
    this.fetchPending = true;
    this.resenjeService.pretraga(xml, tip).subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = new MatTableDataSource<ResenjeDTO>(resenja);
        this.resenja.paginator = this.paginator;
        this.resenja.sort = this.sort;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.resenjeService.findAll().subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = new MatTableDataSource<ResenjeDTO>(resenja);
        this.resenja.paginator = this.paginator;
        this.resenja.sort = this.sort;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );

    this.authService.drawerToggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UsuarioService } from 'src/app/services/usuario.service';

interface Route {
  label: string;
  route: string | null;
  active: boolean;
}

@Component({
  selector: 'app-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.sass'],
})
export class BreadcrumbsComponent implements OnInit {
  isBuilding!: boolean;
  routes!: Array<Route>;
  
  private _authentication!: Authentication;

  constructor(
    private _animalService: AnimalService,
    private _authenticationService: AuthenticationService,
    private _tutorService: TutorService,
    private _usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this._authentication = this._authenticationService.getAuthentication();
    this.isBuilding = true;
    this.routes = this.getRoutes();
  }

  private checkObjectChange() {
    !this.isBuilding ? this.ngOnInit() : null;
  }

  private getObjectName(id: string) {
    let name = '';
    const path = window.location.pathname;

    if (path.includes('animais')) {
      this._animalService.get().subscribe({
        next: (animal) => {
          name = animal ? animal.nome.toUpperCase() : 'Cadastro';
          this.checkObjectChange();
        },
      });
    }

    else if (path.includes('tutores')) {
      this._tutorService.get().subscribe({
        next: (tutor) => {
          name = tutor ? tutor.nome.toUpperCase() : 'Cadastro';
          this.checkObjectChange();
        },
      });
    }

    else if (path.includes('usuarios')) {
      this._usuarioService.get().subscribe({
        next: (usuario) => {
          name = usuario ? usuario.nome.toUpperCase() : 'Cadastro';
          this.checkObjectChange();
        },
      });
    }

    else {
      name = 'Cadastro';
    }

    return name;
  }

  private getRoutes() {
    const routes: Array<Route> = [];
    const path = window.location.pathname
      .split('/')
      .filter((x) => x !== '' && x !== 'painel');

    this.isBuilding = true;

    path.forEach((route, index) => {
      switch (route) {
        case 'agendamentos':
          routes.push({
            label: 'Agendamentos',
            route:
              '/' + this._authentication.role.toLowerCase() + '/agendamentos',
            active: index === path.length - 1,
          });
          break;
        case 'animais':
          routes.push({
            label: 'Animais',
            route: '/' + this._authentication.role.toLowerCase() + '/animais',
            active: index === path.length - 1,
          });
          break;
        case 'atendimentos':
          routes.push({
            label: 'Atendimentos',
            route:
              '/' + this._authentication.role.toLowerCase() + '/atendimentos',
            active: index === path.length - 1,
          });
          break;
        case 'exames':
          routes.push({
            label: 'Exames',
            route: '/' + this._authentication.role.toLowerCase() + '/exames',
            active: index === path.length - 1,
          });
          break;
        case 'feiras-adocao':
          routes.push({
            label: 'Feiras de Adoção',
            route:
              '/' + this._authentication.role.toLowerCase() + '/feiras-adocao',
            active: index === path.length - 1,
          });
          break;
        case 'tutores':
          routes.push({
            label: 'Tutores',
            route: '/' + this._authentication.role.toLowerCase() + '/tutores',
            active: index === path.length - 1,
          });
          break;
        case 'usuarios':
          routes.push({
            label: 'Usuários',
            route: '/' + this._authentication.role.toLowerCase() + '/usuarios',
            active: index === path.length - 1,
          });
          break;
        case this._authentication.role.toLowerCase():
          routes.push({
            label: 'Início',
            route: '/' + this._authentication.role.toLowerCase() + '/painel',
            active: index === path.length - 1,
          });
          break;
        default:
          routes.push({
            label: this.getObjectName(route),
            route: null,
            active: index === path.length - 1,
          });
      }
    });

    this.isBuilding = false;
    return routes;
  }
}

import { Component, OnInit } from '@angular/core';
import { Authentication } from 'src/app/entities/authentication';
import { AnimalService } from 'src/app/services/animal.service';
import { AuthService } from 'src/app/services/auth.service';
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
  authentication!: Authentication;
  routes!: Array<Route>;

  constructor(
    private _animalService: AnimalService,
    private _authenticationService: AuthService,
    private _tutorService: TutorService,
    private _usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.routes = [];
    this.authentication = this._authenticationService.getAuthentication();
    this.routes = this.getRoutes();
  }

  private getObjectName(id: string) {
    let name = '';
    const path = window.location.pathname;

    if (
      path.includes('agendamentos') ||
      path.includes('atendimentos') ||
      path.includes('exames') ||
      path.includes('feira-adocao') ||
      path.includes('cadastro')
    ) {
      name = 'Cadastro';
    }

    if (path.includes('animais') && !path.includes('cadastro')) {
      this._animalService.get().subscribe({
        next: (animal) => {
          name = animal ? animal.nome.toUpperCase() : '';
        },
      });
    }

    if (path.includes('tutores') && !path.includes('cadastro')) {
      this._tutorService.get().subscribe({
        next: (tutor) => {
          name = tutor ? tutor.nome.toUpperCase() : '';
        },
      });
    }

    if (path.includes('usuarios') && !path.includes('cadastro')) {
      this._usuarioService.get().subscribe({
        next: (usuario) => {
          name = usuario ? usuario.nome.toUpperCase() : '';
        },
      });
    }

    return name;
  }

  private getRoutes() {
    const routes: Array<Route> = [];
    const path = window.location.pathname
      .split('/')
      .filter((x) => x !== '' && x !== 'painel');

    path.forEach((route, index) => {
      switch (route) {
        case 'agendamentos':
          routes.push({
            label: 'Agendamentos',
            route:
              '/' + this.authentication.role.toLowerCase() + '/agendamentos',
            active: index === path.length - 1,
          });
          break;
        case 'animais':
          routes.push({
            label: 'Animais',
            route: '/' + this.authentication.role.toLowerCase() + '/animais',
            active: index === path.length - 1,
          });
          break;
        case 'atendimentos':
          routes.push({
            label: 'Atendimentos',
            route:
              '/' + this.authentication.role.toLowerCase() + '/atendimentos',
            active: index === path.length - 1,
          });
          break;
        case 'exames':
          routes.push({
            label: 'Exames',
            route: '/' + this.authentication.role.toLowerCase() + '/exames',
            active: index === path.length - 1,
          });
          break;
        case 'feiras-adocao':
          routes.push({
            label: 'Feiras de Adoção',
            route:
              '/' + this.authentication.role.toLowerCase() + '/feiras-adocao',
            active: index === path.length - 1,
          });
          break;
        case 'tutores':
          routes.push({
            label: 'Tutores',
            route: '/' + this.authentication.role.toLowerCase() + '/tutores',
            active: index === path.length - 1,
          });
          break;
        case 'usuarios':
          routes.push({
            label: 'Usuários',
            route: '/' + this.authentication.role.toLowerCase() + '/usuarios',
            active: index === path.length - 1,
          });
          break;
        case this.authentication.role.toLowerCase():
          routes.push({
            label: 'Início',
            route: '/' + this.authentication.role.toLowerCase() + '/painel',
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

    return routes;
  }
}

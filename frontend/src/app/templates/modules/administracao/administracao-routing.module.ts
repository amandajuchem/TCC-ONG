import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { AdocoesComponent } from '../../components/adocoes/adocoes.component';
import { AnimaisComponent } from '../../components/animais/animais.component';
import { AnimalComponent } from '../../components/animal/animal.component';
import { AtendimentosComponent } from '../../components/atendimentos/atendimentos.component';
import { LayoutComponent } from '../../components/layout/layout.component';
import { PainelComponent } from '../../components/painel/painel.component';
import { TutorComponent } from '../../components/tutor/tutor.component';
import { TutoresComponent } from '../../components/tutores/tutores.component';
import { UsuarioComponent } from '../../components/usuario/usuario.component';
import { UsuariosComponent } from '../../components/usuarios/usuarios.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: 'adocoes', component: AdocoesComponent, canActivate: [AuthGuard] },
      { path: 'animais', component: AnimaisComponent, canActivate: [AuthGuard] },
      { path: 'animais/:id', component: AnimalComponent, canActivate: [AuthGuard] },
      { path: 'atendimentos', component: AtendimentosComponent, canActivate: [AuthGuard] },
      { path: 'painel', component: PainelComponent, canActivate: [AuthGuard] },
      { path: 'tutores', component: TutoresComponent, canActivate: [AuthGuard] },
      { path: 'tutores/:id', component: TutorComponent, canActivate: [AuthGuard] },
      { path: 'usuarios', component: UsuariosComponent, canActivate: [AuthGuard] },
      { path: 'usuarios/:id', component: UsuarioComponent, canActivate: [AuthGuard] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministracaoRoutingModule { }
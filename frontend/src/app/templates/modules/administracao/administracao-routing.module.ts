import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { AnimaisComponent } from '../../components/animais/animais.component';
import { AnimalComponent } from '../../components/animal/animal.component';
import { LayoutComponent } from '../../components/layout/layout.component';
import { PainelComponent } from '../../components/painel/painel.component';
import { TutorComponent } from '../../components/tutor/tutor.component';
import { TutoresComponent } from '../../components/tutores/tutores.component';
import { UsuariosComponent } from '../../components/usuarios/usuarios.component';
import { UsuarioComponent } from '../../components/usuario/usuario.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: 'animais', component: AnimaisComponent, canActivate: [AuthGuard] },
      { path: 'animais/:id', component: AnimalComponent, canActivate: [AuthGuard] },
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
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { AnimaisComponent } from '../../components/animais/animais.component';
import { LayoutComponent } from '../../components/layout/layout.component';
import { PainelComponent } from '../../components/painel/painel.component';
import { TutoresComponent } from '../../components/tutores/tutores.component';
import { UsuariosComponent } from '../../components/usuarios/usuarios.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: 'animais', component: AnimaisComponent, canActivate: [AuthGuard] },
      { path: 'painel', component: PainelComponent, canActivate: [AuthGuard] },
      { path: 'tutores', component: TutoresComponent, canActivate: [AuthGuard] },
      { path: 'usuarios', component: UsuariosComponent, canActivate: [AuthGuard] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministracaoRoutingModule { }
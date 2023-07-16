import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';
import { LoginComponent } from './templates/components/login/login.component';
import { NotFoundComponent } from './templates/components/not-found/not-found.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'administracao',
    loadChildren: () =>
      import('./templates/modules/administracao/administracao.module').then(
        (m) => m.AdministracaoModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'funcionario',
    loadChildren: () =>
      import('./templates/modules/funcionario/funcionario.module').then(
        (m) => m.FuncionarioModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'veterinario',
    loadChildren: () =>
      import('./templates/modules/veterinario/veterinario.module').then(
        (m) => m.VeterinarioModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

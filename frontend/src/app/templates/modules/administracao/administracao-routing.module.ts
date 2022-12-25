import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { LayoutComponent } from '../../components/layout/layout.component';
import { PainelComponent } from '../../components/painel/painel.component';

const routes: Routes = [
  {
    path: 'administracao', component: LayoutComponent, children: [
      { path: 'painel', component: PainelComponent, canActivate: [AuthGuard] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministracaoRoutingModule { }
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/app/guards/auth.guard';

import { AgendamentoCadastroComponent } from '../../components/agendamento-cadastro/agendamento-cadastro.component';
import { AgendamentosComponent } from '../../components/agendamentos/agendamentos.component';
import { AnimaisComponent } from '../../components/animais/animais.component';
import { AnimalComponent } from '../../components/animal/animal.component';
import { AtendimentoCadastroComponent } from '../../components/atendimento-cadastro/atendimento-cadastro.component';
import { AtendimentosComponent } from '../../components/atendimentos/atendimentos.component';
import { ExameCadastroComponent } from '../../components/exame-cadastro/exame-cadastro.component';
import { ExamesComponent } from '../../components/exames/exames.component';
import { FeiraAdocaoCadastroComponent } from '../../components/feira-adocao-cadastro/feira-adocao-cadastro.component';
import { FeirasAdocaoComponent } from '../../components/feiras-adocao/feiras-adocao.component';
import { LayoutComponent } from '../../components/layout/layout.component';
import { PainelComponent } from '../../components/painel/painel.component';
import { TutorComponent } from '../../components/tutor/tutor.component';
import { TutoresComponent } from '../../components/tutores/tutores.component';
import { UsuarioComponent } from '../../components/usuario/usuario.component';
import { UsuariosComponent } from '../../components/usuarios/usuarios.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: 'agendamentos', component: AgendamentosComponent, canActivate: [AuthGuard] },
      { path: 'agendamentos/:id', component: AgendamentoCadastroComponent, canActivate: [AuthGuard] },
      { path: 'animais', component: AnimaisComponent, canActivate: [AuthGuard] },
      { path: 'animais/:id', component: AnimalComponent, canActivate: [AuthGuard] },
      { path: 'atendimentos', component: AtendimentosComponent, canActivate: [AuthGuard] },
      { path: 'atendimentos/:id', component: AtendimentoCadastroComponent, canActivate: [AuthGuard] },
      { path: 'exames', component: ExamesComponent, canActivate: [AuthGuard] },
      { path: 'exames/:id', component: ExameCadastroComponent, canActivate: [AuthGuard] },
      { path: 'feiras-adocao', component: FeirasAdocaoComponent, canActivate: [AuthGuard] },
      { path: 'feiras-adocao/:id', component: FeiraAdocaoCadastroComponent, canActivate: [AuthGuard] },
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
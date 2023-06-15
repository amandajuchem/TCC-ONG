import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

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
import { NotFoundComponent } from '../../components/not-found/not-found.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'agendamentos', component: AgendamentosComponent },
      { path: 'agendamentos/:id', component: AgendamentoCadastroComponent },
      { path: 'animais', component: AnimaisComponent },
      { path: 'animais/:id', component: AnimalComponent },
      { path: 'atendimentos', component: AtendimentosComponent },
      { path: 'atendimentos/:id', component: AtendimentoCadastroComponent },
      { path: 'exames', component: ExamesComponent },
      { path: 'exames/:id', component: ExameCadastroComponent },
      { path: 'feiras-adocao', component: FeirasAdocaoComponent },
      { path: 'feiras-adocao/:id', component: FeiraAdocaoCadastroComponent },
      { path: 'painel', component: PainelComponent },
      { path: 'tutores', component: TutoresComponent },
      { path: 'tutores/:id', component: TutorComponent },
      { path: '**', component: NotFoundComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VeterinarioRoutingModule {}

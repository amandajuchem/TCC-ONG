import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AgendamentoCadastroComponent } from 'src/app/components/agendamento-cadastro/agendamento-cadastro.component';
import { AgendamentosComponent } from 'src/app/components/agendamentos/agendamentos.component';
import { AnimaisComponent } from 'src/app/components/animais/animais.component';
import { AnimalComponent } from 'src/app/components/animal/animal.component';
import { AtendimentoCadastroComponent } from 'src/app/components/atendimento-cadastro/atendimento-cadastro.component';
import { AtendimentosComponent } from 'src/app/components/atendimentos/atendimentos.component';
import { EmpresaComponent } from 'src/app/components/empresa/empresa.component';
import { ExameCadastroComponent } from 'src/app/components/exame-cadastro/exame-cadastro.component';
import { ExamesComponent } from 'src/app/components/exames/exames.component';
import { FeiraAdocaoCadastroComponent } from 'src/app/components/feira-adocao-cadastro/feira-adocao-cadastro.component';
import { FeirasAdocaoComponent } from 'src/app/components/feiras-adocao/feiras-adocao.component';
import { LayoutComponent } from 'src/app/components/layout/layout.component';
import { NotFoundComponent } from 'src/app/components/not-found/not-found.component';
import { PainelComponent } from 'src/app/components/painel/painel.component';
import { TutorComponent } from 'src/app/components/tutor/tutor.component';
import { TutoresComponent } from 'src/app/components/tutores/tutores.component';
import { UsuarioComponent } from 'src/app/components/usuario/usuario.component';
import { UsuariosComponent } from 'src/app/components/usuarios/usuarios.component';

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
      { path: 'empresas/:id', component: EmpresaComponent },
      { path: 'exames', component: ExamesComponent },
      { path: 'exames/:id', component: ExameCadastroComponent },
      { path: 'feiras-adocao', component: FeirasAdocaoComponent },
      { path: 'feiras-adocao/:id', component: FeiraAdocaoCadastroComponent },
      { path: 'painel', component: PainelComponent },
      { path: 'tutores', component: TutoresComponent },
      { path: 'tutores/:id', component: TutorComponent },
      { path: 'usuarios', component: UsuariosComponent },
      { path: 'usuarios/:id', component: UsuarioComponent },
      { path: '**', component: NotFoundComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministracaoRoutingModule {}

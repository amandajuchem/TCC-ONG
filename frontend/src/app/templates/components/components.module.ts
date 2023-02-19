import { NgxMatDatetimePickerModule, NgxMatNativeDateModule } from '@angular-material-components/datetime-picker';
import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { NgxMaskModule } from 'ngx-mask';
import { getDutchPaginatorIntl } from 'src/app/configurations/internacionalization';

import { AgendamentoCadastroComponent } from './agendamento-cadastro/agendamento-cadastro.component';
import { AgendamentoExcluirComponent } from './agendamento-excluir/agendamento-excluir.component';
import { AgendamentosComponent } from './agendamentos/agendamentos.component';
import { AnimaisComponent } from './animais/animais.component';
import { AnimalAdocoesCadastroComponent } from './animal-adocoes-cadastro/animal-adocoes-cadastro.component';
import { AnimalAdocoesExcluirComponent } from './animal-adocoes-excluir/animal-adocoes-excluir.component';
import { AnimalAdocoesComponent } from './animal-adocoes/animal-adocoes.component';
import { AnimalCadastroComponent } from './animal-cadastro/animal-cadastro.component';
import { AnimalExcluirComponent } from './animal-excluir/animal-excluir.component';
import { AnimalFichaMedicaComponent } from './animal-ficha-medica/animal-ficha-medica.component';
import { AnimalInformacoesComponent } from './animal-informacoes/animal-informacoes.component';
import { AnimalComponent } from './animal/animal.component';
import { AtendimentoCadastroComponent } from './atendimento-cadastro/atendimento-cadastro.component';
import { AtendimentoExcluirComponent } from './atendimento-excluir/atendimento-excluir.component';
import { AtendimentosComponent } from './atendimentos/atendimentos.component';
import { ExameCadastroComponent } from './exame-cadastro/exame-cadastro.component';
import { ExameExcluirComponent } from './exame-excluir/exame-excluir.component';
import { ExamesComponent } from './exames/exames.component';
import { FooterComponent } from './footer/footer.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { NotificationComponent } from './notification/notification.component';
import { PainelComponent } from './painel/painel.component';
import { SelecionarAnimaisComponent } from './selecionar-animais/selecionar-animais.component';
import { SelecionarAnimalComponent } from './selecionar-animal/selecionar-animal.component';
import { SelecionarImagemComponent } from './selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from './selecionar-tutor/selecionar-tutor.component';
import { SelecionarUsuarioComponent } from './selecionar-usuario/selecionar-usuario.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { TutorCadastroComponent } from './tutor-cadastro/tutor-cadastro.component';
import { TutorEnderecoComponent } from './tutor-endereco/tutor-endereco.component';
import { TutorExcluirComponent } from './tutor-excluir/tutor-excluir.component';
import { TutorInformacoesComponent } from './tutor-informacoes/tutor-informacoes.component';
import { TutorComponent } from './tutor/tutor.component';
import { TutoresComponent } from './tutores/tutores.component';
import { UsuarioInformacoesComponent } from './usuario-informacoes/usuario-informacoes.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { UsuariosCadastroComponent } from './usuarios-cadastro/usuarios-cadastro.component';
import { UsuariosComponent } from './usuarios/usuarios.component';

@NgModule({
  declarations: [
    AgendamentoCadastroComponent,
    AgendamentoExcluirComponent,
    AgendamentosComponent,
    AnimaisComponent,
    AnimalAdocoesComponent,
    AnimalAdocoesCadastroComponent,
    AnimalAdocoesExcluirComponent,
    AnimalCadastroComponent,
    AnimalExcluirComponent,
    AnimalFichaMedicaComponent,
    AnimalInformacoesComponent,
    AtendimentoCadastroComponent,
    AtendimentoExcluirComponent,
    AtendimentosComponent,
    AnimalComponent,
    ExameCadastroComponent,
    ExameExcluirComponent,
    ExamesComponent,
    FooterComponent,
    LayoutComponent,
    LoginComponent,
    NotificationComponent,
    PainelComponent,
    SelecionarAnimaisComponent,
    SelecionarAnimalComponent,
    SelecionarImagemComponent,
    SelecionarTutorComponent,
    SelecionarUsuarioComponent,
    ToolbarComponent,
    TutorCadastroComponent,
    TutorEnderecoComponent,
    TutorExcluirComponent,
    TutorInformacoesComponent,
    TutorComponent,
    TutoresComponent,
    UsuarioInformacoesComponent,
    UsuarioComponent,
    UsuariosCadastroComponent,
    UsuariosComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatDatepickerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatSelectModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    NgxMaskModule.forRoot(),
    NgxMatDatetimePickerModule,
    NgxMatNativeDateModule,
    ReactiveFormsModule,
    RouterModule,
  ],
  providers: [
    { provide: DatePipe },
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() },
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }
  ]
})
export class ComponentsModule { }
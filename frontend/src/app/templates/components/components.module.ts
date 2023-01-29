import { NgxMatDatetimePickerModule, NgxMatNativeDateModule } from '@angular-material-components/datetime-picker';
import { CommonModule } from '@angular/common';
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
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { NgxMaskModule } from 'ngx-mask';
import { getDutchPaginatorIntl } from 'src/app/configurations/internacionalization';

import { AdocaoCadastroComponent } from './adocao-cadastro/adocao-cadastro.component';
import { AdocaoExcluirComponent } from './adocao-excluir/adocao-excluir.component';
import { AdocoesComponent } from './adocoes/adocoes.component';
import { AnimaisComponent } from './animais/animais.component';
import { AnimalCadastroComponent } from './animal-cadastro/animal-cadastro.component';
import { AnimalExcluirComponent } from './animal-excluir/animal-excluir.component';
import { AnimalFichaMedicaComponent } from './animal-ficha-medica/animal-ficha-medica.component';
import { AnimalInformacoesComponent } from './animal-informacoes/animal-informacoes.component';
import { AnimalComponent } from './animal/animal.component';
import { AtendimentoCadastroComponent } from './atendimento-cadastro/atendimento-cadastro.component';
import { AtendimentoExcluirComponent } from './atendimento-excluir/atendimento-excluir.component';
import { AtendimentosComponent } from './atendimentos/atendimentos.component';
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
    AdocaoCadastroComponent,
    AdocaoExcluirComponent,
    AdocoesComponent,
    AnimaisComponent,
    AnimalCadastroComponent,
    AnimalExcluirComponent,
    AnimalFichaMedicaComponent,
    AnimalInformacoesComponent,
    AtendimentoCadastroComponent,
    AtendimentoExcluirComponent,
    AtendimentosComponent,
    AnimalComponent,
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
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() },
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }
  ]
})
export class ComponentsModule { }
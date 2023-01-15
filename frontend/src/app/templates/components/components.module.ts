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
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { NgxMaskModule } from 'ngx-mask';
import { getDutchPaginatorIntl } from 'src/app/configurations/internacionalization';

import { AnimaisCadastroComponent } from './animais-cadastro/animais-cadastro.component';
import { AnimaisExcluirComponent } from './animais-excluir/animais-excluir.component';
import { AnimaisComponent } from './animais/animais.component';
import { AnimalComponent } from './animal/animal.component';
import { FooterComponent } from './footer/footer.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { NotificationComponent } from './notification/notification.component';
import { PainelComponent } from './painel/painel.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { TutorComponent } from './tutor/tutor.component';
import { TutoresCadastroComponent } from './tutores-cadastro/tutores-cadastro.component';
import { TutoresExcluirComponent } from './tutores-excluir/tutores-excluir.component';
import { TutoresComponent } from './tutores/tutores.component';
import { UsuariosCadastroComponent } from './usuarios-cadastro/usuarios-cadastro.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { SelecionarImagemComponent } from './selecionar-imagem/selecionar-imagem.component';
import { SelecionarTutorComponent } from './selecionar-tutor/selecionar-tutor.component';

@NgModule({
  declarations: [
    LoginComponent,
    FooterComponent,
    NotificationComponent,
    PainelComponent,
    LayoutComponent,
    ToolbarComponent,
    UsuariosComponent,
    TutoresComponent,
    AnimaisComponent,
    UsuariosCadastroComponent,
    AnimaisCadastroComponent,
    AnimaisExcluirComponent,
    AnimalComponent,
    TutoresCadastroComponent,
    TutoresExcluirComponent,
    TutorComponent,
    SelecionarImagemComponent,
    SelecionarTutorComponent
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
    MatToolbarModule,
    NgxMaskModule.forRoot(),
    ReactiveFormsModule,
    RouterModule,
  ],
  providers: [
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() },
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }
  ]
})
export class ComponentsModule { }
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AdministracaoModule } from './administracao/administracao.module';
import { FuncionarioModule } from './funcionario/funcionario.module';
import { VeterinarioModule } from './veterinario/veterinario.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdministracaoModule,
    FuncionarioModule,
    VeterinarioModule,
  ],
})
export class ModulesModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComponentsModule } from './components/components.module';
import { ModulesModule } from './modules/modules.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ComponentsModule,
    ModulesModule
  ]
})
export class TemplatesModule { }
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Empresa } from 'src/app/entities/empresa';
import { NotificationType } from 'src/app/enums/notification-type';
import { EmpresaService } from 'src/app/services/empresa.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-empresa-informacoes',
  templateUrl: './empresa-informacoes.component.html',
  styleUrls: ['./empresa-informacoes.component.sass'],
})
export class EmpresaInformacoesComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private _empresaService: EmpresaService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  get telefones() {
    return this.form.controls['telefones'] as FormArray;
  }

  ngOnInit(): void {

    this._empresaService.get().subscribe({

      next: (empresa) => {
        this.buildForm(empresa);        
      }
    });
  
  }

  addTelefone() {
    this.telefones.push(
      this._formBuilder.group({
        id: [null, Validators.nullValidator],
        numero: [null, Validators.nullValidator],
      })
    );
  }

  buildForm(empresa: Empresa | null) {
    this.form = this._formBuilder.group({
      id: [empresa?.id, Validators.nullValidator],
      nome: [empresa?.nome, Validators.required],
      cnpj: [empresa?.cnpj, Validators.required],
      telefones: this._formBuilder.array(
        empresa?.telefones
          ? empresa.telefones.map((telefone) =>
              this._formBuilder.group({
                id: [telefone.id, Validators.nullValidator],
                numero: [
                  telefone.numero,
                  [Validators.required, Validators.maxLength(11)],
                ],
              })
            )
          : []
      ),
      endereco: [empresa?.endereco, Validators.nullValidator]
    });
  }

  getErrorMessage(controlName: string) {
    return FormUtils.getErrorMessage(this.form, controlName);
  }

  hasError(controlName: string) {
    return FormUtils.hasError(this.form, controlName);
  }

  redirectToPanel() {
    this._redirectService.toPainel();
  }

  removeTelefone(index: number) {
    this.telefones.removeAt(index);
  }

  submit() {
    if (this.form.valid) {
      const empresa: Empresa = Object.assign({}, this.form.getRawValue());

      this._empresaService.update(empresa).subscribe({

        next: (empresa) => {
          this._empresaService.set(empresa);
          this._notificationService.show(
            MessageUtils.EMPRESA.UPDATE_SUCCESS,
            NotificationType.SUCCESS
          );
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(
            MessageUtils.EMPRESA.UPDATE_FAIL + MessageUtils.getMessage(error),
            NotificationType.FAIL
          );
        },
      });
    }
  }
}

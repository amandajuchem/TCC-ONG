import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Empresa } from 'src/app/entities/empresa';
import { Endereco } from 'src/app/entities/endereco';
import { NotificationType } from 'src/app/enums/notification-type';
import { EmpresaService } from 'src/app/services/empresa.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RedirectService } from 'src/app/services/redirect.service';
import { FormUtils } from 'src/app/utils/form-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

@Component({
  selector: 'app-empresa-endereco',
  templateUrl: './empresa-endereco.component.html',
  styleUrls: ['./empresa-endereco.component.sass'],
})
export class EmpresaEnderecoComponent implements OnInit {
  empresa!: Empresa;
  form!: FormGroup;

  constructor(
    private _empresaService: EmpresaService,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    this._empresaService.get().subscribe({
      next: (empresa) => {
        if (empresa) {
          this.empresa = empresa;
          this.buildForm(empresa);
        }
      },
    });
  }

  buildForm(empresa: Empresa) {
    this.form = this._formBuilder.group({
      id: [empresa.endereco?.id, Validators.nullValidator],
      rua: [
        empresa.endereco?.rua,
        [Validators.required, Validators.maxLength(100)],
      ],
      numeroResidencia: [
        empresa.endereco?.numeroResidencia,
        [Validators.required, Validators.maxLength(10)],
      ],
      bairro: [
        empresa.endereco?.bairro,
        [Validators.required, Validators.maxLength(50)],
      ],
      complemento: [
        empresa.endereco?.complemento,
        [Validators.maxLength(100)],
      ],
      cidade: [
        empresa.endereco?.cidade,
        [Validators.required, Validators.maxLength(100)],
      ],
      estado: [
        empresa.endereco?.estado,
        [Validators.required, Validators.maxLength(25)],
      ],
      cep: [
        empresa.endereco?.cep,
        [Validators.required, Validators.maxLength(8)],
      ],
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

  submit() {
    if (this.form.valid) {
      const endereco: Endereco = Object.assign({}, this.form.getRawValue());
      this.empresa.endereco = endereco;

      this._empresaService.update(this.empresa).subscribe({

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

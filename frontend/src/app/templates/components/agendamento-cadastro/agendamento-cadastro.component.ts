import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Agendamento } from 'src/app/entities/agendamento';
import { Authentication } from 'src/app/entities/authentication';
import { NotificationType } from 'src/app/enums/notification-type';
import { Setor } from 'src/app/enums/setor';
import { AgendamentoService } from 'src/app/services/agendamento.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { DateUtils } from 'src/app/utils/date-utils';
import { MessageUtils } from 'src/app/utils/message-utils';

import { AgendamentoExcluirComponent } from '../agendamento-excluir/agendamento-excluir.component';
import { SelecionarAnimalComponent } from '../selecionar-animal/selecionar-animal.component';
import { SelecionarUsuarioComponent } from '../selecionar-usuario/selecionar-usuario.component';

@Component({
  selector: 'app-agendamento-cadastro',
  templateUrl: './agendamento-cadastro.component.html',
  styleUrls: ['./agendamento-cadastro.component.sass']
})
export class AgendamentoCadastroComponent implements OnInit {

  agendamento!: Agendamento;
  authentication!: Authentication;
  form!: FormGroup;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _agendamentoService: AgendamentoService,
    private _authService: AuthService,
    private _dialog: MatDialog,
    private _formBuilder: FormBuilder,
    private _notificationService: NotificationService,
    private _router: Router
  ) { }

  ngOnInit(): void {

    this.authentication = this._authService.getAuthentication();

    this._activatedRoute.params.subscribe({

      next: (params: any) => {
          
        if (params && params.id) {
          
          if (params.id.includes('cadastro')) {
            this.buildForm(null);
          }
          
          else {
            
            this._agendamentoService.findById(params.id).subscribe({

              next: (agendamento) => {
                this.agendamento = agendamento;
                this.buildForm(agendamento);
              },

              error: (error) => {
                console.error(error);
                this._notificationService.show(MessageUtils.AGENDAMENTO_GET_FAIL + error.error[0].message, NotificationType.FAIL);
              }
            });
          }
        }  
      }
    });
  }

  buildForm(agendamento: Agendamento | null) {

    this.form = this._formBuilder.group({
      id: [agendamento?.id, Validators.nullValidator],
      dataHora: [agendamento?.dataHora, Validators.required],
      animal: [agendamento?.animal, Validators.required],
      veterinario: [agendamento?.veterinario, Validators.required],
      descricao: [agendamento?.descricao, Validators.required]
    });

    agendamento ? this.form.disable() : this.form.enable();
  }

  cancel() {    
    this.agendamento ? this.buildForm(this.agendamento) : this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/agendamentos']);
  }

  dateChange() {

    if (this.form.get('dataHora')?.value) {
      this.form.get('dataHora')?.patchValue(DateUtils.getDateTimeWithoutSecondsAndMilliseconds(this.form.get('dataHora')?.value));
    }
  }

  delete() {

    this._dialog.open(AgendamentoExcluirComponent, {
      data: {
        agendamento: this.agendamento
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {
          
        if (result && result.status) {
          this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/agendamentos']);
        }
      }
    });
  }

  selectAnimal() {

    this._dialog.open(SelecionarAnimalComponent, {
      data: {
        multiplus: false
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.form.get('animal')?.patchValue(result.animal);
        }
      },
    });
  }

  selectVeterinario() {

    this._dialog.open(SelecionarUsuarioComponent, {
      data: {
        setor: Setor.VETERINARIO
      },
      width: '100%'
    })
    .afterClosed().subscribe({

      next: (result) => {

        if (result && result.status) {
          this.form.get('veterinario')?.patchValue(result.usuario);
        }
      }
    });
  }

  submit() {

    const agendamento: Agendamento = Object.assign({}, this.form.getRawValue());
    agendamento.dataHora = DateUtils.addHours(agendamento.dataHora, DateUtils.offsetBrasilia);

    if (agendamento.id) {

      this._agendamentoService.update(agendamento).subscribe({

        complete: () => {
          this._notificationService.show(MessageUtils.AGENDAMENTO_UPDATE_SUCCESS, NotificationType.SUCCESS);
          this.ngOnInit();
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.AGENDAMENTO_UPDATE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }

    else {

      this._agendamentoService.save(agendamento).subscribe({

        next: (agendamento) => {
          this._notificationService.show(MessageUtils.AGENDAMENTO_SAVE_SUCCESS, NotificationType.SUCCESS);
          this._router.navigate(['/' + this.authentication.role.toLowerCase() + '/agendamentos/' + agendamento.id]);
        },

        error: (error) => {
          console.error(error);
          this._notificationService.show(MessageUtils.AGENDAMENTO_SAVE_FAIL + error.error[0].message, NotificationType.FAIL);
        }
      });
    }
  }
}
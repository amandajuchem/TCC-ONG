import { AbstractEntity } from './abstract-entity';
import { Animal } from './animal';
import { ExameRealizado } from './exame-realizado';
import { Usuario } from './usuario';

export interface Atendimento extends AbstractEntity {
    dataHora: Date;
    dataHoraRetorno: Date;
    animal: Animal;
    veterinario: Usuario;
    motivo: string;
    diagnostico: string;
    procedimentos: string;
    posologia: string;
    examesRealizados: Array<ExameRealizado>;
}
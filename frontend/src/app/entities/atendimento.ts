import { AbstractEntity } from './abstract-entity';
import { Animal } from './animal';
import { Usuario } from './usuario';

export interface Atendimento extends AbstractEntity {
    dataHora: Date;
    dataHoraRetorno: Date;
    motivo: string;
    comorbidades: string;
    diagnostico: string;
    exames: string;
    procedimentos: string;
    posologia: string;
    documentos: Array<any>;

    animal: Animal;
    veterinario: Usuario;
}
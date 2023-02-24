import { AbstractEntity } from './abstract-entity';
import { Animal } from './animal';
import { Exame } from './exame';
import { Usuario } from './usuario';

export interface Atendimento extends AbstractEntity {
    dataHora: Date;
    dataHoraRetorno: Date;
    motivo: string;
    diagnostico: string;
    exames: Array<Exame>;
    procedimentos: string;
    posologia: string;
    documentos: Array<any>;

    animal: Animal;
    veterinario: Usuario;
}
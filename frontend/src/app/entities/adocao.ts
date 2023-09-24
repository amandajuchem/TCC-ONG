import { Local } from '../enums/local';
import { LocalAdocao } from '../enums/local-adocao';
import { AbstractEntity } from './abstract-entity';
import { Animal } from './animal';
import { FeiraAdocao } from './feira-adocao';
import { Tutor } from './tutor';

export interface Adocao extends AbstractEntity {
    dataHora: Date;
    local: Local;
    localAdocao: LocalAdocao;
    valeCastracao: boolean;
    animal: Animal;
    tutor: Tutor;
    feiraAdocao: FeiraAdocao
    termoResponsabilidade: Array<any>;
}
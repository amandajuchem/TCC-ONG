import { Setor } from '../enums/setor';
import { AbstractEntity } from './abstract-entity';

export interface Usuario extends AbstractEntity {
    nome: string;
    cpf: string;
    senha: string;
    setor: Setor;
    status: boolean;
    foto: any;
}
import { Situacao } from '../enums/situacao';
import { AbstractEntity } from './abstract-entity';
import { Endereco } from './endereco';
import { Telefone } from './telefone';

export interface Tutor extends AbstractEntity {
    nome: string;
    cpf: string;
    rg: string;
    situacao: Situacao;
    observacao: string;
    foto: any;
    telefones: Array<Telefone>;
    endereco: Endereco;
}
import { Situacao } from '../enums/situacao';
import { AbstractEntity } from './abstract-entity';
import { Adocao } from './adocao';
import { Endereco } from './endereco';
import { Observacao } from './observacao';
import { Telefone } from './telefone';

export interface Tutor extends AbstractEntity {
    nome: string;
    cpf: string;
    rg: string;
    situacao: Situacao;
    foto: any;
    telefones: Array<Telefone>;
    endereco: Endereco;
    adocoes: Array<Adocao>;
    observacoes: Array<Observacao>;
}
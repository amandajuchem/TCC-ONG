import { Situacao } from "../enums/situacao";
import { AbstractEntity } from "./abstract-entity";
import { Endereco } from "./endereco";

export interface Tutor extends AbstractEntity {
    nome: string;
    cpf: string;
    rg: string;
    telefone: string;
    situacao: Situacao;
    observacao: string;
    foto: any;
    endereco: Endereco;
}